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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="TURMA")
public class Turma implements Serializable {

	private static final long serialVersionUID = -8125363783011438933L;

	@SequenceGenerator(name = "sq_generator_turma", sequenceName = "sq_turma",allocationSize=1, initialValue = 1)
	@Id
	@GeneratedValue(generator="sq_generator_turma", strategy=GenerationType.SEQUENCE)
	@Column(name = "ID")
    private Long id;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "NOME", nullable = false)
    private String nome;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_CADASTRO", nullable = false)
    private Date dtCadastro;
    
    @Column(name = "IE_SITUACAO", nullable=false)
    private Boolean situacao;
    
    @NotNull
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_ano_turma", referencedColumnName="id")
    private AnoTurma anoTurma;
    
    @ManyToOne 
    @JoinColumn(name = "id_professor", referencedColumnName = "id")
    private Professor professor;
    
//    @OneToMany
//    @JoinColumn ( name =  "id_turma" ) 
//    private List<Aluno> alunos = new ArrayList<Aluno>();

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

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	public AnoTurma getAnoTurma() {
		return anoTurma;
	}

	public void setAnoTurma(AnoTurma anoTurma) {
		this.anoTurma = anoTurma;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

//	public List<Aluno> getAlunos() {
//		return alunos;
//	}
//
//	public void setAlunos(List<Aluno> alunos) {
//		this.alunos = alunos;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dtCadastro == null) ? 0 : dtCadastro.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((professor == null) ? 0 : professor.hashCode());
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
		Turma other = (Turma) obj;
		if (dtCadastro == null) {
			if (other.dtCadastro != null)
				return false;
		} else if (!dtCadastro.equals(other.dtCadastro))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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

