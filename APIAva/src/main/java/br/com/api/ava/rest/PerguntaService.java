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

import java.util.ArrayList;
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

import br.com.api.ava.data.AlternativaRepository;
import br.com.api.ava.data.PerguntaRepository;
import br.com.api.ava.decorators.Secured;
import br.com.api.ava.model.Alternativa;
import br.com.api.ava.model.Pergunta;
import br.com.api.ava.service.AlternativaRegistration;
import br.com.api.ava.service.PerguntaRegistration;

@Secured
@Path("/pergunta")
@RequestScoped
public class PerguntaService {

    @Inject
    private Logger log;

    @Inject
    private Validator validator;
    
    @Inject
    private PerguntaRepository repository;
    
    @Inject
    private AlternativaRepository alternativaRepository;

    @Inject
    PerguntaRegistration registration;
    
    @Inject
    AlternativaRegistration alternativaRegistration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pergunta> listAll() {
        return repository.findAll();
    }

    @GET
    @Path("/{idPergunta:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pergunta buscarPorId(@PathParam("idPergunta") long idPergunta) {
    	return (Pergunta) repository.findById(idPergunta);
    }
    
    @GET
    @Path("/pesquisa/{idPesquisa:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pergunta> buscarPerguntaPorPesquisa(@PathParam("idPesquisa") long idPesquisa) {
    	return (ArrayList<Pergunta>) repository.buscarPerguntasPorPesquisa(idPesquisa);
    }

    @GET
    @Path("/{idPergunta:[0-9][0-9]*}/pesquisa/{idPesquisa:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pergunta buscarPergunta(@PathParam("idPergunta") long idPergunta, @PathParam("idPesquisa") long idPesquisa) {
    	Pergunta pergunta = repository.buscarPergunta(idPergunta, idPesquisa);
    	if (pergunta == null) {
    		log.log(Level.WARNING, "Pergunta " + idPergunta + " não encontrado para a pesquisa " + idPesquisa);
    		throw new WebApplicationException(Response.Status.NOT_FOUND);
    	}
    	return pergunta;
    }
    
   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPergunta(Pergunta pergunta) {

    	Response.ResponseBuilder builder = null;
    	Pergunta perguntaRetorno = null;
    	
        try {
        	
        	pergunta.setDataCriacao(Calendar.getInstance().getTime());
        	
        	validarPergunta(pergunta);
        	
        	perguntaRetorno = registration.update(pergunta);
        	perguntaRetorno.setPesquisa(null);
            builder = Response.ok(perguntaRetorno);
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
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBlog(Pergunta pergunta) {

        Response.ResponseBuilder builder = null;
        
        try {
        	
            registration.update(pergunta);

            builder = Response.ok();
        	
        } catch (Exception e) {
        	log.log(Level.SEVERE, e.getMessage());
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
    
    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePergunta(@PathParam("id") long id) {
    	
    	Response.ResponseBuilder builder = null;
    	Pergunta pergunta = null;
    	List<Alternativa> alternativas = null;
    	try {
			alternativas = alternativaRepository.buscarAlternativasPorPergunta(id);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			Map<String, String> responseObj = new HashMap<>();
    		responseObj.put("error", "Ocorreu um erro ao buscar as alternativas da pergunta " + id + " " + e.getMessage());
    		builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		}
    	
    	try {
			for (Alternativa alternativa : alternativas) {
				alternativaRegistration.delete(alternativa);
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			Map<String, String> responseObj = new HashMap<>();
    		responseObj.put("error", "Ocorreu um erro ao remover as alternativas da pergunta " + id + " " + e.getMessage());
    		builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		}
    	
    	try {
    		
    		try {
    			
    			pergunta = repository.findById(id);
    			
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				Map<String, String> responseObj = new HashMap<>();
	    		responseObj.put("error", e.getMessage());
	    		builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
			}
    		
    		registration.delete(pergunta);
    		
    		builder = Response.ok();
    	} catch (Exception e) {
    		log.log(Level.SEVERE, e.getMessage());
    		Map<String, String> responseObj = new HashMap<>();
    		responseObj.put("error", e.getMessage());
    		builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    	}
    	
    	return builder.build();
    }

    
    private void validarPergunta(Pergunta pergunta) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Pergunta>> violations = validator.validate(pergunta);

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
