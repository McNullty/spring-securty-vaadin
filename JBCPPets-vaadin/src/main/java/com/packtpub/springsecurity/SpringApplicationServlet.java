package com.packtpub.springsecurity;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

public class SpringApplicationServlet extends AbstractApplicationServlet {

	private static final long serialVersionUID = -6861856553187461535L;
	private static Logger log = LoggerFactory
			.getLogger(SpringApplicationServlet.class);

	private WebApplicationContext applicationContext;
	private Class<? extends Application> applicationClass;
	private String applicationBean;

	@SuppressWarnings("unchecked")
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		log.debug("init()");

		super.init(servletConfig);
		applicationBean = servletConfig.getInitParameter("applicationBean");
		if (applicationBean == null) {
			throw new ServletException(
					"ApplicationBean not specified in servlet parameters");
		}
		applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(servletConfig.getServletContext());
		applicationClass = (Class<? extends Application>) applicationContext
				.getType(applicationBean);
	}

	@Override
	protected Application getNewApplication(HttpServletRequest request)
			throws ServletException {
		log.trace("getNewApplication()");
		return (Application) applicationContext.getBean(applicationBean);
	}

	@Override
	protected Class<? extends Application> getApplicationClass()
			throws ClassNotFoundException {
		log.trace("getApplicationClass()");
		return applicationClass;
	}

}
