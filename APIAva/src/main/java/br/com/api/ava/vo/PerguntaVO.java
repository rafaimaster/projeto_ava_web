package br.com.api.ava.vo;

import java.util.Date;
import java.util.List;

public class PerguntaVO {

	private Long id;
    private String descricao;
    private Date dataCriacao;
    private List<AlternativaVO> alternativas;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public List<AlternativaVO> getAlternativas() {
		return alternativas;
	}
	public void setAlternativas(List<AlternativaVO> alternativas) {
		this.alternativas = alternativas;
	}
    
}
