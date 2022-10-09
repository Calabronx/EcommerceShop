package com.factorit.EcommerceShop.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class SoapConfiguration extends WsConfigurerAdapter {
//    @Bean
//    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
//        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
//        servlet.setApplicationContext(applicationContext);
//        servlet.setTransformWsdlLocations(true);
//        return new ServletRegistrationBean(servlet, "/service/*");
//    }
//
//    @Bean(name = "shoppingCartDetailsWsdl")
//    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
//        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
//        wsdl11Definition.setPortTypeName("ShoppingCartDetailsPort");
//        wsdl11Definition.setLocationUri("/service/shoppingcart-details");
//        wsdl11Definition.setTargetNamespace("http://localhost:8080/api/v1/cartstatus/{id}");
//        wsdl11Definition.setSchema(schema);
//        return wsdl11Definition;
//    }
//
//    @Bean
//    public XsdSchema shoppingCartSchema() {
//        return new SimpleXsdSchema(new ClassPathResource("shoppingcart.xsd"));
//    }
}
