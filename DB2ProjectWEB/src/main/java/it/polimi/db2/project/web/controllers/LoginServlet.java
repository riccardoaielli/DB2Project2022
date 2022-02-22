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

import it.polimi.db2.project.ejb.entities.UserEntity;
import it.polimi.db2.project.ejb.exceptions.CredentialsException;
import it.polimi.db2.project.ejb.services.UserService;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.project.ejb.services/UserService")
    private UserService userService;

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
        resp.sendRedirect(getServletContext().getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = StringEscapeUtils.escapeJava(req.getParameter("username"));
        String password = StringEscapeUtils.escapeJava(req.getParameter("password"));

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

        if (user == null) {
            resp.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", "Incorrect username or password.");

            templateEngine.process("/WEB-INF/index.html", ctx, resp.getWriter());
        } else {

            req.getSession().setAttribute("user", user);
            resp.sendRedirect(getServletContext().getContextPath() + "/homepage");
        }
    }
}
