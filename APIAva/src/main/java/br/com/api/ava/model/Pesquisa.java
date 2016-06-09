package br.com.api.ava.model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Rafael Dornelles Lima
 */
@Entity
@Table(name = "PESQUISA")
public class Pesquisa implements Serializable {

	private static final long serialVersionUID = 166735167738844276L;
	
	
	private Long id;
    private String titulo;
    private String subTitulo;
    private SituacaoPublicacao situacao;
    private Usuario autor;
    private Date dataCriacao;
    private Date dataEncerramento;

    public Pesquisa() {
    }

	public Pesquisa(Long id) {
		this.id = id;
	}

	@SequenceGenerator(name = "sq_generator_pesquisa", sequenceName = "sq_pesquisa",allocationSize=1, initialValue = 1)
	@Id
	@GeneratedValue(generator="sq_generator_pesquisa", strategy=GenerationType.SEQUENCE)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TITULO")
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	@Column(name = "SUB_TITULO")
	public String getSubTitulo() {
		return subTitulo;
	}

	public void setSubTitulo(String subTitulo) {
		this.subTitulo = subTitulo;
	}

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_situacao", referencedColumnName="id")
	public SituacaoPublicacao getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoPublicacao situacao) {
		this.situacao = situacao;
	}

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_usuario", referencedColumnName="id")
	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CRIACAO")
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_ENCERRAMENTO")
	public Date getDataEncerramento() {
		return dataEncerramento;
	}

	public void setDataEncerramento(Date dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result
				+ ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result
				+ ((subTitulo == null) ? 0 : subTitulo.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
		Pesquisa other = (Pesquisa) obj;
		if (dataCriacao == null) {
			if (other.dataCriacao != null)
				return false;
		} else if (!dataCriacao.equals(other.dataCriacao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		if (subTitulo == null) {
			if (other.subTitulo != null)
				return false;
		} else if (!subTitulo.equals(other.subTitulo))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	
	
}