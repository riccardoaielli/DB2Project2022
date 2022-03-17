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

@WebServlet(name = "CreationServlet", value = "/employee/newSP")
public class CreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    
    @EJB(name = "it.polimi.db2.project.ejb.services/EmployeeServicePackageService")
    private EmployeeServicePackService ESPservice;

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
        String[] val_periods = {"val1", "val2", "val3"};
        
        ctx.setVariable("val_periods", val_periods);
        
        // CHECK IF THE CURRENT USER IS AN EMPLOYEE OR NOT, IF IT NOT REDIRECT TO HOME
        if(employee==null) {
        	path = "/WEB-INF/index.html";
        }
        else {
        	path = "/WEB-INF/employee/newSP.html";
        }

        templateEngine.process(path, ctx, resp.getWriter());
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
    	HttpSession session = req.getSession();
    	EmployeeEntity employee = (EmployeeEntity) session.getAttribute("employee");
    	
    	String error = "The employee did not authenticate!";
    	
    	String path = "/WEB-INF/index.html";
    	 
    	if (employee == null) {
            

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", error);

            templateEngine.process(path, ctx, resp.getWriter());
    	}
    	
    	
    	List<String> servicesStrings = new ArrayList<>();
    	Enumeration<String> params = req.getParameterNames(); 
    	String name = req.getParameter(params.nextElement());
    	while(params.hasMoreElements())
    		servicesStrings.add(params.nextElement());
    	
    	servicesStrings = new ArrayList(servicesStrings.subList(0, servicesStrings.size() - 1 ));
    	
    	System.out.println("Name: "+ name + " ---- " + servicesStrings);
    
    	EmployeeServicePackEntity newESP = null;
       
        
        try {
            
        	newESP = ESPservice.addNewEmployeeServicePack(name, servicesStrings);
        } catch (CredentialsException e) {
            error = e.getMessage();
        }

        
    	ServletContext servletContext = getServletContext();
        resp.setContentType("text/html");
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
    	templateEngine.process(path, ctx, resp.getWriter());
    	resp.sendRedirect(getServletContext().getContextPath() + "");
    }
}


