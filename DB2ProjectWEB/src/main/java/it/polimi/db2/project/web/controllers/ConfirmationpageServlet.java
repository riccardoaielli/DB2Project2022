package it.polimi.db2.project.web.controllers;

import java.io.IOException;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.project.ejb.entities.AlertEntity;
import it.polimi.db2.project.ejb.entities.OptionalProductEntity;
import it.polimi.db2.project.ejb.entities.OrderEntity;
import it.polimi.db2.project.ejb.entities.ServiceEntity;
import it.polimi.db2.project.ejb.entities.ServicePackageEntity;
import it.polimi.db2.project.ejb.entities.UserEntity;
import it.polimi.db2.project.ejb.entities.ValidityPeriodEntity;
import it.polimi.db2.project.ejb.services.AlertService;
import it.polimi.db2.project.ejb.services.OrderService;
import it.polimi.db2.project.ejb.services.ServicePackageService;
import it.polimi.db2.project.ejb.services.UserService;

@WebServlet(name = "ConfirmationpageServlet", value = "/confirmationpage")
public class ConfirmationpageServlet extends HttpServlet {
	
	@EJB
    private UserService userService;
	
	@EJB
    private ServicePackageService servicePackageService;
	
	@EJB
    private OrderService orderService;
	
	@EJB
    private AlertService alertService;
	
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	ServicePackageEntity servicePackage;
    boolean creaPacchetto = true;
    float costototale;
    String servletToLoad;
    OrderEntity failedOrder;
    boolean isvalid;

	public void init() {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		UserEntity user = (UserEntity) session.getAttribute("user");
		
		
//		failedOrder = (OrderEntity) req.getSession(false).getAttribute("failedOrder");
		
		servicePackage = (ServicePackageEntity) req.getSession(false).getAttribute("servicePackage");
		

		costototale = servicePackage.getTotalcostoptionalproducts() + servicePackage.getCostpackage();
		String costoTotale = String.valueOf(costototale);
		
		
//		req.setAttribute("failedOrder", failedOrder);
		req.setAttribute("servicePackage", servicePackage);
		req.setAttribute("costoTotale", costoTotale);

		String path;
		resp.setContentType("text/html");
		ServletContext servletContext = getServletContext();
		WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
		path="/WEB-INF/confirmationpage.html";
		templateEngine.process(path,ctx,resp.getWriter());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		HttpSession session = req.getSession();
		UserEntity user = (UserEntity) session.getAttribute("user");

		if (req.getParameter("buyLoginbtn") != null) {

			System.out.println("buyLoginbtn premuto");
			resp.sendRedirect(getServletContext().getContextPath() + "/login");
			return;
		
		}else if(req.getParameter("buyFailbtn") != null){
			System.out.println("buyFailbtn premuto");
			isvalid = false;
			
		}else if(req.getParameter("buySuccessbtn") != null){
			System.out.println("buySuccessbtn premuto");
			isvalid = true;
		}
		
		failedOrder = (OrderEntity) req.getSession(false).getAttribute("failedOrder");

		if (failedOrder != null) {
			creaPacchetto = false;
		} else {
			creaPacchetto = true;
		}
		
		servicePackage = (ServicePackageEntity) req.getSession(false).getAttribute("servicePackage");
		
		if(creaPacchetto){
			float totalcost = servicePackage.getCostpackage() + servicePackage.getTotalcostoptionalproducts();
    		OrderEntity order = new OrderEntity(new Timestamp(System.currentTimeMillis()), totalcost, user, servicePackage, isvalid);
    		
            try {
                servicePackage = servicePackageService.createServicePackage(servicePackage);
                System.out.println("servicePackage inserito nel db");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    		try {
    			order = orderService.createOrder(order);
    			System.out.println("order inserito nel db");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
        	failedOrder = orderService.updateOrder(failedOrder, isvalid);
            System.out.println("sistemato isvalid in failed order nel db");
        }

        if(!isvalid) user = userService.incrementsFailedPayments(user);
        
        if(user.getNumberOfFailedPayments()==3){
            AlertEntity alert = new AlertEntity(failedOrder.getTotalcost(), failedOrder.getTimestamp(), user);
            System.out.println("Creo alert perchè l'utente ha 3 o più pagamenti falliti");
            alertService.createAlert(alert);
            user = userService.setNumberOfFailedPayments(user);
        }
        
        
        //imposto l'utente come insolvente se ha degli ordini falliti
        if(orderService.findFailedOrdersByUserId(user.getId()).size()>=1) userService.setUserInsolvent(user, true);
        else userService.setUserInsolvent(user, false);
        req.getSession().setAttribute("user", user);
        
        if(orderService.findOrderScheduledByUserId(user.getId()).size()>0) servletToLoad = "/serviceactivationschedule";
        else servletToLoad = "/homepage";

//        if(servletToLoad == "/homepage") {
//        	req.removeAttribute("servicePackage");
//        	req.removeAttribute("costoTotale");
//        	req.getSession(false).removeAttribute("failedOrder");
//        	req.getSession(false).removeAttribute("servicePackage");
//        }
        req.removeAttribute("servicePackage");
    	req.removeAttribute("costoTotale");
    	req.getSession(false).removeAttribute("failedOrder");
    	req.getSession(false).removeAttribute("servicePackage");
    	
        resp.sendRedirect(getServletContext().getContextPath() + servletToLoad);

	}

}
