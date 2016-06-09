package br.com.api.ava.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.api.ava.model.AvaliacaoPesquisa;

@Stateless
public class AvaliacaoPesquisaRegistration {

	@Inject
	private EntityManager em;


	public void register(AvaliacaoPesquisa avaliacaoPesquisa) {
		em.persist(avaliacaoPesquisa);
	}

	public void update(AvaliacaoPesquisa avaliacaoPesquisa) {
		em.merge(avaliacaoPesquisa);
	}

	public void delete(AvaliacaoPesquisa avaliacaoPesquisa) {
		em.remove(em.getReference(AvaliacaoPesquisa.class, avaliacaoPesquisa.getId()));
	}
}
