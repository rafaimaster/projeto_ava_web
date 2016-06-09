package br.com.api.ava.data;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import br.com.api.ava.model.Turma;

@ApplicationScoped
public class TurmaRepository {

	@Inject
    private EntityManager em;

    public List<Turma> findAll() {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Turma> criteria = cb.createQuery(Turma.class);
        Root<Turma> turma = criteria.from(Turma.class);
        criteria.select(turma).orderBy(cb.asc(turma.get("nome")));
        return em.createQuery(criteria).getResultList();
    }
    
    public Turma findById(Long id) {
    	return em.find(Turma.class, id);
    }

	@SuppressWarnings("rawtypes")
	public List<Turma> buscarMinhasTurmas(long idUsuario) {
		List<Turma> turmas = new ArrayList<Turma>();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Turma> criteria = cb.createQuery(Turma.class);
    	
    	Root<Turma> turma = criteria.from(Turma.class);
    	
    	Join professor = turma.join("professor");
    	
    	criteria.select(turma).where(cb.equal(professor.get("id"), idUsuario));
    	criteria.orderBy(cb.asc(turma.get("nome")));
    	
    	turmas = em.createQuery(criteria).getResultList();
    			
    	for (Turma turma2 : turmas) {
			turma2.getProfessor().setLoginUsuario(null);
		}
    	
		return turmas;
	}

}
