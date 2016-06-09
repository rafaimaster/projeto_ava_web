package br.com.api.ava.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.api.ava.model.Professor;

@Stateless
public class ProfessorRegistration {
//	@Inject
//	private Logger log;

	@Inject
	private EntityManager em;

	// @Inject
	// private Event<Member> memberEventSrc;

	public void register(Professor professor) {
		em.persist(professor);
		// memberEventSrc.fire(member);
	}

	public void update(Professor professor) {
		em.merge(professor);
	}

	public void delete(Professor professor) {
		em.remove(em.getReference(Professor.class, professor.getId()));
	}
}
