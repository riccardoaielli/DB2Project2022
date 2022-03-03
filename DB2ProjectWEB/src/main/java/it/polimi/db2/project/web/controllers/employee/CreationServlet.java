//package it.polimi.db2.project.web.controllers.employee;
//
////import it.polimi.db2.project.ejb.entities.ProductEntity;
////import it.polimi.db2.project.ejb.exceptions.BadProductException;
////import it.polimi.db2.project.ejb.exceptions.BadQuestionnaireException;
//import it.polimi.db2.project.ejb.services.ServicePackageService;
//import it.polimi.db2.project.ejb.services.OptionalProductService;
//
//import org.apache.commons.text.StringEscapeUtils;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.WebContext;
//import org.thymeleaf.templatemode.TemplateMode;
//import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
//
//import javax.ejb.EJB;
//import javax.servlet.ServletContext;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.format.DateTimeParseException;
//import java.util.ArrayList;
//import java.util.List;
//
//@WebServlet(name = "EmployeeCreationServlet", value = "/employee/creation")
//public class CreationServlet extends HttpServlet {
//    private TemplateEngine templateEngine;
//
//    @EJB(name = "it.polimi.db2.project.ejb.services/ServicePackageService")
//    private ServicePackageService servicePackageService;
//
//    @EJB(name = "it.polimi.db2.project.ejb.services/OptionalProductService")
//    private OptionalProductService optionalProductService;
//
//    public void init() {
//        ServletContext servletContext = getServletContext();
//        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
//        templateResolver.setTemplateMode(TemplateMode.HTML);
//        this.templateEngine = new TemplateEngine();
//        this.templateEngine.setTemplateResolver(templateResolver);
//        templateResolver.setSuffix(".html");
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        resp.setContentType("text/html");
//
//        ServletContext servletContext = getServletContext();
//        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
//
////        List<ProductEntity> products = productService.findAllProducts();
//
////        ctx.setVariable("products", products);
//
//        String creationPath = "/WEB-INF/employee/homepage.html";
//        templateEngine.process(creationPath, ctx, resp.getWriter());
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
////        String date = StringEscapeUtils.escapeJava(req.getParameter("date"));
////        String product = StringEscapeUtils.escapeJava(req.getParameter("product"));
////
////        String[] ques = req.getParameterValues("question[]");
////
////        if (ques == null) {
////            resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Missing question.");
////            return;
////        }
////
////        List<String> questions = new ArrayList<>(List.of(ques));
////        questions.removeIf(String::isEmpty);
////        questions.forEach(StringEscapeUtils::escapeJava);
////
////        if (date == null || product == null || date.isEmpty() || product.isEmpty() || questions.isEmpty()) {
////            resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Missing questionnaire values.");
////            return;
////        }
////
////        LocalDate localDate;
////        // Checks that date is today or later.
////        try {
////            localDate = LocalDate.parse(date);
////        } catch (DateTimeParseException e) {
////            resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Invalid date. Please select a valid date.");
////            return;
////        }
////
////        if (localDate.compareTo(LocalDate.now()) < 0) {
////            resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Invalid date. Please select a valid date.");
////            return;
////        }
////
////        int productId;
////        try {
////            productId = Integer.parseInt(product);
////        } catch (NumberFormatException e) {
////            resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Invalid product id.  Please select a valid product.");
////            return;
////        }
////
////        try {
////            questionnaireService.addNewQuestionnaire(localDate, productId, questions);
////        } catch (BadProductException | BadQuestionnaireException e) {
////            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
////            return;
////        }
////
////        // Show success and give the possibility to create other questionnaires.
////        List<ProductEntity> products = productService.findAllProducts();
////
////        resp.setContentType("text/html");
////        ServletContext servletContext = getServletContext();
////        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
////
////        ctx.setVariable("success", true);
////        ctx.setVariable("products", products);
////
////        String path = "/WEB-INF/admin/creation.html";
////
////        templateEngine.process(path, ctx, resp.getWriter());
//    }
//}
