package br.com.api.ava.interceptors;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class InterceptadorRequest implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext req) throws IOException {
		req.getHeaders().add("Access-Control-Allow-Origin", "*");
		req.getHeaders().add( "Access-Control-Allow-Credentials", "true" );
		req.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, Authorization, accept, x-session-token");
		req.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD");
		System.out.println(req.getHeaderString("x-session-token"));
		
//		String token = (req.getHeaderString("x-session-token") != null) ? req.getHeaderString("x-session-token") : "";
//		
//		if (!token.equals("tokenteste")) {
//			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
//		}
	}
}
