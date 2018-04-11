package com.mytest.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {

	private Logger logger = LoggerFactory.getLogger(AppInitializer.class);

	public void onStartup(ServletContext container) throws ServletException {

		try {

			AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
			ctx.register(SpringWebConfig.class);
			ctx.setServletContext(container);
			container.addListener(new ContextLoaderListener(ctx));
			container.addListener(new RequestContextListener());
			logger.info("Created AnnotationConfigWebApplicationContext");

			ServletRegistration.Dynamic dispatcher = container.addServlet("spring-mvc-dispatcher",
					new DispatcherServlet(ctx));
			dispatcher.setLoadOnStartup(1);
			dispatcher.addMapping("/dwr/*");
			logger.info("DispatcherServlet added to AnnotationConfigWebApplicationContext");

			// ServletRegistration.Dynamic servlet = container.addServlet("login", new
			// com.mycompany.ad.UserLoginServlet());
			//
			// servlet.setLoadOnStartup(1);
			// servlet.addMapping("/login/*");
			// logger.info("UserLoginServlet added to
			// AnnotationConfigWebApplicationContext");
			//
			// ServletRegistration.Dynamic dwr = container.addServlet("dwr", new
			// org.directwebremoting.servlet.DwrServlet());
			// dwr.setInitParameter("debug", "true");
			// dwr.setLoadOnStartup(2);
			// dwr.addMapping("/dwr/*");
			logger.info("DWR Servlet Mapping Created");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}

	}

}