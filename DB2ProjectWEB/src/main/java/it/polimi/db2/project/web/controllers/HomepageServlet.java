package it.polimi.db2.project.web.controllers;

import it.polimi.db2.project.ejb.entities.*;
import it.polimi.db2.project.ejb.exceptions.CredentialsException;
import it.polimi.db2.project.ejb.services.EmployeeServicePackService;
import it.polimi.db2.project.ejb.services.OrderService;
import it.polimi.db2.project.ejb.services.ServicePackageService;
import it.polimi.db2.project.ejb.services.UserService;

import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "HomepageServlet", value = "/homepage")
public class HomepageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    
    String servletToLoad;
    
    
	@EJB(name = "it.polimi.db2.project.ejb.services/EmployeeServicePackService")
	private EmployeeServicePackService employeeServicePackService;
	
    @EJB(name = "it.polimi.db2.project.ejb.services/UserService")
    private UserService userService;
    
    @EJB
    private OrderService orderService;
    
    @EJB
    private ServicePackageService servicePackageService;


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
        String path;
        
        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        
        if(user != null) {
        	if(user.getFlag_ins()) {
        	
	        	List<OrderEntity> orders;
	        	try {
	            	orders = orderService.findFailedOrdersByUserId(user.getId());
	            	        } catch (PersistenceException e) {
	                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not retrieve the orders");
	                return;
	            }
	        	ctx.setVariable("orders", orders);
        	
        	}
        }
        
        List<EmployeeServicePackEntity> employeeServicePacks;
        try {
        	employeeServicePacks = employeeServicePackService.findAllEmployeeServicePack();
        	        } catch (PersistenceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not retrieve the employeeServicePacks");
            return;
        }

        resp.setContentType("text/html");

        ctx.setVariable("employeeServicePacks", employeeServicePacks);
        path = "/WEB-INF/homepage.html";
        

        templateEngine.process(path, ctx, resp.getWriter());
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	
    	HttpSession session = req.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        
    	if (req.getParameter("orderbtn") != null) {
    		
    		//recupera il service package
    		OrderEntity failedOrder = orderService.findOrderByID(Integer.valueOf(req.getParameter("orderId"))).get();
    		ServicePackageEntity servicePackage = servicePackageService.findServicePackById(failedOrder.getService_pack_id().getId());
    		session.setAttribute("failedOrder", failedOrder);
    		session.setAttribute("servicePackage", servicePackage);
    		servletToLoad = "/confirmationpage";
    		
    	}else {
    		servletToLoad = "/buypage";
    	}
    	resp.sendRedirect(getServletContext().getContextPath() + servletToLoad );
    	return;
    }
}
