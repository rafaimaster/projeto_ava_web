package br.com.api.ava.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import br.com.api.ava.model.Aluno;
import br.com.api.ava.model.Blog;
import br.com.api.ava.model.LoginUsuario;
import br.com.api.ava.model.Usuario;


@ApplicationScoped
public class AlunoRepository {

	@Inject
    private EntityManager em;

    public List<Aluno> findAll() {
    	CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Aluno> criteria = builder.createQuery(Aluno.class);
        Root<Aluno> aluno = criteria.from(Aluno.class);
        criteria.select(aluno).orderBy(builder.asc(aluno.get("nome")));
        return em.createQuery(criteria).getResultList();
    }
    
    public Aluno findById(Long id) {
    	return em.find(Aluno.class, id);
    }
    
    @SuppressWarnings("rawtypes")
	public Aluno findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aluno> criteria = cb.createQuery(Aluno.class);
        Root<Aluno> aluno = criteria.from(Aluno.class);
        
        Join loginUsuario = aluno.join("loginUsuario");
        
        criteria.select(aluno).where(cb.equal(loginUsuario.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }
    
    @SuppressWarnings("rawtypes")
    public List<Aluno> buscarAlunoPorTurma(long idTurma) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Aluno> criteria = cb.createQuery(Aluno.class);
    	Root<Aluno> aluno = criteria.from(Aluno.class);
    	
    	Join turma = aluno.join("turma");
    	
    	criteria.select(aluno).where(cb.equal(turma.get("id"), idTurma));
    	return em.createQuery(criteria).getResultList();
    }
    
    @SuppressWarnings("rawtypes")
	public Aluno buscarAluno(long idTurma, long idAluno) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Aluno> criteria = cb.createQuery(Aluno.class);
    	Root<Aluno> aluno = criteria.from(Aluno.class);
    	
    	Join turma = aluno.join("turma");
    	Join<Aluno, LoginUsuario> autor = (Join) aluno.fetch("loginUsuario");
    	
    	criteria.where(cb.and(cb.equal(turma.get("id"), idTurma),cb.equal(aluno.get("id"), idAluno)));
    	
    	return em.createQuery(criteria).getSingleResult();
	}
    
//    @SuppressWarnings("rawtypes")
//	public Aluno findByToken(String token) {
//    	CriteriaBuilder cb = em.getCriteriaBuilder();
//    	CriteriaQuery<Aluno> criteria = cb.createQuery(Aluno.class);
//    	Root<Aluno> aluno = criteria.from(Aluno.class);
//    	
//    	Join loginUsuario = aluno.join("loginUsuario");
//    	
//    	criteria.select(aluno).where(cb.equal(loginUsuario.get("token"), token));
//    	return em.createQuery(criteria).getSingleResult();
//    }

}
