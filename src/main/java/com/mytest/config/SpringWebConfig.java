package com.mytest.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.GlobalFilter;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.extend.Configurator;
import org.directwebremoting.spring.DwrClassPathBeanDefinitionScanner;
import org.directwebremoting.spring.DwrController;
import org.directwebremoting.spring.DwrHandlerMapping;
import org.directwebremoting.spring.SpringConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

import com.mytest.bean.SBean;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.mytest" })
//@PropertySource(value = { "classpath:application.properties" })
public class SpringWebConfig extends WebMvcConfigurerAdapter {

	private Logger logger = LoggerFactory.getLogger(SpringWebConfig.class);

	@Bean
	public DwrController dwrController(ApplicationContext applicationContext) {
		logger.info("Starting dwrController Bean");
		BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) applicationContext
				.getAutowireCapableBeanFactory();
		Map<String, String> configParam = new HashMap<String, String>();

		logger.info("Configuring scanners for DWR Bean");

		ClassPathBeanDefinitionScanner scanner = new DwrClassPathBeanDefinitionScanner(beanDefinitionRegistry);
		scanner.addIncludeFilter(new AnnotationTypeFilter(RemoteProxy.class));
		scanner.addIncludeFilter(new AnnotationTypeFilter(DataTransferObject.class));
		scanner.addIncludeFilter(new AnnotationTypeFilter(GlobalFilter.class));
		scanner.scan("com.mytest.bean");

		logger.info("Instantiating DwrController instance");
		DwrController dwrController = new DwrController();
		dwrController.setDebug(true);
		dwrController.setConfigParams(configParam);

		logger.info("Setting up SpringConfigurator for dwrController");
		SpringConfigurator springConfigurator = new SpringConfigurator();
		List<Configurator> configurators = new ArrayList<Configurator>();
		configurators.add(springConfigurator);
		dwrController.setConfigurators(configurators);

		logger.info("dwrController ready.");
		return dwrController;
	}

	@Bean
	public BeanNameUrlHandlerMapping beanNameUrlHandlerMapping() {
		logger.info("Setting up beanNameUrlHandlerMapping");
		BeanNameUrlHandlerMapping beanNameUrlHandlerMapping = new BeanNameUrlHandlerMapping();
		logger.info("beanNameUrlHandlerMapping ready.");
		return beanNameUrlHandlerMapping;
	}

	@Bean
	public DwrHandlerMapping dwrHandlerMapping(DwrController dwrController) {
		logger.info("Setting up dwrHandlerMapping");
		Map<String, DwrController> urlMap = new HashMap<String, DwrController>();
		urlMap.put("/dwr/**/*", dwrController);

		DwrHandlerMapping dwrHandlerMapping = new DwrHandlerMapping();
		dwrHandlerMapping.setAlwaysUseFullPath(true);
		dwrHandlerMapping.setUrlMap(urlMap);
		logger.info("dwrHandlerMappying ready.");
		return dwrHandlerMapping;
	}

	// @Bean(name="sBean")
	// public SBean sBean() {
	// logger.info("SBean starting");
	// return new SBean();
	// }

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
		logger.info("DefaultServletHandlerConfigurer enabled");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// not using an interceptor
	}

}