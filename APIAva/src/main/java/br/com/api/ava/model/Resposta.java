package br.com.api.ava.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Rafael Dornelles Lima
 */
@Entity
@Table(name = "RESPOSTA")
public class Resposta implements Serializable {
	
	private static final long serialVersionUID = -269282568328622638L;
	
	private Long id;
    private String descricao;
    private Pergunta pergunta;
    private Alternativa alternativa;

    public Resposta() {
    }

	public Resposta(Long id) {
		this.id = id;
	}

	@SequenceGenerator(name = "sq_generator_resposta", sequenceName = "sq_resposta",allocationSize=1, initialValue = 1)
	@Id
	@GeneratedValue(generator="sq_generator_resposta", strategy=GenerationType.SEQUENCE)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DESCRICAO")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_pergunta", referencedColumnName="id")
	public Pergunta getPergunta() {
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_alternativa", referencedColumnName="id")
	public Alternativa getAlternativa() {
		return alternativa;
	}

	public void setAlternativa(Alternativa alternativa) {
		this.alternativa = alternativa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Resposta other = (Resposta) obj;
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
		return true;
	}
	
}