package com.mcnz.spring;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MyHttpRequestHandler implements HttpRequestHandler {
	Map<String, String> validUsers = new HashMap<>();

	public MyHttpRequestHandler() {
		validUsers.put("admin:", "authorized");
	}

	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * setAccessControlHeaders(request, response);
		 * 
		 * // pre-flight for basic auth if
		 * (request.getMethod().equalsIgnoreCase("OPTIONS")) {
		 * response.setStatus(response.SC_OK); return; }
		 */
		
		String auth = request.getHeader("Authorization");
		// Do we allow that user?
		if (!allowUser(auth)) {
			// Not allowed, so report he's unauthorized
			response.setHeader("WWW-Authenticate", "BASIC realm=\"appuntivari test\"");
			response.sendError(response.SC_UNAUTHORIZED);
			// Could offer to add him to the allowed user list
		} else {
			PrintWriter writer = response.getWriter();
			// writer.write("response from MyHttpRequestHandler, uri: " +
			// request.getRequestURI());

			Map<String, List<String>> map = new HashMap<>();

			map.put("tes1", Arrays.asList("a", "b", "c"));
			map.put("tes2", Arrays.asList("d", "e", "f"));
			ObjectMapper mapper = new ObjectMapper();

			writer.write(mapper.writeValueAsString(map));
		}
	}

	protected boolean allowUser(String auth) throws IOException {

		if (auth == null) {
			System.out.println("No Auth");
			return false;
		}
		if (!auth.toUpperCase().startsWith("BASIC ")) {
			System.out.println("Only Accept Basic Auth");
			return false;
		}

		// Get encoded user and password, comes after "BASIC "
		String userpassEncoded = auth.substring(6);
		// Decode it, using any base 64 decoder
		Decoder dec = Base64.getDecoder();
		String userpassDecoded = new String(dec.decode(userpassEncoded));

		// Check our user list to see if that user and password are "allowed"
		if ("authorized".equals(validUsers.get(userpassDecoded))) {
			return true;
		} else {
			return false;
		}
	}

	private void setAccessControlHeaders(HttpServletRequest request, HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		resp.setHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
	}
}