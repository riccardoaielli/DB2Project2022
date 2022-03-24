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

@WebServlet(name = "CreateOPServlet", value = "/employee/newOP")
public class CreateOPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    
    @EJB(name = "it.polimi.db2.project.ejb.services/OptionalProductService")
    private OptionalProductService OPservice;
    
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
        
        
        // CHECK IF THE CURRENT USER IS AN EMPLOYEE OR NOT, IF IT NOT REDIRECT TO HOME
        if(employee==null) {
        	path = "/WEB-INF/employee/index.html";
        }
        else {
        	path = "/WEB-INF/employee/newOP.html";
        }

        templateEngine.process(path, ctx, resp.getWriter());
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
    	HttpSession session = req.getSession();
    	EmployeeEntity employee = (EmployeeEntity) session.getAttribute("employee");
    	
    	
    	String error = "The employee did not authenticate!";
    	
    	String path = "/WEB-INF/employee/index.html";
    	
    	String destServlet = "/employee/newOP";
    	
    	OptionalProductEntity newOP = null;
    	 
    	if (employee == null) {
            

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", error);
            templateEngine.process(path, ctx, resp.getWriter());
    	}
    	
   
    	String name = req.getParameter("OP_name");
    	String feeString = req.getParameter("OP_fee");
    	Float fee = Float.valueOf(feeString).floatValue();
    
    	
    	
    
    	String newOP_message = "The name for the Optional Product is already in use!";
        
    	if( name == null || fee == null)
    		newOP_message = "The name for the Optional Product is already in use!";
    	else
    		newOP = OPservice.addNewOptionalProduct(name, fee);
        
        if( newOP != null )
        	newOP_message = "success";
        
        path = "/WEB-INF/employee/newOP.html";
        
        ServletContext servletContext = getServletContext();
        resp.setContentType("text/html");
    	WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        ctx.setVariable("newOP_message", newOP_message);

    	templateEngine.process(path, ctx, resp.getWriter());
    	
    }
}
