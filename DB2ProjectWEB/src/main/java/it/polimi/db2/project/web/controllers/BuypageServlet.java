package it.polimi.db2.project.web.controllers;

import java.io.IOException;
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

import it.polimi.db2.project.ejb.entities.EmployeeServicePackEntity;
import it.polimi.db2.project.ejb.entities.UserEntity;
import it.polimi.db2.project.ejb.services.EmployeeServicePackService;

@WebServlet(name = "BuypageServlet", value = "/buypage")
public class BuypageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

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

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        path = "/WEB-INF/buypage.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }
}