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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.api.ava.data.AvaliacaoPesquisaRepository;
import br.com.api.ava.decorators.Secured;
import br.com.api.ava.model.AvaliacaoPesquisa;
import br.com.api.ava.service.AvaliacaoPesquisaRegistration;

@Path("/avaliacaoPesquisa")
@RequestScoped
public class AvaliacaoPesquisaService {

    @Inject
    private Logger log;

    @Inject
    private Validator validator;
    
    @Inject
    private AvaliacaoPesquisaRepository repository;

    @Inject
    AvaliacaoPesquisaRegistration registration;

    @Secured
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public AvaliacaoPesquisa buscarPorId(@PathParam("id") long id) {
    	return (AvaliacaoPesquisa) repository.findById(id);
    }
    
    @GET
    @Path("/pesquisa/{idPesquisa:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public AvaliacaoPesquisa buscarPorIdPesquisa(@PathParam("idPesquisa") long idPesquisa) {
    	return (AvaliacaoPesquisa) repository.buscarPorIdPesquisa(idPesquisa);
    }

    @Secured
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAvaliacaoPesquisa(AvaliacaoPesquisa avaliacaoPesquisa) {

    	Response.ResponseBuilder builder = null;

        try {
        	
        	validarAvaliacaoPesquisa(avaliacaoPesquisa);
        	
            registration.register(avaliacaoPesquisa);

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
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAvaliacaoPesquisa(AvaliacaoPesquisa avaliacaoPesquisa) {

        Response.ResponseBuilder builder = null;
        
        try {
        	
            registration.update(avaliacaoPesquisa);

            builder = Response.ok();
        	
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
    public Response deleteAvaliacaoPesquisa(@PathParam("id") long id) {
    	
    	Response.ResponseBuilder builder = null;
    	AvaliacaoPesquisa avaliacaoPesquisa = null;
    	try {
    		
    		try {
    			
    			avaliacaoPesquisa = repository.findById(id);
    			
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				Map<String, String> responseObj = new HashMap<>();
	    		responseObj.put("error", e.getMessage());
	    		builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
			}
    		
    		registration.delete(avaliacaoPesquisa);
    		
    		builder = Response.ok();
    	} catch (Exception e) {
    		log.log(Level.SEVERE, e.getMessage());
    		Map<String, String> responseObj = new HashMap<>();
    		responseObj.put("error", e.getMessage());
    		builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    	}
    	
    	return builder.build();
    }

    
    private void validarAvaliacaoPesquisa(AvaliacaoPesquisa avaliacaoPesquisa) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<AvaliacaoPesquisa>> violations = validator.validate(avaliacaoPesquisa);

        if (!violations.isEmpty()) {
        	log.log(Level.SEVERE, violations.toString());
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }
    
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        //log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put("error", violation.getPropertyPath().toString() + " : " + violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }
    
}
