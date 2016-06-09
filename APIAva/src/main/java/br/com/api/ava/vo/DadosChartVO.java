package br.com.api.ava.vo;

public class DadosChartVO {

	private Integer idLabel;
	private String label;
	private Integer idSerie;
	private String serie;
	private Integer dados;
	public Integer getIdLabel() {
		return idLabel;
	}
	public void setIdLabel(Integer idLabel) {
		this.idLabel = idLabel;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Integer getIdSerie() {
		return idSerie;
	}
	public void setIdSerie(Integer idSerie) {
		this.idSerie = idSerie;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public Integer getDados() {
		return dados;
	}
	public void setDados(Integer dados) {
		this.dados = dados;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dados == null) ? 0 : dados.hashCode());
		result = prime * result + ((idLabel == null) ? 0 : idLabel.hashCode());
		result = prime * result + ((idSerie == null) ? 0 : idSerie.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((serie == null) ? 0 : serie.hashCode());
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
		DadosChartVO other = (DadosChartVO) obj;
		if (dados == null) {
			if (other.dados != null)
				return false;
		} else if (!dados.equals(other.dados))
			return false;
		if (idLabel == null) {
			if (other.idLabel != null)
				return false;
		} else if (!idLabel.equals(other.idLabel))
			return false;
		if (idSerie == null) {
			if (other.idSerie != null)
				return false;
		} else if (!idSerie.equals(other.idSerie))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (serie == null) {
			if (other.serie != null)
				return false;
		} else if (!serie.equals(other.serie))
			return false;
		return true;
	}
	
}
