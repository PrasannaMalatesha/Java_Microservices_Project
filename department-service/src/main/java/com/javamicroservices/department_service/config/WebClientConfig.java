package com.javamicroservices.department_service.config;

import com.javamicroservices.department_service.client.EmployeeClient;
import com.javamicroservices.department_service.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;

import org.springframework.web.service.invoker.HttpServiceProxyFactory;


import static org.springframework.web.reactive.function.client.support.WebClientAdapter.*;

@Configuration
public class WebClientConfig {

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    public WebClient employeeWebClient(){

        return WebClient.builder().baseUrl("http://employee-service").filter(filterFunction).build();

    }

    @Bean
    public EmployeeClient employeeClient() {

        //Changed a bit
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(employeeWebClient()))
                .build();

        return httpServiceProxyFactory.createClient(EmployeeClient.class);

    }

}
