package br.com.api.ava.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.api.ava.model.Turma;

@Stateless
public class TurmaRegistration {
//	@Inject
//	private Logger log;

	@Inject
	private EntityManager em;

	// @Inject
	// private Event<Member> memberEventSrc;

	public void register(Turma turma) {
		em.persist(turma);
		// memberEventSrc.fire(member);
	}

	public void update(Turma turma) {
		em.merge(turma);
	}

	public void delete(Turma turma) {
		em.remove(em.getReference(Turma.class, turma.getId()));
	}
}
