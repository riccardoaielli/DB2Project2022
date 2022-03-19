package it.polimi.db2.project.web.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
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
import it.polimi.db2.project.ejb.entities.OptionalProductEntity;
import it.polimi.db2.project.ejb.entities.ServicePackageEntity;
import it.polimi.db2.project.ejb.entities.UserEntity;
import it.polimi.db2.project.ejb.entities.ValidityPeriodEntity;
import it.polimi.db2.project.ejb.services.EmployeeServicePackService;
import it.polimi.db2.project.ejb.services.OptionalProductService;
import it.polimi.db2.project.ejb.services.UserService;
import it.polimi.db2.project.ejb.services.ValidityPeriodService;

@WebServlet(name = "BuypageServlet", value = "/buypage")
public class BuypageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;

	private List<ValidityPeriodEntity> validityPeriods;
	private List<OptionalProductEntity> optionalProducts;
	EmployeeServicePackEntity employeeServicePackEntity;
	ServicePackageEntity servicePackage = null;
	String packageSelected = null;

	@EJB
	private UserService userService;

	@EJB
	private ValidityPeriodService validityPeriodService;
	
	@EJB
	private EmployeeServicePackService employeeServicePackService;
	
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		HttpSession session = req.getSession();
		

		if (req.getParameter("employeeServicePackbtn") != null) {
			
			System.out.println(req.getParameter("employeeServicePackbtn"));
			int employeeServicePackId = Integer.valueOf(req.getParameter("employeeServicePackId"));
			System.out.println(employeeServicePackId);
			
			employeeServicePackEntity = employeeServicePackService.findEmployeeServicePackById(employeeServicePackId);
			
			req.setAttribute("employeeServicePackEntity", employeeServicePackEntity);
			
			List<ValidityPeriodEntity> validityPeriods;
			try {
				validityPeriods = validityPeriodService.findValidityPeriodsOfEmployeeServicePackId(employeeServicePackEntity.getId());
			} catch (PersistenceException e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not retrieve validityPeriods");
				return;
			}
			
			System.out.println(validityPeriods.isEmpty());
			
			List<OptionalProductEntity> optionalProducts;
			try {
				optionalProducts = optionalProductService.findOptProdOfEmployeeServicePackId(employeeServicePackEntity.getId());
			} catch (PersistenceException e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not retrieve validityPeriods");
				return;
			}

			req.setAttribute("validityPeriods", validityPeriods);
			req.setAttribute("optionalProducts", optionalProducts);
			
			String path;
			resp.setContentType("text/html");
			ServletContext servletContext = getServletContext();
			WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
			path = "/WEB-INF/buypage.html";

			templateEngine.process(path, ctx, resp.getWriter());

		} else if (req.getParameter("confirmbtn") != null) {
			
//			Enumeration<String> paramsName = req.getParameterNames();
//			while(paramsName.hasMoreElements()) {
//				System.out.println(paramsName.nextElement());
//			}
//			
//			paramsName = req.getParameterNames();
//			while(paramsName.hasMoreElements()) {
//				String name = req.getParameter(paramsName.nextElement());
//				System.out.println(name);
//			}

			int validityPeriodId = Integer.parseInt(req.getParameter("validityPeriod"));

			String[] optionalProductsSelected = req.getParameterValues("optProducts");
			String startDateStr = req.getParameter("startDate");

			LocalDate startDate, endDate;
			java.sql.Date sqlStartDate, sqlEndDate;

			ValidityPeriodEntity validityPeriod = validityPeriodService.findByValPeriodID(validityPeriodId);

			ArrayList<OptionalProductEntity> optionalProducts = null;
			float totalValueOptProducts = 0;

			if (optionalProductsSelected != null) {
				optionalProducts = new ArrayList<>();
				for (String optProd : optionalProductsSelected) {
					optionalProducts.add(optionalProductService.findByOptProdName(optProd));
				}
				for (OptionalProductEntity optionalProduct : optionalProducts)
					totalValueOptProducts = totalValueOptProducts
							+ optionalProduct.getFee() * validityPeriod.getMonths();

			}

			float valuePackage = validityPeriod.getMonthly_fee() * validityPeriod.getMonths();

			startDate = LocalDate.parse(startDateStr);
			endDate = startDate.plusMonths(validityPeriod.getMonths());

			sqlStartDate = java.sql.Date.valueOf(startDate);
			sqlEndDate = java.sql.Date.valueOf(endDate);

			servicePackage = new ServicePackageEntity(employeeServicePackEntity, validityPeriod, sqlStartDate, sqlEndDate,
					valuePackage, totalValueOptProducts, optionalProducts);

			session.setAttribute("servicePackage", servicePackage);

			resp.sendRedirect(getServletContext().getContextPath() + "/confirmationpage");
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		HttpSession session = req.getSession();
		UserEntity user = (UserEntity) session.getAttribute("user");
		
		List<EmployeeServicePackEntity> employeeServicePacks;
		try {
			employeeServicePacks = employeeServicePackService.findAllEmployeeServicePack();
		} catch (PersistenceException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not retrieve the employeeServicePacks");
			return;
		}

		req.setAttribute("employeeServicePacks", employeeServicePacks);

		String path;
		resp.setContentType("text/html");
		ServletContext servletContext = getServletContext();
		WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
		path = "/WEB-INF/buypage.html";

		templateEngine.process(path, ctx, resp.getWriter());
	}
}