package br.com.api.ava.vo;

import java.util.Date;
import java.util.List;

public class PesquisaVO {

	private Long id;
    private String titulo;
    private String subTitulo;
    private Date dataCriacao;
    private Date dataEncerramento;
    private List<PerguntaVO> perguntas;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getSubTitulo() {
		return subTitulo;
	}
	public void setSubTitulo(String subTitulo) {
		this.subTitulo = subTitulo;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Date getDataEncerramento() {
		return dataEncerramento;
	}
	public void setDataEncerramento(Date dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}
	public List<PerguntaVO> getPerguntas() {
		return perguntas;
	}
	public void setPerguntas(List<PerguntaVO> perguntas) {
		this.perguntas = perguntas;
	}
    
}
