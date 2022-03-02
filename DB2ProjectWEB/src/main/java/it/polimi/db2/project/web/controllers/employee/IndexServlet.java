package it.polimi.db2.project.web.controllers.employee;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "EmployeeIndexServlet", value = "/employee")
public class IndexServlet extends HttpServlet {
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

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("employee") == null) { // No logged-in user found, so redirect to login page.
            resp.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            String path = "/WEB-INF/employee/index.html";

            templateEngine.process(path, ctx, resp.getWriter());
        } else {
            resp.sendRedirect(req.getContextPath() + "/employee/homepage");
        }
    }
}