package br.com.api.ava.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.api.ava.model.Alternativa;

@Stateless
public class AlternativaRegistration {

	@Inject
	private EntityManager em;

	public void register(Alternativa alternativa) {
		em.persist(alternativa);
	}

	public void update(Alternativa alternativa) {
		em.merge(alternativa);
	}

	public void delete(Alternativa alternativa) {
		em.remove(em.getReference(Alternativa.class, alternativa.getId()));
	}
}
