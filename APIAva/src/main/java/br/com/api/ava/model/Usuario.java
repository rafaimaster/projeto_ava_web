/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.api.ava.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy=InheritanceType.JOINED) 
public class Usuario implements Serializable {

	private static final long serialVersionUID = -8125363783011438933L;

	@SequenceGenerator(name = "sq_generator_user", sequenceName = "sq_usuario",allocationSize=1, initialValue = 1)
	@Id
	@GeneratedValue(generator="sq_generator_user", strategy=GenerationType.SEQUENCE)
	@Column(name = "ID")
    private Long id;

    @NotNull
    @Size(min = 1, max = 150)
    @Pattern(regexp = "[^0-9]*", message = "O nome não pode conter números.")
    @Column(name = "NOME", nullable = false)
    private String nome;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_perfil_usuario", referencedColumnName="id")
    private PerfilUsuario perfilUsuario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_CADASTRO", nullable = false)
    private Date dtCadastro;
    
    @NotNull(message="O campo de Data de Nascimento precisa estar preenchido.")
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_NASCIMENTO", nullable = true)
    private Date dtNascimento;
    
    @NotNull(message= "O campo 'Sexo' precisa estar preenchido.")
    @Size(min = 1, max = 1, message= "O campo 'Sexo' precisa estar preenchido.")
    @Pattern(regexp = "M|F", message = "O campo 'Sexo' só permite M|F.")
    @Column(name = "IE_SEXO", nullable = false)
    private String ieSexo;
    
    @Column(name = "IE_SITUACAO", nullable=false)
    private Boolean situacao;
    
    @OneToOne(fetch=FetchType.LAZY, optional=false, cascade=CascadeType.PERSIST)
    @JoinColumn( name="ID", unique=true, nullable=false, updatable=false)
    private LoginUsuario loginUsuario; 
    
    
    @ManyToMany 
    @JoinTable(name="GRUPO_ACESSO_USUARIO", 
    	joinColumns= {@JoinColumn(name="id")},
    	inverseJoinColumns= {@JoinColumn(name="id_grupo")}) 
    private List<GrupoAcesso> grupoAcesso;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public PerfilUsuario getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(PerfilUsuario perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getIeSexo() {
		return ieSexo;
	}

	public void setIeSexo(String ieSexo) {
		this.ieSexo = ieSexo;
	}

	public LoginUsuario getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(LoginUsuario loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dtCadastro == null) ? 0 : dtCadastro.hashCode());
		result = prime * result
				+ ((dtNascimento == null) ? 0 : dtNascimento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ieSexo == null) ? 0 : ieSexo.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Usuario other = (Usuario) obj;
		if (dtCadastro == null) {
			if (other.dtCadastro != null)
				return false;
		} else if (!dtCadastro.equals(other.dtCadastro))
			return false;
		if (dtNascimento == null) {
			if (other.dtNascimento != null)
				return false;
		} else if (!dtNascimento.equals(other.dtNascimento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ieSexo == null) {
			if (other.ieSexo != null)
				return false;
		} else if (!ieSexo.equals(other.ieSexo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		return true;
	}

}
