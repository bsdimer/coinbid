package com.madbid.admin.config;

import com.sun.faces.config.WebConfiguration;
import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.faces.webapp.FacesServlet;
import javax.servlet.*;
import java.util.EnumSet;

/**
 * Created by dimer.
 */
public class ApplicationConfig implements WebApplicationInitializer {
    private static final String FACES_SERVLET_NAME = "faces";
    private static final String FACES_SERVLET_MAPPING = "*.xhtml";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        XmlWebApplicationContext rootContext = new XmlWebApplicationContext();
        rootContext.setConfigLocation("classpath:applicationContext.xml");

        ServletRegistration.Dynamic facesServlet = servletContext.addServlet(FACES_SERVLET_NAME, new FacesServlet());
        facesServlet.setLoadOnStartup(1);
        facesServlet.addMapping(FACES_SERVLET_MAPPING);

        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", "true");
        servletContext.setInitParameter(WebConfiguration.WebContextInitParameter.JavaxFacesProjectStage.getQualifiedName(), "Development");
        servletContext.setInitParameter(WebConfiguration.BooleanWebContextInitParameter.FaceletsSkipComments.getQualifiedName(), "true");
        /*servletContext.setInitParameter(WebConfiguration.WebContextInitParameter.FaceletsLibraries.getQualifiedName(),
                "/WEB-INF/springsecurity.taglib.xml");*/

        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);


        FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncoding", characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");

        FilterRegistration.Dynamic security = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
        security.addMappingForUrlPatterns(dispatcherTypes, true, "/*");

        FilterRegistration.Dynamic primefacesUploadFilter = servletContext.addFilter("fileUploadFilter", new FileUploadFilter());
        primefacesUploadFilter.addMappingForServletNames(dispatcherTypes, true, FACES_SERVLET_NAME);

        servletContext.addListener(new ContextLoaderListener(rootContext));
    }
}


