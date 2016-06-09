/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.api.ava.rest;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.api.ava.data.LoginUsuarioRepository;
import br.com.api.ava.data.UsuarioRepository;
import br.com.api.ava.model.LoginUsuario;
import br.com.api.ava.model.Usuario;
import br.com.api.ava.service.LoginUsuarioRegistration;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the
 * members table.
 */
@Path("/loginUsuario")
@RequestScoped
public class LoginUsuarioService {

	@Inject
	private Logger log;

	// @Inject
	// private Validator validator;

	@Inject
	private LoginUsuarioRepository repository;

	@Inject
	private UsuarioRepository repositoryUsuario;

	@Inject
	LoginUsuarioRegistration registration;

	/**
	 * Creates a new member from the values provided. Performs validation, and
	 * will return a JAX-RS response with either 200 ok, or with a map of
	 * fields, and related errors.
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(LoginUsuario loginUsuario) {

		Response.ResponseBuilder builder = null;

		LoginUsuario login = null;
		Usuario usuario = null;

		try {

			login = repository.findByEmail(loginUsuario.getEmail());

			if (!login.getSenha().equals(loginUsuario.getSenha())) {
				Map<String, String> responseObj = new HashMap<>();
				responseObj.put("error", "Usuário/senha inválidos.");
				builder = Response.status(Response.Status.UNAUTHORIZED).entity(
						responseObj);

				return builder.build();
			}

		} catch (NoResultException e) {
			log.log(Level.WARNING, e.getMessage());
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", "Usuário não encontrado");
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
			return builder.build();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error",
					"Ocorreu um erro ao recuperar as informações de login. "
							+ e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
			return builder.build();
		}

		try {

			// set data cadastro
			login.setToken(getToken(loginUsuario.getEmail()));
			login.setDtGeracaoToken(Calendar.getInstance().getTime());

			// Validates member using bean validation
			// validarUsuario(usuario);

			// usuario.setPerfilUsuario(perfilUsuario);
			registration.update(login);

			// Create an "ok" response
			builder = Response.ok();
		} catch (ValidationException ve) {
			log.log(Level.WARNING, ve.getMessage());
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", ve.getMessage());
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
			return builder.build();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error",
					"Ocorreu um erro ao atualizar o token de autenticação. "
							+ e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
			return builder.build();
		}

		try {
			usuario = repositoryUsuario.findById(login.getId());
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error",
					"Ocorreu um erro ao recuperar as informações do usuário. "
							+ e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
			return builder.build();
		}
		usuario.getLoginUsuario().setSenha(null);
		return builder.entity(usuario).build();
	}

	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(LoginUsuario loginUsuario) {
		
		Response.ResponseBuilder builder = null;
		LoginUsuario login;
		
		try {
			
			login = repository.findByEmail(loginUsuario.getEmail());
			
			try {
				
				login.setToken(null);
				login.setDtGeracaoToken(null);
		
				registration.update(login);
				
				builder = Response.ok();

			} catch (ValidationException ve) {
				throw ve;
			} catch (Exception e) {
				throw e;
			}
			
		} catch (NoResultException e) {
			log.log(Level.WARNING, e.getMessage());
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", "Usuário não encontrado");
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (ValidationException ve) {
			log.log(Level.WARNING, ve.getMessage());
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", ve.getMessage());
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error",
					"Ocorreu um erro ao recuperar as informações de login. "
							+ e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}
		
		return builder.build();
	}

	private String getToken(String email) {

		MessageDigest m = null;
		String md5hash = null;
		String str  = email + Calendar.getInstance().getTimeInMillis();
		try {
			m = MessageDigest.getInstance ("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		if (m != null) {
			m.update(str.getBytes(), 0, str.length());
			md5hash = new BigInteger(1,m.digest()).toString(16);
		}
		return md5hash;

	}
	
}
