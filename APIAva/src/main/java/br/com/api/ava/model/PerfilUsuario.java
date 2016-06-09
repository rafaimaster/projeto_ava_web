package br.com.api.ava.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PERFIL_USUARIO")
public class PerfilUsuario implements Serializable {

	private static final long serialVersionUID = -5063398975354450974L;

	@SequenceGenerator(name = "sq_generator_perfil", sequenceName = "sq_perfil_usuario",allocationSize=1, initialValue = 1)
	@Id
	@GeneratedValue(generator="sq_generator_perfil", strategy=GenerationType.SEQUENCE)
	@Column(name = "ID")
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DS_PERFIL")
    private String descricao;
    
    @NotNull
    @Column(name = "IE_SITUACAO")
    private Boolean situacao;

    public PerfilUsuario() {
    }
    
	public PerfilUsuario(Long id) {
		this.id = id;
	}

	public PerfilUsuario(Long id, String descricao, Boolean situacao) {
		this.id = id;
		this.descricao = descricao;
		this.situacao = situacao;
	}

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

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	@Override
	public String toString() {
		return "PerfilUsuario: [id:" + this.id + ", descricao: " + this.descricao + ", situacao: " + this.situacao +"]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((situacao == null) ? 0 : situacao.hashCode());
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
		PerfilUsuario other = (PerfilUsuario) obj;
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
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		return true;
	}
	
}
