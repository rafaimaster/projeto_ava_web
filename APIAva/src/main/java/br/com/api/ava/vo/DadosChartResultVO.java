package br.com.api.ava.vo;

import java.util.Collection;
import java.util.List;

public class DadosChartResultVO {

	private Collection<String> labels;
	private Collection<String> series;
	private List<List<Integer>> dados;
	private List<Integer> dados1;
	
	public Collection<String> getLabels() {
		return labels;
	}
	public void setLabels(Collection<String> labels) {
		this.labels = labels;
	}
	public Collection<String> getSeries() {
		return series;
	}
	public void setSeries(Collection<String> series) {
		this.series = series;
	}
	public List<List<Integer>> getDados() {
		return dados;
	}
	public void setDados(List<List<Integer>> dados) {
		this.dados = dados;
	}
	public List<Integer> getDados1() {
		return dados1;
	}
	public void setDados1(List<Integer> dados1) {
		this.dados1 = dados1;
	}	
}
