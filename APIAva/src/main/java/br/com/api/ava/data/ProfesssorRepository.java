package br.com.api.ava.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import br.com.api.ava.model.Professor;


@ApplicationScoped
public class ProfesssorRepository {

	@Inject
    private EntityManager em;

    public List<Professor> findAll() {
    	CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Professor> criteria = builder.createQuery(Professor.class);
        Root<Professor> professor = criteria.from(Professor.class);
        criteria.select(professor).orderBy(builder.asc(professor.get("nome")));
        return em.createQuery(criteria).getResultList();
    }
    
    public Professor findById(Long id) {
    	return em.find(Professor.class, id);
    }
    
    @SuppressWarnings("rawtypes")
	public Professor findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Professor> criteria = cb.createQuery(Professor.class);
        Root<Professor> professor = criteria.from(Professor.class);
        
        Join loginUsuario = professor.join("loginUsuario");
        
        criteria.select(professor).where(cb.equal(loginUsuario.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }
    
//    @SuppressWarnings("rawtypes")
//	public Professor findByToken(String token) {
//    	CriteriaBuilder cb = em.getCriteriaBuilder();
//    	CriteriaQuery<Professor> criteria = cb.createQuery(Professor.class);
//    	Root<Professor> usuario = criteria.from(Professor.class);
//    	
//    	Join loginUsuario = usuario.join("loginUsuario");
//    	
//    	criteria.select(usuario).where(cb.equal(loginUsuario.get("token"), token));
//    	return em.createQuery(criteria).getSingleResult();
//    }

}
