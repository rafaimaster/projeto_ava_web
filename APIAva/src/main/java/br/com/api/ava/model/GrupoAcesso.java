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
@Table(name = "GRUPO_ACESSO")
public class GrupoAcesso implements Serializable {

	private static final long serialVersionUID = -3105426117667193932L;

	@SequenceGenerator(name = "sq_generator_gp_acesso", sequenceName = "sq_grupo_acesso",allocationSize=1, initialValue = 1)
	@Id
	@GeneratedValue(generator="sq_generator_gp_acesso", strategy=GenerationType.SEQUENCE)
	@Column(name = "ID")
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DS_GRUPO")
    private String descricao;
    
    @NotNull
    @Column(name = "IE_GRUPO")
    private String grupo;

    public GrupoAcesso() {
    }
    
	public GrupoAcesso(Long id) {
		this.id = id;
	}

	public GrupoAcesso(Long id, String descricao, String grupo) {
		this.id = id;
		this.descricao = descricao;
		this.grupo = grupo;
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

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	@Override
	public String toString() {
		return "GrupoAcesso: [id:" + this.id + ", descricao: " + this.descricao + ", grupo: " + this.grupo +"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
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
		GrupoAcesso other = (GrupoAcesso) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (grupo == null) {
			if (other.grupo != null)
				return false;
		} else if (!grupo.equals(other.grupo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
