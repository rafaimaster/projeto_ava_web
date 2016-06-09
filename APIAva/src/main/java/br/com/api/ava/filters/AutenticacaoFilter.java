package br.com.api.ava.filters;

import java.io.IOException;
import java.util.Calendar;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import br.com.api.ava.data.ProfesssorRepository;
import br.com.api.ava.data.UsuarioRepository;
import br.com.api.ava.decorators.Secured;
import br.com.api.ava.model.Usuario;
import br.com.api.ava.service.LoginUsuarioRegistration;
import br.com.api.ava.service.ProfessorRegistration;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AutenticacaoFilter implements ContainerRequestFilter {

	private static final Integer MINUTOS_EXPIRACAO_TOKEN = 10;
	
	@Inject
	private UsuarioRepository repositoryUsuario;
	
	@Inject
	private LoginUsuarioRegistration loginUsuarioRegistration;
	
	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		// Get the HTTP Authorization header from the request
		String authorizationHeader = requestContext
				.getHeaderString(HttpHeaders.AUTHORIZATION);

		// Check if the HTTP Authorization header is present and formatted
		// correctly
		if (authorizationHeader == null
				|| !authorizationHeader.startsWith("Bearer ")) {
			throw new NotAuthorizedException(
					"Authorization header must be provided");
		}

		// Extract the token from the HTTP Authorization header
		String token = authorizationHeader.substring("Bearer".length()).trim();

		try {

			// Validate the token
			validateToken(token);

		} catch (Exception e) {
			requestContext.abortWith(Response.status(
					Response.Status.UNAUTHORIZED).build());
		}
	}

	private void validateToken(String token) throws Exception {
		Usuario usuario = repositoryUsuario.findByToken(token);
		
		/*
		if (!dataExpirou(usuario)) {
			throw new Exception();
		} else {
			usuario.getLoginUsuario().setDtGeracaoToken(Calendar.getInstance().getTime());
			loginUsuarioRegistration.update(usuario.getLoginUsuario());
		}
		*/
		if (!token.equals(usuario.getLoginUsuario().getToken())) {
			throw new Exception();
		}
	}

	private boolean dataExpirou(Usuario usuario) {
		Calendar dataAtual = Calendar.getInstance();
		Calendar dataToken = Calendar.getInstance();
		dataToken.setTime(usuario.getLoginUsuario().getDtGeracaoToken());
		dataToken.add(Calendar.MINUTE, MINUTOS_EXPIRACAO_TOKEN);
		
		System.out.println(dataToken.getTime());
		if (dataAtual.after(dataToken)) {
			return false;
		}
		
		return true;
	}

}
