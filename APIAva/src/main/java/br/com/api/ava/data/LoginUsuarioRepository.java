package br.com.api.ava.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.api.ava.model.LoginUsuario;

@ApplicationScoped
public class LoginUsuarioRepository {

	@Inject
    private EntityManager em;
	
	public LoginUsuario findByEmail(String email) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LoginUsuario> criteria = cb.createQuery(LoginUsuario.class);
        Root<LoginUsuario> loginUsuario = criteria.from(LoginUsuario.class);
        criteria.select(loginUsuario).where(cb.equal(loginUsuario.get("email"), email));
        
        return em.createQuery(criteria).getSingleResult();
    }
	
}
