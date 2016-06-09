package br.com.api.ava.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.api.ava.model.Pergunta;

@Stateless
public class PerguntaRegistration {

	@Inject
	private EntityManager em;

	public void register(Pergunta pergunta) {
		em.persist(pergunta);
	}

	public Pergunta update(Pergunta pergunta) {
		return em.merge(pergunta);
	}

	public void delete(Pergunta pergunta) {
		em.remove(em.getReference(Pergunta.class, pergunta.getId()));
	}
}
