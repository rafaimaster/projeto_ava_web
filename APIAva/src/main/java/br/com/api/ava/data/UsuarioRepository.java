package br.com.api.ava.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import br.com.api.ava.model.LoginUsuario;
import br.com.api.ava.model.Usuario;


@ApplicationScoped
public class UsuarioRepository {

	@Inject
    private EntityManager em;

    public List<Usuario> findAll() {
    	CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
        Root<Usuario> usuario = criteria.from(Usuario.class);
        criteria.select(usuario).orderBy(builder.asc(usuario.get("nome")));
        return em.createQuery(criteria).getResultList();
    }
    
    public Usuario findById(Long id) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);
        Root<Usuario> usuario = criteria.from(Usuario.class);
        usuario.fetch("loginUsuario");
        
        criteria.select(usuario).where(cb.equal(usuario.get("id"), id));
    	
    	return em.createQuery(criteria).getSingleResult();
    }
    
    @SuppressWarnings("rawtypes")
	public Usuario findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);
        Root<Usuario> usuario = criteria.from(Usuario.class);
        
        Join loginUsuario = usuario.join("loginUsuario");
        
        criteria.select(usuario).where(cb.equal(loginUsuario.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Usuario findByToken(String token) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);
    	Root<Usuario> usuario = criteria.from(Usuario.class);
    	
    	Join<Usuario, LoginUsuario> loginUsuario = (Join) usuario.fetch("loginUsuario");
    	
    	
    	criteria.select(usuario).where(cb.equal(loginUsuario.get("token"), token));
    	return em.createQuery(criteria).getSingleResult();
    }

}
