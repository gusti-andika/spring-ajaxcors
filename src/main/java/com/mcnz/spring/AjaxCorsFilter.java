package com.mcnz.spring;

import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

public class AjaxCorsFilter extends CorsFilter {
	
	public static final String[] ALLOWED_HTTP_HEADERS = { "Origin", "Accept" ,"X-Requested-With", "Content-Type",
			"Authorization", "Access-Control-Request-Method", "Access-Control-Request-Headers" };

	public AjaxCorsFilter() {
		super(configurationSource());
	}

	private static UrlBasedCorsConfigurationSource configurationSource() {
		CorsConfiguration config = new CorsConfiguration();

		// origins
		config.addAllowedOrigin("*");

		// when using ajax: withCredentials: true, we require exact origin match
		config.setAllowCredentials(true);

		// headers
		for (String allowedHeader : ALLOWED_HTTP_HEADERS)
			config.addAllowedHeader(allowedHeader.toLowerCase());

		// methods
		config.addAllowedMethod(HttpMethod.OPTIONS);
		config.addAllowedMethod(HttpMethod.GET);
		config.addAllowedMethod(HttpMethod.POST);
		config.addAllowedMethod(HttpMethod.HEAD);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}