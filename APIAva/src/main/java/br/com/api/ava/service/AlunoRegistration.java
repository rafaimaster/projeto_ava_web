package br.com.api.ava.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.api.ava.model.Aluno;

@Stateless
public class AlunoRegistration {
//	@Inject
//	private Logger log;

	@Inject
	private EntityManager em;

	// @Inject
	// private Event<Member> memberEventSrc;

	public void register(Aluno aluno) {
		em.persist(aluno);
		// memberEventSrc.fire(member);
	}

	public void update(Aluno aluno) {
		em.merge(aluno);
	}

	public void delete(Aluno aluno) {
		em.remove(em.getReference(Aluno.class, aluno.getId()));
	}
}
