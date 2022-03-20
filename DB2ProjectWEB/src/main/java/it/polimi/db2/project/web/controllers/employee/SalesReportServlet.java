package it.polimi.db2.project.web.controllers.employee;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.project.ejb.entities.EmployeeEntity;
import it.polimi.db2.project.ejb.entities.OptionalProductEntity;
import it.polimi.db2.project.ejb.entities.ValidityPeriodEntity;
import it.polimi.db2.project.ejb.enums.Services;
import it.polimi.db2.project.ejb.services.EmployeeServicePackService;
import it.polimi.db2.project.ejb.services.OptionalProductService;
import it.polimi.db2.project.ejb.services.ValidityPeriodService;

/**
 * Servlet implementation class SalesReportServlet
 */
@WebServlet("/employee/SalesReportServlet")
public class SalesReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private TemplateEngine templateEngine;

	@EJB(name = "it.polimi.db2.project.ejb.services/ValidityPeriodService")
	private ValidityPeriodService VPservice;

	public void init() {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	public SalesReportServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		EmployeeEntity employee = (EmployeeEntity) session.getAttribute("employee");
		
		String destServlet = "/employee";
		String error = "The employee did not authenticate!";
    	
   	 // AUTHENTICATION CHECK
    	if (employee == null) {
            

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("errorMessage", error);
            response.sendRedirect(getServletContext().getContextPath() + destServlet);
    	}
    	
    	destServlet = "/employee/salesReport";

		String path = "/WEB-INF/employee/salesReport.html";
		ServletContext servletContext = getServletContext();
		response.setContentType("text/html");
		WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		
		//ctx.setVariable("services", Services.values());
		
		templateEngine.process(path, ctx, response.getWriter());
		
		// resp.sendRedirect(getServletContext().getContextPath() + destServlet);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
