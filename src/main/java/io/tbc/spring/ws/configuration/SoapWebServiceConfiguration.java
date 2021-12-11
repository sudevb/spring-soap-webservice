package io.tbc.spring.ws.configuration;

import java.util.List;
import java.util.Properties;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

@EnableWs
@Configuration
public class SoapWebServiceConfiguration extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(applicationContext);
        return new ServletRegistrationBean<>(messageDispatcherServlet, "/medium/ws/*");
    }

    @Bean(name = "calculatorDemo")
    public Wsdl11Definition wsdl11Definition() {
        SimpleWsdl11Definition simpleWsdl11Definition = new SimpleWsdl11Definition();
        simpleWsdl11Definition.setWsdl(new ClassPathResource("/wsdl/calculator.wsdl"));
        return simpleWsdl11Definition;
    }
    
    @Bean
    public SimplePasswordValidationCallbackHandler simplePasswordValidationCallbackHandler() {
        SimplePasswordValidationCallbackHandler simplePasswordValidationCallbackHandler = new SimplePasswordValidationCallbackHandler();
        Properties properties = new Properties();
        properties.setProperty("user", "password");
        properties.setProperty("admin", "password");
        simplePasswordValidationCallbackHandler.setUsers(properties);
        return simplePasswordValidationCallbackHandler;
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() {
        Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
        wss4jSecurityInterceptor.setValidationActions("UsernameToken");
        wss4jSecurityInterceptor.setValidationCallbackHandler(simplePasswordValidationCallbackHandler());
        return wss4jSecurityInterceptor;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(securityInterceptor());
    }
}
