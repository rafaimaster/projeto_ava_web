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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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

import br.com.api.ava.data.BlogRepository;
import br.com.api.ava.decorators.Secured;
import br.com.api.ava.model.Blog;
import br.com.api.ava.model.SituacaoPublicacao;
import br.com.api.ava.service.BlogRegistration;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the members table.
 */
@Path("/blog")
@RequestScoped
public class BlogService {

    @Inject
    private Logger log;

//    @Inject
//    private Validator validator;

    @Inject
    private BlogRepository repository;

    @Inject
    BlogRegistration registration;

    
    @GET
    @Path("/publicados")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Blog> buscarPostsPublicados() {
        return repository.buscarPostsPublicados();
    }
    
    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Blog> listAll() {
        return repository.findAll();
    }

    @Secured
    @GET
    @Path("/meusPosts/{idUsuario:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Blog> buscarMeusPosts(@PathParam("idUsuario") long idUsuario) {
    	return repository.buscarMeusPosts(idUsuario);
    }
    
    @Secured
    @GET
    @Path("/postsDosAlunos/{idProf:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Blog> buscarPostsDosAlunos(@PathParam("idProf") long idProf) {
    	return repository.buscarPostsDosAlunos(idProf);
    }
    
    @Secured
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Blog buscarBlogPorId(@PathParam("id") long id) {
        Blog blog = repository.findById(id);
        if (blog == null) {
        	log.log(Level.WARNING, "Blog " + id + " não encontrado.");
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return blog;
    }

   
    @Secured
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBlog(Blog blog) {

    	Response.ResponseBuilder builder = null;

        try {
            // Validates member using bean validation
            //validateMember(blog);
//        	Usuario user = new Usuario();
//        	user.setId(1L);
//        	user.setNome("Rafael Dornelles Lima");
//        	blog.setAutor(user);
        	
        	blog.setSituacaoBlog(pegarEntidadeSituacaoRascunho());
        	blog.setDataCriacao(Calendar.getInstance().getTime());
        	
            registration.register(blog);

            // Create an "ok" response
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

    
	private SituacaoPublicacao pegarEntidadeSituacaoRascunho() {
		SituacaoPublicacao situacao = new SituacaoPublicacao();
		situacao.setId(1L);
		situacao.setDescricao("Rascunho");
		situacao.setSituacao(true);
		return situacao;
	}
    
	@Secured
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBlog(Blog blog) {

        Response.ResponseBuilder builder = null;

        try {

            registration.update(blog);

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
    public Response deleteBlog(@PathParam("id") long id) {
    	
    	Response.ResponseBuilder builder = null;
    	Blog blog = null;
    	try {
    		
    		try {
    			
    			blog = repository.findById(id);
    			
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				Map<String, String> responseObj = new HashMap<>();
	    		responseObj.put("error", e.getMessage());
	    		builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
			}
    		
    		registration.delete(blog);
    		
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

}
