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
@Table(name = "BLOG")
public class Blog implements Serializable {

	private static final long serialVersionUID = 166735167738844276L;
	
	
	private Long blogId;
    private String titulo;
    private SituacaoPublicacao situacaoBlog;
    private Usuario autor;
    private Date dataCriacao;
    private String conteudo;

    public Blog() {
    }

	public Blog(Long blogId) {
		this.blogId = blogId;
	}

	@SequenceGenerator(name = "sq_generator_blog", sequenceName = "sq_blog",allocationSize=1, initialValue = 1)
	@Id
	@GeneratedValue(generator="sq_generator_blog", strategy=GenerationType.SEQUENCE)
	@Column(name = "ID_BLOG")
	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	@Column(name = "TITULO")
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id", referencedColumnName="id")
	public SituacaoPublicacao getSituacaoBlog() {
		return situacaoBlog;
	}

	public void setSituacaoBlog(SituacaoPublicacao situacaoBlog) {
		this.situacaoBlog = situacaoBlog;
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

	@Column(name = "CONTEUDO", columnDefinition="TEXT")
	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result + ((blogId == null) ? 0 : blogId.hashCode());
		result = prime * result
				+ ((conteudo == null) ? 0 : conteudo.hashCode());
		result = prime * result
				+ ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result
				+ ((situacaoBlog == null) ? 0 : situacaoBlog.hashCode());
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
		Blog other = (Blog) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (blogId == null) {
			if (other.blogId != null)
				return false;
		} else if (!blogId.equals(other.blogId))
			return false;
		if (conteudo == null) {
			if (other.conteudo != null)
				return false;
		} else if (!conteudo.equals(other.conteudo))
			return false;
		if (dataCriacao == null) {
			if (other.dataCriacao != null)
				return false;
		} else if (!dataCriacao.equals(other.dataCriacao))
			return false;
		if (situacaoBlog == null) {
			if (other.situacaoBlog != null)
				return false;
		} else if (!situacaoBlog.equals(other.situacaoBlog))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}
	
}