package br.com.api.ava.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.api.ava.model.PerfilUsuario;

@ApplicationScoped
public class PerfilUsuarioRepository {
	@Inject
    private EntityManager em;

    public List<PerfilUsuario> findAll() {
    	CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<PerfilUsuario> criteria = builder.createQuery(PerfilUsuario.class);
        Root<PerfilUsuario> perfilUsuario = criteria.from(PerfilUsuario.class);
        criteria.select(perfilUsuario).orderBy(builder.asc(perfilUsuario.get("descricao")));
        return em.createQuery(criteria).getResultList();
    }

	public List<PerfilUsuario> findAllAtivos() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<PerfilUsuario> criteria = builder.createQuery(PerfilUsuario.class);
        Root<PerfilUsuario> perfilUsuario = criteria.from(PerfilUsuario.class);
        criteria.select(perfilUsuario).where(builder.equal(perfilUsuario.get("situacao"), true))
        .orderBy(builder.asc(perfilUsuario.get("descricao")));
        return em.createQuery(criteria).getResultList();
	}
}
