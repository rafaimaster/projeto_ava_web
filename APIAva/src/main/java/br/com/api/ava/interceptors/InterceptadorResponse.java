package br.com.api.ava.interceptors;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class InterceptadorResponse implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext req,
			ContainerResponseContext resp) throws IOException {
		resp.getHeaders().add("Access-Control-Allow-Origin", "*");
		resp.getHeaders().add( "Access-Control-Allow-Credentials", "true" );
		resp.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, Authorization, accept, x-session-token");
		resp.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD");
	}

}
