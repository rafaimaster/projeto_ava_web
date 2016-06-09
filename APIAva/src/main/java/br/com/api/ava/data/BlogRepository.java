package br.com.api.ava.data;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import br.com.api.ava.model.Aluno;
import br.com.api.ava.model.Blog;
import br.com.api.ava.model.Professor;
import br.com.api.ava.model.Usuario;

@ApplicationScoped
public class BlogRepository {

	@Inject
    private EntityManager em;

    public List<Blog> findAll() {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Blog> criteria = cb.createQuery(Blog.class);
        Root<Blog> blog = criteria.from(Blog.class);
        criteria.select(blog).orderBy(cb.asc(blog.get("titulo")));
        return em.createQuery(criteria).getResultList();
    }
    
    public Blog findById(Long id) {
    	Blog blog = em.find(Blog.class, id);
    	
    	blog.getAutor().setLoginUsuario(null);
		if (blog.getAutor() instanceof Aluno) {
			Aluno aluno = (Aluno) blog.getAutor();
			aluno.getTurma().getProfessor().setLoginUsuario(null);
		} else if (blog.getAutor() instanceof Professor) {
			Professor professor = (Professor) blog.getAutor();
			professor.setLoginUsuario(null);
		}
    	
    	return blog;
    }

	@SuppressWarnings("rawtypes")
	public List<Blog> buscarMeusPosts(long idUsuario) {
		List<Blog> postagens = new ArrayList<Blog>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Blog> criteria = cb.createQuery(Blog.class);
    	
    	Root<Blog> blog = criteria.from(Blog.class);
    	
    	Join autor = blog.join("autor");
    	
    	criteria.select(blog).where(cb.equal(autor.get("id"), idUsuario));
    	
    	postagens = em.createQuery(criteria).getResultList();
    	
    	for (Blog blog2 : postagens) {
			blog2.getAutor().setLoginUsuario(null);
			if (blog2.getAutor() instanceof Aluno) {
				Aluno aluno = (Aluno) blog2.getAutor();
				aluno.getTurma().getProfessor().setLoginUsuario(null);
			} else if (blog2.getAutor() instanceof Professor) {
				Professor professor = (Professor) blog2.getAutor();
				professor.setLoginUsuario(null);
			}
			
		}
    	
		return postagens;
	}

//	@SuppressWarnings("rawtypes")
//	public List<Blog> buscarPostsDosAlunos1() {
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//    	CriteriaQuery<Blog> criteria = cb.createQuery(Blog.class);
//    	
//    	Root<Blog> blog = criteria.from(Blog.class);
//    	
//    	Join situacaoBlog = blog.join("situacaoBlog");
//    	Join autor = blog.join("autor");
//    	Join<Aluno,Turma> aturma = autor.join("aluno");
//    	Join perfilUsuario = autor.join("perfilUsuario");
//    	
//    	criteria.select(blog).where(cb.and(cb.equal(perfilUsuario.get("id"), 2),//filtro para perfil 2-Aluno
//    										cb.notEqual(situacaoBlog.get("id"), 1))/*situação diferente de 1-Rascunho*/);
//    	
//    	
//		return em.createQuery(criteria).getResultList();
//	}
	
	@SuppressWarnings("unchecked")
	public List<Blog> buscarPostsDosAlunos(Long idProf) {
		Query query = em.createNativeQuery("  select b.* "
										         + " from turma t, "
										         + "      professor p, "
										         + "      usuario prof, "
										         + "      aluno a, "
										         + "      usuario alu, "
										         + "      blog b "
										         + "where t.id_professor = p.id "
										         + "  and prof.id= p.id "
										         + "  and a.id = alu.id "
										         + "  and a.id_turma = t.id "
										         + "  and b.id_usuario = alu.id "
										         + "  and prof.id = :idProf ", Blog.class);
		query.setParameter("idProf", idProf);
		      
		List<Blog> postagens = query.getResultList();
		
		for (Blog blog2 : postagens) {
			blog2.getAutor().setLoginUsuario(null);
			if (blog2.getAutor() instanceof Aluno) {
				Aluno aluno = (Aluno) blog2.getAutor();
				aluno.getTurma().getProfessor().setLoginUsuario(null);
			} else if (blog2.getAutor() instanceof Professor) {
				Professor professor = (Professor) blog2.getAutor();
				professor.setLoginUsuario(null);
			}
			
		}
		
		return  postagens;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public List<Blog> buscarPostsPublicados() {
		List<Blog> postagens = new ArrayList<Blog>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Blog> criteria = cb.createQuery(Blog.class);
    	
    	Root<Blog> blog = criteria.from(Blog.class);
    	
    	Join situacao = blog.join("situacaoBlog");
    	Join<Blog, Usuario> autor = (Join) blog.fetch("autor");
    	
    	
    	criteria.select(blog).where(cb.equal(situacao.get("id"), 3)).orderBy(cb.desc(blog.get("dataCriacao")));//3-Publicado
    	
    	postagens = em.createQuery(criteria).getResultList();
    	
    	for (Blog blog2 : postagens) {
			blog2.getAutor().setLoginUsuario(null);
			if (blog2.getAutor() instanceof Aluno) {
				Aluno aluno = (Aluno) blog2.getAutor();
				aluno.getTurma().getProfessor().setLoginUsuario(null);
			} else if (blog2.getAutor() instanceof Professor) {
				Professor professor = (Professor) blog2.getAutor();
				professor.setLoginUsuario(null);
			}
			
		}
    	
    	return postagens;
	}
	
	

}
