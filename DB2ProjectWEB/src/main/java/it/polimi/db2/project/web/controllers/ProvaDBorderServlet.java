package it.polimi.db2.project.web.controllers;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
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

import it.polimi.db2.project.ejb.entities.EmployeeServicePackEntity;
import it.polimi.db2.project.ejb.entities.OptionalProductEntity;
import it.polimi.db2.project.ejb.entities.OrderEntity;
import it.polimi.db2.project.ejb.entities.ServicePackageEntity;
import it.polimi.db2.project.ejb.entities.UserEntity;
import it.polimi.db2.project.ejb.entities.ValidityPeriodEntity;
import it.polimi.db2.project.ejb.services.EmployeeServicePackService;
import it.polimi.db2.project.ejb.services.OptionalProductService;
import it.polimi.db2.project.ejb.services.OrderService;
import it.polimi.db2.project.ejb.services.ServicePackageService;
import it.polimi.db2.project.ejb.services.UserService;
import it.polimi.db2.project.ejb.services.ValidityPeriodService;


@WebServlet(name = "ProvaDBorderServlet", value = "/provaDBorder")
public class ProvaDBorderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    
    @EJB
    private ServicePackageService servicePackageService;
    
    @EJB
    private OrderService orderService;
    
	@EJB
    private UserService userService;
	
	@EJB
    private EmployeeServicePackService employeeServicePackService;
	
	@EJB
    private ValidityPeriodService validityPeriodService;
	
	@EJB
    private OptionalProductService optionalProductService;

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
		
		//ServicePackageEntity servicePackage = servicePackageService.findServicePackById(1);
		EmployeeServicePackEntity employeeServicePack = employeeServicePackService.findEmployeeServicePackById(2);
		ValidityPeriodEntity validityPeriodEntity = validityPeriodService.findByValPeriodID(1);
		UserEntity utente = userService.findUserById(1);
		Timestamp tempo = new Timestamp(System.currentTimeMillis());
		
		Date date = new Date(System.currentTimeMillis());
		
		OptionalProductEntity optp = optionalProductService.findByOptProdID(1).get();
		
		List<OptionalProductEntity> optps = new ArrayList();
		
		optps.add(optp);
		
		ServicePackageEntity servicePackage = new ServicePackageEntity(employeeServicePack, validityPeriodEntity, date,date, 100, 10, optps);
		
		OrderEntity order = new OrderEntity(tempo, 10, utente, servicePackage, false);
		
		try {
			servicePackageService.createServicePackage(servicePackage);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			orderService.createOrder(order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
		
		resp.setContentType("text/html");
        

        templateEngine.process("/WEB-INF/blankpage.html", ctx, resp.getWriter());

    }

}
