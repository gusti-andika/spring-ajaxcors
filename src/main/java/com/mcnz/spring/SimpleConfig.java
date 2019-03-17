package com.mcnz.spring;

import javax.servlet.Filter;

import org.ebaysf.web.cors.CORSFilter;
import org.springframework.context.annotation.*;
import org.springframework.web.HttpRequestHandler;

@Configuration
public class SimpleConfig {

	@Bean()
	@Scope(value = "session")
	public ClickCounter clickCounter() {
		return new ClickCounter();
	}

	@Bean(name = "example")
	public HttpRequestHandler httpRequestHandler() {
		return new MyHttpRequestHandler();
	}
	
	@Bean
	public Filter corsFilter() {
		return new CORSFilter();
	}
	
	@Bean
	public Filter ajaxCorsFilter() {
		return new AjaxCorsFilter();
	}
}