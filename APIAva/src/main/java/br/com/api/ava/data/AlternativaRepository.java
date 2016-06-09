package br.com.api.ava.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import br.com.api.ava.model.Alternativa;

@ApplicationScoped
public class AlternativaRepository {

	@Inject
    private EntityManager em;

    public List<Alternativa> findAll() {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Alternativa> criteria = cb.createQuery(Alternativa.class);
        Root<Alternativa> alternativa = criteria.from(Alternativa.class);
        criteria.select(alternativa).orderBy(cb.asc(alternativa.get("id")));
        return em.createQuery(criteria).getResultList();
    }
    
    public Alternativa findById(Long id) {
    	return em.find(Alternativa.class, id);
    }

	@SuppressWarnings("rawtypes")
	public List<Alternativa> buscarAlternativasPorPergunta(long idPergunta) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Alternativa> criteria = cb.createQuery(Alternativa.class);
    	
    	Root<Alternativa> alternativa = criteria.from(Alternativa.class);
    	
    	Join pergunta = alternativa.join("pergunta");
    	
    	criteria.select(alternativa).where(cb.equal(pergunta.get("id"), idPergunta));
    	criteria.orderBy(cb.asc(alternativa.get("id")));
    	
    	List<Alternativa> alternativas = em.createQuery(criteria).getResultList(); 
    	
    	for (Alternativa alternativa2 : alternativas) {
    		alternativa2.setPergunta(null);
		}
    	
		return alternativas;
	}

	@SuppressWarnings("rawtypes")
	public Alternativa buscarAlternativa(long idAlternativa, long idPergunta) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Alternativa> criteria = cb.createQuery(Alternativa.class);
    	Root<Alternativa> alternativa = criteria.from(Alternativa.class);
    	
    	Join pergunta = alternativa.join("pergunta");
    	
    	criteria.where(cb.and(cb.equal(pergunta.get("id"), idPergunta),cb.equal(alternativa.get("id"), idAlternativa)));
    	
    	return em.createQuery(criteria).getSingleResult();
	}

}
