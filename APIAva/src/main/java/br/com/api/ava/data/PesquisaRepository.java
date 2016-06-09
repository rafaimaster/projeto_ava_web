package br.com.api.ava.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.api.ava.model.Aluno;
import br.com.api.ava.model.Pesquisa;
import br.com.api.ava.model.Professor;
import br.com.api.ava.model.Usuario;
import br.com.api.ava.vo.DadosChartResultVO;
import br.com.api.ava.vo.DadosChartVO;

@ApplicationScoped
public class PesquisaRepository {

	@Inject
    private EntityManager em;

    public List<Pesquisa> findAll() {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pesquisa> criteria = cb.createQuery(Pesquisa.class);
        Root<Pesquisa> pesquisa = criteria.from(Pesquisa.class);
        criteria.select(pesquisa).orderBy(cb.asc(pesquisa.get("titulo")));
        return em.createQuery(criteria).getResultList();
    }
    
    public Pesquisa findById(Long id) {
    	return em.find(Pesquisa.class, id);
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public Pesquisa buscarPesquisaPorIdParaResponder(long idPesquisa) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Pesquisa> criteria = cb.createQuery(Pesquisa.class);
    	
    	Root<Pesquisa> pesquisa = criteria.from(Pesquisa.class);
    	
    	//Join autor = pesquisa.join("autor");
    	
    	Join situacao = pesquisa.join("situacao");
    	Join<Pesquisa, Usuario> autor = (Join) pesquisa.fetch("autor");
    	
    	Expression<Timestamp> currentTimestamp = cb.currentTimestamp();
    	Predicate dtEncerramentoNull = cb.isNull(pesquisa.get("dataEncerramento"));
    	Predicate dtMaiorOuIgualdtEncerramento = cb.greaterThanOrEqualTo(pesquisa.get("dataEncerramento").as(Date.class), currentTimestamp);
    	
    	/* pesquisa que está publicada e com data de encerramento  */
    	criteria.select(pesquisa).where(cb.equal(situacao.get("id"), 3)/*3-Publicado*/,
    									cb.equal(pesquisa.get("id"), idPesquisa),
    									cb.or(dtEncerramentoNull, dtMaiorOuIgualdtEncerramento))
    									.orderBy(cb.desc(pesquisa.get("dataCriacao")));
    	
    	Pesquisa pesquisaResult = em.createQuery(criteria).getSingleResult();
    	pesquisaResult.getAutor().setLoginUsuario(null);
		
		return pesquisaResult;
	}
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Pesquisa> buscarMinhasPesquisas(long idUsuario) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Pesquisa> criteria = cb.createQuery(Pesquisa.class);
    	
    	Root<Pesquisa> pesquisa = criteria.from(Pesquisa.class);
    	
    	//Join autor = pesquisa.join("autor");
    	
    	Join<Pesquisa, Usuario> autor = (Join) pesquisa.fetch("autor");
    	
    	criteria.select(pesquisa).where(cb.equal(autor.get("id"), idUsuario));
    	
    	List<Pesquisa> pesquisas = em.createQuery(criteria).getResultList(); 
    	
    	for (Pesquisa pesquisa2 : pesquisas) {
    		if (pesquisa2.getAutor() instanceof Aluno) {
    			((Aluno) pesquisa2.getAutor()).getTurma().getProfessor().setLoginUsuario(null);
    		}
			pesquisa2.getAutor().setLoginUsuario(null);
		}
    	
