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
import java.util.ArrayList;
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

import br.com.api.ava.data.AlternativaRepository;
import br.com.api.ava.data.PerguntaRepository;
import br.com.api.ava.data.PesquisaRepository;
import br.com.api.ava.decorators.Secured;
import br.com.api.ava.model.Alternativa;
import br.com.api.ava.model.Pergunta;
import br.com.api.ava.model.Pesquisa;
import br.com.api.ava.model.Resposta;
import br.com.api.ava.model.SituacaoPublicacao;
import br.com.api.ava.service.PesquisaRegistration;
import br.com.api.ava.service.RespostaRegistration;
import br.com.api.ava.vo.AlternativaVO;
import br.com.api.ava.vo.DadosChartResultVO;
import br.com.api.ava.vo.PerguntaVO;
import br.com.api.ava.vo.PesquisaVO;
import br.com.api.ava.vo.RespostaVO;

@Path("/pesquisa")
@RequestScoped
public class PesquisaService {

    @Inject
    private Logger log;

    @Inject
    private PesquisaRepository repository;
    
    @Inject
    private PerguntaRepository perguntaRepository;
    
    @Inject
    private AlternativaRepository alternativaRepository;

    @Inject
    PesquisaRegistration registration;
    
    @Inject    
    RespostaRegistration respostaRegistration;

    @GET
    @Path("/publicadas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pesquisa> buscarPesquisasPublicadas() {
        return repository.buscarPesquisasPublicadas();
    }
    
    @GET
    @Path("/encerradas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pesquisa> buscarPesquisasEncerradas() {
    	return repository.buscarPesquisasEncerradas();
    }
    
    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pesquisa> listAll() {
        return repository.findAll();
    }

    @Secured
    @GET
    @Path("/minhasPesquisas/{idUsuario:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pesquisa> buscarMinhasPesquisa(@PathParam("idUsuario") long idUsuario) {
    	return repository.buscarMinhasPesquisas(idUsuario);
    }
    
    @Secured
    @GET
    @Path("/pesquisasDosAlunos/{idProf:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pesquisa> buscarPesquisasDosAlunos(@PathParam("idProf") long idProf) {
    	return repository.buscarPesquisasDosAlunos(idProf);
    }
    
    @Secured
    @GET
    @Path("/ver/{idPesquisa:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PesquisaVO> verPesquisaPorId(@PathParam("idPesquisa") long idPesquisa) {
    	List<PesquisaVO> pesquisasVO = new ArrayList<PesquisaVO>();
    	List<PerguntaVO> perguntasVO = new ArrayList<PerguntaVO>();
    	List<AlternativaVO> alternativasVO = new ArrayList<AlternativaVO>();
    	PesquisaVO pesquisaVO = null;
    	PerguntaVO perguntaVO = null;
    	AlternativaVO alternativaVO = null;
    	
    	
    	Pesquisa pesquisa = repository.findById(idPesquisa);
    	List<Pergunta> perguntas = new ArrayList<Pergunta>();
    	List<Alternativa> alternativas = new ArrayList<Alternativa>();
    	
		pesquisaVO = new PesquisaVO();
		pesquisaVO.setId(pesquisa.getId());
		pesquisaVO.setTitulo(pesquisa.getTitulo());
		pesquisaVO.setSubTitulo(pesquisa.getSubTitulo());
		pesquisaVO.setDataCriacao(pesquisa.getDataCriacao());
		pesquisaVO.setDataEncerramento(pesquisa.getDataEncerramento());
		
		perguntas = perguntaRepository.buscarPerguntasPorPesquisa(pesquisa.getId());
		
		for (Pergunta pergunta : perguntas) {
			perguntaVO = new PerguntaVO();
			perguntaVO.setId(pergunta.getId());
			perguntaVO.setDescricao(pergunta.getDescricao());
			perguntaVO.setDataCriacao(pergunta.getDataCriacao());
			
			alternativasVO = new ArrayList<AlternativaVO>();
			alternativas = alternativaRepository.buscarAlternativasPorPergunta(pergunta.getId());
			
			for (Alternativa alternativa : alternativas) {
				alternativaVO = new AlternativaVO();
				alternativaVO.setId(alternativa.getId());
				alternativaVO.setDescricao(alternativa.getDescricao());
				
				alternativasVO.add(alternativaVO);
			}
			perguntaVO.setAlternativas(alternativasVO);
			
			perguntasVO.add(perguntaVO);
		}
		
		pesquisaVO.setPerguntas(perguntasVO);
		
		
		pesquisasVO.add(pesquisaVO);
		
    	return pesquisasVO;
    }
    
