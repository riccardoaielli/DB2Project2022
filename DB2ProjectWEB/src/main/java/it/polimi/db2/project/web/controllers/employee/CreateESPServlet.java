package it.polimi.db2.project.web.controllers.employee;



import it.polimi.db2.project.ejb.entities.*;
import it.polimi.db2.project.ejb.enums.Services;
import it.polimi.db2.project.ejb.exceptions.CredentialsException;
import it.polimi.db2.project.ejb.services.*;
import it.polimi.db2.project.web.exceptions.InputException;

import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Array;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@WebServlet(name = "CreateESPServlet", value = "/employee/newESP")
public class CreateESPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    
    @EJB(name = "it.polimi.db2.project.ejb.services/EmployeeServicePackageService")
    private EmployeeServicePackService ESPservice;
    
    @EJB(name = "it.polimi.db2.project.ejb.services/OptionalProductService")
    private OptionalProductService OPservice;
    
    @EJB(name = "it.polimi.db2.project.ejb.services/ValidityPeriodService")
    private ValidityPeriodService VPservice; 
    
    @EJB(name = "it.polimi.db2.project.ejb.services/ServiceService")
    private ServiceService sservice;
    
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
        EmployeeEntity employee = (EmployeeEntity) session.getAttribute("employee");
        String path;

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        
        ctx.setVariable("services", Services.values());
        
        List<ValidityPeriodEntity> val_periods = VPservice.findAllValidityPeriod();
        ctx.setVariable("val_periods", val_periods);
        
        List<OptionalProductEntity> optional_products = OPservice.findAllOptionalProduct();
        List<ServiceEntity> services = sservice.findAllService();
        ctx.setVariable("services", services);
        ctx.setVariable("optional_products", optional_products);
        
        String destServlet = "";
        
        // CHECK IF THE CURRENT USER IS AN EMPLOYEE OR NOT, IF IT NOT REDIRECT TO INDEX
        if(employee==null) {
        	path = "/WEB-INF/employee/index.html";
        	destServlet = "/employee/index";
        }
        else {
        	path = "/WEB-INF/employee/newESP.html";
        	destServlet = "/employee/newESP";
        }

        templateEngine.process(path, ctx, resp.getWriter());
        
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
    	HttpSession session = req.getSession();
    	EmployeeEntity employee = (EmployeeEntity) session.getAttribute("employee");
    	
    	String error = "The employee did not authenticate!";
    	
    	String path = "/WEB-INF/employee/index.html";
    	
    	String destServlet = "/employee/newESP";
    	
    	
    	 
    	if (employee == null) {
            

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", error);
            templateEngine.process(path, ctx, resp.getWriter());
    	}
    	
    	
    	
    	List<String> servicesIds = new ArrayList<>();
    	List<String> OptionalPStrings = new ArrayList<>();
    	List<String> ValidityPIds = new ArrayList<>();
    	
    	Enumeration<String> params = req.getParameterNames(); 
    	String name = req.getParameter(params.nextElement());
    	String full = "";
    	String sub = "";
    	String clean = "";
    	
    	while(params.hasMoreElements()) {
    		full  = (String)params.nextElement();
    		sub = full.substring(0, 3);
    		clean = full.substring(3);
    		if( sub.equals("SER") )
    			servicesIds.add(clean);
    		else if( sub.equals("OPS") )
    			OptionalPStrings.add(clean);
    		else if( sub.equals("VPE") )
    			ValidityPIds.add(clean);
    		
    	}
    	
    	System.out.println(servicesIds);
    	System.out.println(OptionalPStrings);
    	System.out.println(ValidityPIds);

    	
    	
    
    	EmployeeServicePackEntity newESP = null;
    	String newESP_message = "The name for the Employee Service Package is already in use!";
        
 
            
        newESP = ESPservice.addNewEmployeeServicePack(name, servicesIds, ValidityPIds, OptionalPStrings);
        System.out.println("OK1");
        if( newESP != null )
        	newESP_message = "success";
        
        path = "/WEB-INF/employee/newESP.html";
        ServletContext servletContext = getServletContext();
        resp.setContentType("text/html");
    	WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
    	
        ctx.setVariable("newESP_message", newESP_message);
        List<ServiceEntity> services = sservice.findAllService();
        ctx.setVariable("services", services);
        List<ValidityPeriodEntity> val_periods = VPservice.findAllValidityPeriod();
        ctx.setVariable("val_periods", val_periods);
        
        List<OptionalProductEntity> optional_products = OPservice.findAllOptionalProduct();
        ctx.setVariable("optional_products", optional_products);
    	templateEngine.process(path, ctx, resp.getWriter());
    	
    }
}


