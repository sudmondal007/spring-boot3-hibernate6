package com.home.boot.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

/**
 * Tiles configuration.
 */
@Configuration
@EnableWebMvc
@ComponentScan({ "com.home.boot" })
public class WebFrameworkConfiguration implements WebMvcConfigurer,  ApplicationContextAware {
	
	private ApplicationContext webApplicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.webApplicationContext = applicationContext;
    }
	
	@Bean
	public SpringResourceTemplateResolver thymeleafTemplateResolver(){
	    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
	    templateResolver.setApplicationContext(webApplicationContext);
	    templateResolver.setOrder(9);
	    templateResolver.setPrefix("/WEB-INF/tlviews/");
	    templateResolver.setSuffix("");
	    templateResolver.setTemplateMode(TemplateMode.HTML);
	    return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
	    SpringTemplateEngine springTemplateEngine= new SpringTemplateEngine();
	    springTemplateEngine.addDialect(new LayoutDialect());
	    springTemplateEngine.setTemplateResolver(thymeleafTemplateResolver());
	    springTemplateEngine.setEnableSpringELCompiler(true);
	    return springTemplateEngine;
	}

	@Bean
	public ThymeleafViewResolver thymeleafViewResolver(){
	    final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	    viewResolver.setViewNames(new String[] {"*.html"});
	    viewResolver.setExcludedViewNames(new String[] {"*.jsp"});
	    viewResolver.setTemplateEngine(templateEngine());
	    viewResolver.setCharacterEncoding("UTF-8");
	    return viewResolver;
	}

	@Bean
    public InternalResourceViewResolver jspViewResolver() {
    	final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    	viewResolver.setOrder(10);
    	viewResolver.setViewClass(JstlView.class);
    	viewResolver.setPrefix("/WEB-INF/jsp/");
    	viewResolver.setSuffix("");
    	viewResolver.setViewNames("*.jsp");
    	return viewResolver;
    }
}
