package br.com.api.ava.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import br.com.api.ava.model.Pergunta;

@ApplicationScoped
public class PerguntaRepository {

	@Inject
    private EntityManager em;

    public List<Pergunta> findAll() {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pergunta> criteria = cb.createQuery(Pergunta.class);
        Root<Pergunta> pergunta = criteria.from(Pergunta.class);
        criteria.select(pergunta).orderBy(cb.asc(pergunta.get("id")));
        return em.createQuery(criteria).getResultList();
    }
    
    public Pergunta findById(Long id) {
    	return em.find(Pergunta.class, id);
    }

	@SuppressWarnings("rawtypes")
	public List<Pergunta> buscarPerguntasPorPesquisa(long idPesquisa) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Pergunta> criteria = cb.createQuery(Pergunta.class);
    	
    	Root<Pergunta> pergunta = criteria.from(Pergunta.class);
    	
    	Join pesquisa = pergunta.join("pesquisa");
    	
    	criteria.select(pergunta).where(cb.equal(pesquisa.get("id"), idPesquisa));
    	criteria.orderBy(cb.asc(pergunta.get("id")));
    	
    	List<Pergunta> perguntas = em.createQuery(criteria).getResultList(); 
    	
    	for (Pergunta pergunta2 : perguntas) {
			pergunta2.setPesquisa(null);
		}
    	
		return perguntas;
	}

	@SuppressWarnings("rawtypes")
	public Pergunta buscarPergunta(long idPergunta, long idPesquisa) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Pergunta> criteria = cb.createQuery(Pergunta.class);
    	Root<Pergunta> pergunta = criteria.from(Pergunta.class);
    	
    	Join pesquisa = pergunta.join("pesquisa");
    	
    	criteria.where(cb.and(cb.equal(pesquisa.get("id"), idPesquisa),cb.equal(pergunta.get("id"), idPergunta)));
    	
    	return em.createQuery(criteria).getSingleResult();
	}

}
