package it.polimi.db2.project.web.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.project.ejb.entities.OrderEntity;
import it.polimi.db2.project.ejb.entities.ServicePackageEntity;
import it.polimi.db2.project.ejb.entities.UserEntity;
import it.polimi.db2.project.ejb.exceptions.CredentialsException;
import it.polimi.db2.project.ejb.services.UserService;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.project.ejb.services/UserService")
    private UserService userService;
//    String visibilita = "visibile";
    ServicePackageEntity servicePackage;

    
    @Override
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
    	
    	servicePackage = (ServicePackageEntity) req.getSession(false).getAttribute("servicePackage");
    	
//    	if(servicePackage != null) {
//    		visibilita = "nascosto";
//    	}
    	
    	//String servletToLoad = (String) req.getSession().getAttribute("servletToLoad");
    	
    	req.setAttribute("servicePackage", servicePackage);
//    	req.setAttribute("visibilita", visibilita);
    	
        resp.sendRedirect(getServletContext().getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	
    	servicePackage = (ServicePackageEntity) req.getSession(false).getAttribute("servicePackage");
        
        if (req.getParameter("homewithoutloginbtn") != null) {
    		
        	req.getSession().removeAttribute("servicePackage");
    		resp.sendRedirect(getServletContext().getContextPath() + "/homepage");
    		return;
    		
    	}
        
        String username = StringEscapeUtils.escapeJava(req.getParameter("username"));
        String password = StringEscapeUtils.escapeJava(req.getParameter("password"));
        
        String servlettoload = "";

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value.");
            return;
        }

        UserEntity user;
        try {
            user = userService.checkCredentials(username, password);
        } catch (CredentialsException | NonUniqueResultException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials.");
            return;
        }
        
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());

        if (user == null) {
            resp.setContentType("text/html");
            ctx.setVariable("errorMessage", "Incorrect username or password.");
            templateEngine.process("/WEB-INF/index.html", ctx, resp.getWriter());
            
        }
        else if(user != null) {
        	if(servicePackage == null) {
        		req.getSession().setAttribute("user", user);
        		req.getSession().removeAttribute("servicePackage");
        		req.removeAttribute("servicePackage");
                servlettoload =  "/homepage";
        	}else {
        		req.getSession().setAttribute("user", user);
        		servlettoload =  "/confirmationpage";
        	}
        	resp.sendRedirect(getServletContext().getContextPath() + servlettoload);
        }
        
        
    }
}
