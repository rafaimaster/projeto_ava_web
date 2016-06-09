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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name="LOGIN_USUARIO", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class LoginUsuario implements Serializable {

	private static final long serialVersionUID = -1988121876508347806L;

	
	@SequenceGenerator(name = "sq_generator_login_user", sequenceName = "sq_login_usuario",allocationSize=1, initialValue = 1)
	@Id
	@GeneratedValue(generator="sq_generator_login_user", strategy=GenerationType.SEQUENCE)
	@Column(name = "ID")
    private Long id;
	
    @Email
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SENHA", length = 512, nullable = true)
    private String senha;

    @Column(name = "TOKEN", length = 512, nullable = true)
    private String token;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_GERACAO_TOKEN")
    private Date dtGeracaoToken;
    
    @Column(name = "ID_FACEBOOK", nullable = true)
    private String idFacebook;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_REVOGACAO_ACESSO", nullable=true)
    private Date dtRevegacaoAcesso;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getDtGeracaoToken() {
		return dtGeracaoToken;
	}

	public void setDtGeracaoToken(Date dtGeracaoToken) {
		this.dtGeracaoToken = dtGeracaoToken;
	}

	public String getIdFacebook() {
		return idFacebook;
	}

	public void setIdFacebook(String idFacebook) {
		this.idFacebook = idFacebook;
	}

	public Date getDtRevegacaoAcesso() {
		return dtRevegacaoAcesso;
	}

	public void setDtRevegacaoAcesso(Date dtRevegacaoAcesso) {
		this.dtRevegacaoAcesso = dtRevegacaoAcesso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dtGeracaoToken == null) ? 0 : dtGeracaoToken.hashCode());
		result = prime
				* result
				+ ((dtRevegacaoAcesso == null) ? 0 : dtRevegacaoAcesso
						.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((idFacebook == null) ? 0 : idFacebook.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
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
		LoginUsuario other = (LoginUsuario) obj;
		if (dtGeracaoToken == null) {
			if (other.dtGeracaoToken != null)
				return false;
		} else if (!dtGeracaoToken.equals(other.dtGeracaoToken))
			return false;
		if (dtRevegacaoAcesso == null) {
			if (other.dtRevegacaoAcesso != null)
				return false;
		} else if (!dtRevegacaoAcesso.equals(other.dtRevegacaoAcesso))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idFacebook == null) {
			if (other.idFacebook != null)
				return false;
		} else if (!idFacebook.equals(other.idFacebook))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

}
