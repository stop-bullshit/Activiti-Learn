package cn.masterlover.activiti6.conf;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan
public class ServletConfig {

    /**
     * description 为所有请求添加/app 前缀
     * params []
     * return org.springframework.boot.web.servlet.ServletRegistrationBean
     * create by heng.wang
     * date: 2018/12/21 17:39
     */
    @Bean
    public ServletRegistrationBean servletListenerRegistrationBean() {
        AnnotationConfigWebApplicationContext annotationConfigApplicationContext = new AnnotationConfigWebApplicationContext();
        annotationConfigApplicationContext.scan("org.activiti.app");

        DispatcherServlet dispatcherServlet = new DispatcherServlet(annotationConfigApplicationContext);
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet);
        servletRegistrationBean.setLoadOnStartup(1);
        servletRegistrationBean.addUrlMappings("/app/*");
        servletRegistrationBean.setName("app");
        return servletRegistrationBean;
    }
}
