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

import br.com.api.ava.data.AlunoRepository;
import br.com.api.ava.decorators.Secured;
import br.com.api.ava.model.Aluno;
import br.com.api.ava.model.PerfilUsuario;
import br.com.api.ava.service.AlunoRegistration;
import br.com.api.ava.service.LoginUsuarioRegistration;
import br.com.api.ava.service.TurmaRegistration;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the members table.
 */
@Path("/aluno")
@RequestScoped
public class AlunoService {

    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private AlunoRepository repository;
    
    @Inject
    AlunoRegistration registration;
    
    @Inject
    LoginUsuarioRegistration loginUsuarioRegistration;
    
    @Inject
    TurmaRegistration turmaRegistration;

    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Aluno> listAll() {
        return repository.findAll();
    }

    @Secured
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Aluno buscarUsuarioPorId(@PathParam("id") long id) {
        Aluno aluno = repository.findById(id);
        if (aluno == null) {
        	log.log(Level.WARNING, "Aluno " + id + " não encontrado.");
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return aluno;
    }

    @Secured
    @GET
    @Path("/turma/{idTurma:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Aluno> buscarAlunosPorTurma(@PathParam("idTurma") long idTurma) {
    	List<Aluno> alunos = repository.buscarAlunoPorTurma(idTurma);
        if (alunos == null) {
        	log.log(Level.WARNING, "Alunos não encontrados para a turma " + idTurma);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        for (Aluno aluno : alunos) {
			aluno.setLoginUsuario(null);
		}
        
        return alunos;
    }
    @Secured
    @GET
    @Path("/{idAluno:[0-9][0-9]*}/turma/{idTurma:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Aluno buscarAluno(@PathParam("idAluno") long idAluno, @PathParam("idTurma") long idTurma) {
    	Aluno aluno = repository.buscarAluno(idTurma, idAluno);
    	if (aluno == null) {
    		log.log(Level.WARNING, "Aluno " + idAluno + " não encontrado para a turma " + idTurma);
    		throw new WebApplicationException(Response.Status.NOT_FOUND);
    	}
    	return aluno;
    }
    
   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUsuario(Aluno aluno) {
    	
    	Response.ResponseBuilder builder = null;
    	PerfilUsuario perfil = new PerfilUsuario();
    	try {
    		
    		perfil.setId(2L);//perfil de aluno
    		//set data cadastro
    		aluno.setDtCadastro(Calendar.getInstance().getTime());
    		aluno.setPerfilUsuario(perfil);
    		
    		// Validates member using bean validation
    		validarUsuario(aluno);
    		
    		//usuario.setPerfilUsuario(perfilUsuario);
    		registration.register(aluno);
    		
    		// Create an "ok" response
    		builder = Response.ok();
    	} catch (ConstraintViolationException ce) {
    		log.log(Level.WARNING, ce.getMessage());
    		builder = createViolationResponse(ce.getConstraintViolations());
    	} catch (ValidationException ve) {
    		log.log(Level.WARNING, ve.getMessage());
    		Map<String, String> responseObj = new HashMap<>();
    		responseObj.put("error", "O E-mail " + aluno.getLoginUsuario().getEmail() + " já está cadastrado.");
    		builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
    	} catch (Exception e) {
    		log.log(Level.SEVERE, e.getMessage());
    		Map<String, String> responseObj = new HashMap<>();
    		responseObj.put("error", e.getMessage());
    		builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    	}
    	
    	return builder.build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAluno(Aluno aluno) {
    	
    	Response.ResponseBuilder builder = null;
    	try {
    		
    		// Validates member using bean validation
    		validarUpdateAluno(aluno);
    		
    		//usuario.setPerfilUsuario(perfilUsuario);
    		registration.update(aluno);
    		
    		// Create an "ok" response
    		builder = Response.ok();
    	} catch (ConstraintViolationException ce) {
    		log.log(Level.WARNING, ce.getMessage());
    		builder = createViolationResponse(ce.getConstraintViolations());
    	} catch (Exception e) {
    		log.log(Level.SEVERE, e.getMessage());
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
    public Response deleteAluno(@PathParam("id") long id) {
    	
    	Response.ResponseBuilder builder = null;
    	Aluno aluno = null;
    	try {
    		
    		try {
    			
    			aluno = repository.findById(id);
    			
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				Map<String, String> responseObj = new HashMap<>();
	    		responseObj.put("error", e.getMessage());
	    		builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
			}
    		
    		registration.delete(aluno);
    		
    		loginUsuarioRegistration.delete(aluno.getLoginUsuario());
    		
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
    
    private void validarUpdateAluno(Aluno aluno) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Aluno>> violations = validator.validate(aluno);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }
    
    private void validarUsuario(Aluno aluno) throws ConstraintViolationException, ValidationException {
    	// Create a bean validator and check for issues.
    	Set<ConstraintViolation<Aluno>> violations = validator.validate(aluno);
    	
    	if (!violations.isEmpty()) {
    		throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
    	}
    	
    	// Check the uniqueness of the email address
    	if (emailAlreadyExists(aluno.getLoginUsuario().getEmail())) {
    		throw new ValidationException("O E-mail " + aluno.getLoginUsuario().getEmail() + " já está cadastrado.");
    	}
    }
    
    private boolean emailAlreadyExists(String email) {
        Aluno aluno = null;
        try {
        	aluno = repository.findByEmail(email);
        } catch (NoResultException e) {
            // ignore
        }
        return aluno != null;
    }

}