    @GET
    @Path("/verPerguntas/{idPesquisa:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public PesquisaVO buscarPesquisaPorIdParaResponder(@PathParam("idPesquisa") long idPesquisa) {
    	PesquisaVO pesquisaVO = null;
    	List<PerguntaVO> perguntasVO = new ArrayList<PerguntaVO>();
    	List<AlternativaVO> alternativasVO = new ArrayList<AlternativaVO>();
    	PerguntaVO perguntaVO = null;
    	AlternativaVO alternativaVO = null;
    	
    	
    	Pesquisa pesquisa = repository.buscarPesquisaPorIdParaResponder(idPesquisa);
    	List<Pergunta> perguntas = new ArrayList<Pergunta>();
    	List<Alternativa> alternativas = new ArrayList<Alternativa>();
    	
		pesquisaVO = new PesquisaVO();
		pesquisaVO.setId(pesquisa.getId());
		pesquisaVO.setTitulo(pesquisa.getTitulo());
		pesquisaVO.setSubTitulo(pesquisa.getSubTitulo());
		pesquisaVO.setDataCriacao(pesquisa.getDataCriacao());
		pesquisaVO.setDataEncerramento(pesquisa.getDataEncerramento());
		
		perguntas = perguntaRepository.buscarPerguntasPorPesquisa(pesquisa.getId());
		
		for (Pergunta pergunta : perguntas) {
			perguntaVO = new PerguntaVO();
			perguntaVO.setId(pergunta.getId());
			perguntaVO.setDescricao(pergunta.getDescricao());
			perguntaVO.setDataCriacao(pergunta.getDataCriacao());
			
			alternativasVO = new ArrayList<AlternativaVO>();
			alternativas = alternativaRepository.buscarAlternativasPorPergunta(pergunta.getId());
			
			for (Alternativa alternativa : alternativas) {
				alternativaVO = new AlternativaVO();
				alternativaVO.setId(alternativa.getId());
				alternativaVO.setDescricao(alternativa.getDescricao());
				alternativaVO.setSelecionada(false);
				
				alternativasVO.add(alternativaVO);
			}
			perguntaVO.setAlternativas(alternativasVO);
			
			perguntasVO.add(perguntaVO);
		}
		
		pesquisaVO.setPerguntas(perguntasVO);
		
    	return pesquisaVO;
    }
    
    @Secured
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pesquisa buscarPesquisaPorId(@PathParam("id") long id) {
        Pesquisa pesquisa = repository.findById(id);
        if (pesquisa == null) {
        	log.log(Level.WARNING, "Pesquisa " + id + " não encontrado.");
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        pesquisa.getAutor().setLoginUsuario(null);
        
        return pesquisa;
    }

    @Secured
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarPesquisa(Pesquisa pesquisa) {

    	Response.ResponseBuilder builder = null;

        try {
        	
        	pesquisa.setSituacao(pegarEntidadeSituacaoRascunho());
        	pesquisa.setDataCriacao(Calendar.getInstance().getTime());
        	
            registration.register(pesquisa);

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
    
    @POST
    @Path("/responder/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response responderPesquisa(List<RespostaVO> respostas) {
    	String identificadorResposta = "";
    	Response.ResponseBuilder builder = null;
    	Resposta resposta = null;
        try {
        	
        	String s= Calendar.getInstance().getTime().toString();
            MessageDigest m =MessageDigest.getInstance("MD5");
            m.update(s.getBytes(),0,s.length());
            identificadorResposta = new BigInteger(1,m.digest()).toString(16);
        	
        	for (RespostaVO respostaVO : respostas) {
        		if (respostaVO == null) {
        			continue;
        		}
	        	resposta = new Resposta();
	        	resposta.setId(respostaVO.getId());
	        	resposta.setDescricao(identificadorResposta);
	        	
	        	Pergunta pergunta = perguntaRepository.findById(respostaVO.getPerguntaVO().getId());
	        	Alternativa alternativa = alternativaRepository.findById(respostaVO.getAlternativaVO().getId());
	        	
	        	resposta.setPergunta(pergunta);
	        	resposta.setAlternativa(alternativa);
	        	
	            respostaRegistration.register(resposta);
        	}
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
    public Response atualizarPesquisa(Pesquisa pesquisa) {

        Response.ResponseBuilder builder = null;

        try {

            registration.update(pesquisa);

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
    public Response deletarPesquisa(@PathParam("id") long id) {
    	
    	Response.ResponseBuilder builder = null;
    	Pesquisa pesquisa = null;
    	try {
    		
    		try {
    			
    			pesquisa = repository.findById(id);
    			
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				Map<String, String> responseObj = new HashMap<>();
	    		responseObj.put("error", e.getMessage());
	    		builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
			}
    		
    		registration.delete(pesquisa);
    		
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

	@GET
	@Path("/chart/{idPergunta1:[0-9][0-9]*}/{idPergunta2:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
	public DadosChartResultVO buscarResultadoChart(@PathParam("idPergunta1") Long idPergunta1, @PathParam("idPergunta2") Long idPergunta2) {
		DadosChartResultVO dados = new DadosChartResultVO();
		
		if (idPergunta1 != null && (idPergunta2 != null && idPergunta2.compareTo(0L) != 0 )) {
			dados = repository.buscarResultadoChart(idPergunta1, idPergunta2);
		} else if (idPergunta1 != null && (idPergunta2 == null || idPergunta2.compareTo(0L) == 0)) {
			dados = repository.buscarResultadoChart(idPergunta1);
		}
		
		return dados;
	}
	
}
