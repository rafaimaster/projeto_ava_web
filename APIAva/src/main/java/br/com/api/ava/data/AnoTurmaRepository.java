package br.com.api.ava.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.api.ava.model.AnoTurma;

@ApplicationScoped
public class AnoTurmaRepository {
	@Inject
    private EntityManager em;

    public List<AnoTurma> findAll() {
    	CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<AnoTurma> criteria = builder.createQuery(AnoTurma.class);
        Root<AnoTurma> anoTurma = criteria.from(AnoTurma.class);
        criteria.select(anoTurma).orderBy(builder.asc(anoTurma.get("descricao")));
        return em.createQuery(criteria).getResultList();
    }

	public List<AnoTurma> findAllAtivos() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<AnoTurma> criteria = builder.createQuery(AnoTurma.class);
        Root<AnoTurma> anoTurma = criteria.from(AnoTurma.class);
        criteria.select(anoTurma).where(builder.equal(anoTurma.get("situacao"), true))
        .orderBy(builder.asc(anoTurma.get("descricao")));
        return em.createQuery(criteria).getResultList();
	}
}
