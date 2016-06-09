package br.com.api.ava.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Rafael Dornelles Lima
 */
@Entity
@Table(name = "AVALIACAO_PESQUISA")
public class AvaliacaoPesquisa implements Serializable {
	
	private static final long serialVersionUID = -269282568328622638L;
	
	private Long id;
    private String titulo;
    private String descricao;
    private Pesquisa pesquisa;
    private String tipoGrafico;
    private Pergunta pergunta1;
    private Pergunta pergunta2;

    public AvaliacaoPesquisa() {
    }

	public AvaliacaoPesquisa(Long id) {
		this.id = id;
	}

	@SequenceGenerator(name = "sq_generator_avaliacao_pesquisa", sequenceName = "sq_avaliacao_pesquisa",allocationSize=1, initialValue = 1)
	@Id
	@GeneratedValue(generator="sq_generator_avaliacao_pesquisa", strategy=GenerationType.SEQUENCE)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@NotNull
	@Column(name = "TITULO")
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@NotNull
	@Column(name = "DESCRICAO", columnDefinition="TEXT")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@NotNull
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_pesquisa", referencedColumnName="id")
	public Pesquisa getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
	}

	@NotNull
	@Column(name = "TIPO_GRAFICO")
	public String getTipoGrafico() {
		return tipoGrafico;
	}

	public void setTipoGrafico(String tipoGrafico) {
		this.tipoGrafico = tipoGrafico;
	}

	@NotNull
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_pergunta1", referencedColumnName="id")
	public Pergunta getPergunta1() {
		return pergunta1;
	}

	public void setPergunta1(Pergunta pergunta1) {
		this.pergunta1 = pergunta1;
	}

	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_pergunta2", referencedColumnName="id")
	public Pergunta getPergunta2() {
		return pergunta2;
	}

	public void setPergunta2(Pergunta pergunta2) {
		this.pergunta2 = pergunta2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((tipoGrafico == null) ? 0 : tipoGrafico.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AvaliacaoPesquisa other = (AvaliacaoPesquisa) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tipoGrafico == null) {
			if (other.tipoGrafico != null)
				return false;
		} else if (!tipoGrafico.equals(other.tipoGrafico))
			return false;
		return true;
	}

	
}