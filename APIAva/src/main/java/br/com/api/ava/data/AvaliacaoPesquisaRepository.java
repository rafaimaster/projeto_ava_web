package br.com.api.ava.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import br.com.api.ava.model.AvaliacaoPesquisa;

@ApplicationScoped
public class AvaliacaoPesquisaRepository {

	@Inject
    private EntityManager em;
	
    public AvaliacaoPesquisa findById(Long id) {
    	AvaliacaoPesquisa avaliacao = em.find(AvaliacaoPesquisa.class, id);    	
    	avaliacao.getPesquisa().getAutor().setLoginUsuario(null);
    	
    	return avaliacao;
    }

	@SuppressWarnings("rawtypes")
	public AvaliacaoPesquisa buscarPorIdPesquisa(long idPesquisa) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<AvaliacaoPesquisa> criteria = cb.createQuery(AvaliacaoPesquisa.class);
    	
    	Root<AvaliacaoPesquisa> avaliacaoPesquisa = criteria.from(AvaliacaoPesquisa.class);
    	
    	Join pesquisa = avaliacaoPesquisa.join("pesquisa");
    	
    	criteria.select(avaliacaoPesquisa).where(cb.equal(pesquisa.get("id"), idPesquisa));
    	
    	AvaliacaoPesquisa avaliacao = null;
    	try {
    		avaliacao = em.createQuery(criteria).getSingleResult();    	
    		avaliacao.getPesquisa().getAutor().setLoginUsuario(null);
		} catch (NoResultException e) {
			avaliacao = new AvaliacaoPesquisa();
		}
    	
		return avaliacao;
	}

}
