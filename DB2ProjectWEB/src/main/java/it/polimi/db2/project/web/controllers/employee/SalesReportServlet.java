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

import it.polimi.db2.project.ejb.SalesReportEntities.Alert;
import it.polimi.db2.project.ejb.SalesReportEntities.AverageOPwithESP;
import it.polimi.db2.project.ejb.SalesReportEntities.Best_seller_OP;
import it.polimi.db2.project.ejb.SalesReportEntities.Insolvent;
import it.polimi.db2.project.ejb.SalesReportEntities.NumberTotalPurchasesPerESP;
import it.polimi.db2.project.ejb.SalesReportEntities.NumberTotalPurchasesPerESPAndValidityPeriod;
import it.polimi.db2.project.ejb.SalesReportEntities.RejectedOrder;
import it.polimi.db2.project.ejb.SalesReportEntities.SalesPerPackage;
import it.polimi.db2.project.ejb.entities.EmployeeEntity;
import it.polimi.db2.project.ejb.entities.OptionalProductEntity;
import it.polimi.db2.project.ejb.entities.ValidityPeriodEntity;
import it.polimi.db2.project.ejb.enums.Services;
import it.polimi.db2.project.ejb.services.EmployeeService;
import it.polimi.db2.project.ejb.services.EmployeeServicePackService;
import it.polimi.db2.project.ejb.services.OptionalProductService;
import it.polimi.db2.project.ejb.services.SalesReportService;
import it.polimi.db2.project.ejb.services.ValidityPeriodService;


@WebServlet("/employee/SalesReportServlet")
public class SalesReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private TemplateEngine templateEngine;

	@EJB(name = "it.polimi.db2.project.ejb.services/ValidityPeriodService")
	private ValidityPeriodService VPservice;
	
	@EJB(name = "it.polimi.db2.project.ejb.services/SalesReportService")
	private SalesReportService SRservice;

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
		
	}

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		HttpSession session = request.getSession();
		SRservice.refreshAll();
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
		
		
		List<SalesPerPackage> salesESP = SRservice.findAllSalesPerPackage();
		List<AverageOPwithESP> averageOPs = SRservice.findAllAverageOPwithESP();
		List<NumberTotalPurchasesPerESP> totPurchases = SRservice.findAllNumberTotalPurchasesPerESP();
		List<NumberTotalPurchasesPerESPAndValidityPeriod> totPurchasesVP= SRservice.findAllNumberTotalPurchasesPerESPAndValidityPeriod();
		List<Alert> alerts = SRservice.findAllAlert();
		List<Insolvent> insolvents = SRservice.findAllInsolvent();
		List<RejectedOrder> rejects = SRservice.findAllRejectedOrder();
		Best_seller_OP best_seller = SRservice.findAllBest_seller_OP();
		
		ctx.setVariable("salesESP", salesESP);
		ctx.setVariable("best_seller", best_seller);
		ctx.setVariable("averageOPs", averageOPs);
	
		ctx.setVariable("totPurchases", totPurchases);
		
		ctx.setVariable("totPurchasesVP", totPurchasesVP);
		
		ctx.setVariable("alerts", alerts);
		ctx.setVariable("insolvents", insolvents);
		ctx.setVariable("rejects", rejects);
		
		templateEngine.process(path, ctx, response.getWriter());
		
		

	}

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
