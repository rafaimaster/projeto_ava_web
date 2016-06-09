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

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.api.ava.data.ProfesssorRepository;
import br.com.api.ava.decorators.Secured;
import br.com.api.ava.model.PerfilUsuario;
import br.com.api.ava.model.Professor;
import br.com.api.ava.service.LoginUsuarioRegistration;
import br.com.api.ava.service.ProfessorRegistration;

@Path("/professor")
@RequestScoped
public class ProfessorService {

    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private ProfesssorRepository repository;

    @Inject
    ProfessorRegistration registration;
    
    @Inject
    LoginUsuarioRegistration loginUsuarioRegistration;
    
    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Professor> listAll() {
        return repository.findAll();
    }

    @Secured
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Professor buscarUsuarioPorId(@PathParam("id") long id) {
        Professor professor = repository.findById(id);
        if (professor == null) {
        	log.log(Level.WARNING, "Professor " + id + " não encontrado.");
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return professor;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUsuario(Professor professor) {

        Response.ResponseBuilder builder = null;
        PerfilUsuario perfil = new PerfilUsuario();
        try {
        	
        	perfil.setId(1L);//perfil de professor
        	//set data cadastro
        	professor.setDtCadastro(Calendar.getInstance().getTime());
        	professor.setPerfilUsuario(perfil);
        	
            // Validates member using bean validation
        	validarUsuario(professor);
        	
        	//usuario.setPerfilUsuario(perfilUsuario);
        	registration.register(professor);

            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
        	log.log(Level.WARNING, ce.getMessage());
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException ve) {
        	log.log(Level.WARNING, ve.getMessage());
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", "O E-mail " + professor.getLoginUsuario().getEmail() + " já está cadastrado.");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
        	log.log(Level.SEVERE, e.getMessage());
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
   
    
    @Secured
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfessor(Professor usuario) {

        Response.ResponseBuilder builder = null;

        try {

        	registration.update(usuario);

            builder = Response.ok();
        } catch (Exception e) {
        	log.log(Level.SEVERE, e.getMessage());
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
    
    @Secured
    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUsuario(@PathParam("id") long id) {
    	
    	Response.ResponseBuilder builder = null;
    	Professor professor = null;
    	try {
    		
    		try {
    			
    			professor = repository.findById(id);
    			
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				Map<String, String> responseObj = new HashMap<>();
	    		responseObj.put("error", e.getMessage());
	    		builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
			}
    		
    		registration.delete(professor);
    		
    		loginUsuarioRegistration.delete(professor.getLoginUsuario());
    		
    		builder = Response.ok();
    	} catch (Exception e) {
    		log.log(Level.SEVERE, e.getMessage());
    		// Handle generic exceptions
    		Map<String, String> responseObj = new HashMap<>();
    		responseObj.put("error", e.getMessage());
    		builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    	}
    	
    	return builder.build();
    }
    
    
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        //log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put("error", violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }
    
    private void validarUsuario(Professor professor) throws ConstraintViolationException, ValidationException {
    	// Create a bean validator and check for issues.
    	Set<ConstraintViolation<Professor>> violations = validator.validate(professor);
    	
    	if (!violations.isEmpty()) {
    		throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
    	}
    	
    	// Check the uniqueness of the email address
    	if (emailAlreadyExists(professor.getLoginUsuario().getEmail())) {
    		throw new ValidationException("O E-mail " + professor.getLoginUsuario().getEmail() + " já está cadastrado.");
    	}
    }
    
    private boolean emailAlreadyExists(String email) {
        Professor professor = null;
        try {
        	professor = repository.findByEmail(email);
        } catch (NoResultException e) {
            // ignore
        }
        return professor != null;
    }

}
