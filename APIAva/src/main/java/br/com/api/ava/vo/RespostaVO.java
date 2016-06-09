package br.com.api.ava.vo;


public class RespostaVO {

	private Long id;
    private String descricao;
    private PerguntaVO perguntaVO;
    private AlternativaVO alternativaVO;
    
    
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
	public PerguntaVO getPerguntaVO() {
		return perguntaVO;
	}
	public void setPerguntaVO(PerguntaVO perguntaVO) {
		this.perguntaVO = perguntaVO;
	}
	public AlternativaVO getAlternativaVO() {
		return alternativaVO;
	}
	public void setAlternativaVO(AlternativaVO alternativaVO) {
		this.alternativaVO = alternativaVO;
	}
	
}