		return pesquisas;
	}

	@SuppressWarnings("unchecked")
	public List<Pesquisa> buscarPesquisasDosAlunos(Long idProf) {
		Query query = em.createNativeQuery("  select b.* "
										         + " from turma t, "
										         + "      professor p, "
										         + "      usuario prof, "
										         + "      aluno a, "
										         + "      usuario alu, "
										         + "      pesquisa b "
										         + "where t.id_professor = p.id "
										         + "  and prof.id= p.id "
										         + "  and a.id = alu.id "
										         + "  and a.id_turma = t.id "
										         + "  and b.id_usuario = alu.id "
										         + "  and prof.id = :idProf ", Pesquisa.class );
		query.setParameter("idProf", idProf);
		
		List<Pesquisa> pesquisas = (List<Pesquisa>) query.getResultList(); 
		
		for (Pesquisa pesquisa2 : pesquisas) {
			if (pesquisa2.getAutor() instanceof Aluno) {
				((Aluno) pesquisa2.getAutor()).getTurma().getProfessor().setLoginUsuario(null);
			}
			pesquisa2.getAutor().setLoginUsuario(null);
		}
		      
		return pesquisas; 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public List<Pesquisa> buscarPesquisasPublicadas() {
		List<Pesquisa> pesquisas = new ArrayList<Pesquisa>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Pesquisa> criteria = cb.createQuery(Pesquisa.class);
    	
    	Root<Pesquisa> pesquisa = criteria.from(Pesquisa.class);
    	
    	Join situacao = pesquisa.join("situacao");
    	Join<Pesquisa, Usuario> autor = (Join) pesquisa.fetch("autor");
    	
    	
    	
    	Expression<Timestamp> currentTimestamp = cb.currentTimestamp();
    	Predicate dtEncerramentoNull = cb.isNull(pesquisa.get("dataEncerramento"));
    	Predicate dtMaiorOuIgualdtEncerramento = cb.greaterThanOrEqualTo(pesquisa.get("dataEncerramento").as(Date.class), currentTimestamp);
    	
    	/* pesquisa que está publicada e sem data de encerramento  */
    	criteria.select(pesquisa).where(cb.equal(situacao.get("id"), 3)/*3-Publicado*/,
    									cb.or(dtEncerramentoNull, dtMaiorOuIgualdtEncerramento))
    									.orderBy(cb.desc(pesquisa.get("dataCriacao")));
    	
    	//criteria.select(pesquisa).where(cb.equal(situacao.get("id"), 3)).orderBy(cb.desc(pesquisa.get("dataCriacao")));//3-Publicado
    	
    	pesquisas = em.createQuery(criteria).getResultList();
    	
    	for (Pesquisa pesquisa2 : pesquisas) {
			pesquisa2.getAutor().setLoginUsuario(null);
			if (pesquisa2.getAutor() instanceof Aluno) {
				Aluno aluno = (Aluno) pesquisa2.getAutor();
				aluno.getTurma().getProfessor().setLoginUsuario(null);
			} else if (pesquisa2.getAutor() instanceof Professor) {
				Professor professor = (Professor) pesquisa2.getAutor();
				professor.setLoginUsuario(null);
			}
			
		}
    	
    	return pesquisas;
	}
	
	@SuppressWarnings("unchecked")
	public DadosChartResultVO buscarResultadoChart(Long idPergunta1, Long idPergunta2) {
		Query query = em.createNativeQuery(" select t.id_alternativa id_labels, t.descricao_alt labels, a.id_alternativa id_series, x.descricao series, count(*) dados"
				+ " from resposta a,"
				+ " alternativa x,"
				+ " (select distinct r.id_alternativa, m.descricao as descricao_alt, r.descricao"
				+ " from resposta r, "
				+ " alternativa m"
				+ " where m.id = r.id_alternativa "
				+ " and m.id_pergunta = r.id_pergunta"
				+ " and r.id_pergunta in (:idPergunta1)) t"
				+ " where a.id_alternativa <> t.id_alternativa"
				+ " and a.descricao = t.descricao"
				+ " and a.id_alternativa = x.id"
				+ " and x.id_pergunta = a.id_pergunta"
				+ " and a.id_pergunta in (:idPergunta2)"
				+ " group by t.id_alternativa, t.descricao_alt, a.id_alternativa, x.descricao"
				+ " order by a.id_alternativa, t.id_alternativa ");
		query.setParameter("idPergunta1", idPergunta1)
				.setParameter("idPergunta2", idPergunta2);
		
		List<DadosChartVO> lista = new ArrayList<DadosChartVO>();
		
		List<Object[]> resultado = query.getResultList(); 
		
		DadosChartVO vo;
		for (Object[] object : resultado) {
			vo = new DadosChartVO();
			vo.setIdLabel(Integer.parseInt(object[0].toString()));
			vo.setLabel(object[1].toString());
			vo.setIdSerie(Integer.parseInt(object[2].toString()));
			vo.setSerie(object[3].toString());
			vo.setDados(Integer.parseInt(object[4].toString()));
			lista.add(vo);
		}
		
		DadosChartResultVO resultVO;
		resultVO = new DadosChartResultVO();
		resultVO.setDados(new ArrayList<List<Integer>>());
		LinkedHashSet<String> labels = new LinkedHashSet<String>();
		LinkedHashSet<String> series = new LinkedHashSet<String>();
		List<Integer> dados = null;
		for (DadosChartVO chart : lista) {
			if(!labels.contains(chart.getLabel())){
				labels.add(chart.getLabel());
			}
			if(!series.contains(chart.getSerie())){
				series.add(chart.getSerie());
			}
		}
		
		for (String serie : series) {
			dados = new ArrayList<Integer>();
			for (DadosChartVO chart : lista) {
				if (chart.getSerie().equals(serie)) {
					dados.add(chart.getDados());
				}
			}
			/*
			if (dados.size() < labels.size()) {
				for (int i = dados.size(); i <= labels.size(); i++) {
					dados.add(0);
				}
			}
			*/
			resultVO.getDados().add(dados);
		}
		
		resultVO.setDados1(new ArrayList<Integer>());
		resultVO.setLabels(labels);
		resultVO.setSeries(series);
		
		      
		return resultVO; 
	}
	
	@SuppressWarnings("unchecked")
	public DadosChartResultVO buscarResultadoChart(Long idPergunta1) {
		Query query = em.createNativeQuery(" select a.id_alternativa id_alternativa_1, "
										 + "        x.descricao descricao_alt_1,  "
										 + "		count(*)"
										 + "   from resposta a,"
										 + "        alternativa x,"
										 + "        pergunta p"
										 + "  where a.id_alternativa = x.id"
										 + "    and x.id_pergunta = a.id_pergunta"
										 + "    and a.id_alternativa = x.id"
										 + "    and a.id_pergunta in (:idPergunta1)"
										 + "    and p.id = a.id_pergunta"
										 + " group by a.id_alternativa, x.descricao"
										 + " order by 1; ");
		query.setParameter("idPergunta1", idPergunta1);	
		
		List<DadosChartVO> lista = new ArrayList<DadosChartVO>();
		
		List<Object[]> resultado = query.getResultList(); 
		
		DadosChartVO vo;
		for (Object[] object : resultado) {
			vo = new DadosChartVO();
			vo.setIdLabel(Integer.parseInt(object[0].toString()));
			vo.setLabel(object[1].toString());
			vo.setDados(Integer.parseInt(object[2].toString()));
			lista.add(vo);
		}
		
		DadosChartResultVO resultVO;
		resultVO = new DadosChartResultVO();
		resultVO.setDados(new ArrayList<List<Integer>>());
		LinkedHashSet<String> labels = new LinkedHashSet<String>();
		
		List<Integer> dados = new ArrayList<Integer>();
		
		for (DadosChartVO chart : lista) {
			labels.add(chart.getLabel());
			dados.add(chart.getDados());
		}
		
		resultVO.setDados(new ArrayList<List<Integer>>());
		resultVO.setDados1(dados);
		resultVO.setLabels(labels);
		resultVO.setSeries(labels);
		
		return resultVO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public List<Pesquisa> buscarPesquisasEncerradas() {
		List<Pesquisa> pesquisas = new ArrayList<Pesquisa>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Pesquisa> criteria = cb.createQuery(Pesquisa.class);
    	
    	Root<Pesquisa> pesquisa = criteria.from(Pesquisa.class);
    	
    	Join situacao = pesquisa.join("situacao");
    	Join<Pesquisa, Usuario> autor = (Join) pesquisa.fetch("autor");
    	
    	Expression<Timestamp> currentTimestamp = cb.currentTimestamp();
    	Predicate dtEncerramentoNotNull = cb.isNotNull(pesquisa.get("dataEncerramento"));
    	Predicate dtMenorOuIgualdtEncerramento = cb.lessThanOrEqualTo(pesquisa.get("dataEncerramento").as(Date.class), currentTimestamp);
    	
    	/* pesquisa que está publicada e sem data de encerramento  */
    	criteria.select(pesquisa).where(cb.equal(situacao.get("id"), 3)/*3-Publicado*/,
    									cb.and(dtEncerramentoNotNull, dtMenorOuIgualdtEncerramento))
    									.orderBy(cb.desc(pesquisa.get("dataEncerramento")));
    	
    	pesquisas = em.createQuery(criteria).getResultList();
    	
    	for (Pesquisa pesquisa2 : pesquisas) {
			pesquisa2.getAutor().setLoginUsuario(null);
			if (pesquisa2.getAutor() instanceof Aluno) {
				Aluno aluno = (Aluno) pesquisa2.getAutor();
				aluno.getTurma().getProfessor().setLoginUsuario(null);
			} else if (pesquisa2.getAutor() instanceof Professor) {
				Professor professor = (Professor) pesquisa2.getAutor();
				professor.setLoginUsuario(null);
			}
			
		}
    	
    	return pesquisas;
	}

}
