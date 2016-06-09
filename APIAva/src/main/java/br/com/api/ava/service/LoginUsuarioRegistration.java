package br.com.api.ava.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.api.ava.model.LoginUsuario;

@Stateless
public class LoginUsuarioRegistration {
	
	@Inject
	private EntityManager em;

	public void update(LoginUsuario loginUsuario) {
		em.merge(loginUsuario);
	}
	
	public void delete(LoginUsuario loginUsuario) {
		em.remove(em.getReference(LoginUsuario.class, loginUsuario.getId()));
	}
}
