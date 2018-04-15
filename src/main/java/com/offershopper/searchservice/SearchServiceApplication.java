package com.offershopper.searchservice;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;

import com.offershopper.searchservice.controller.App;

import brave.sampler.Sampler;

@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class SearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchServiceApplication.class, args);
		//App.setup();
		App.fullTextSearch("coffee", false, false);
	}
	
	@Bean
    public Sampler defaultSampler()
    {
      return Sampler.ALWAYS_SAMPLE;
     }

}

