package persistencia.dto;

public class FormaPgtoDTO {

	Integer id;
	String codEmpresa;
    String codFPgto;
    String descricao;
    Double multa;
    Double juros;
    
	public FormaPgtoDTO(){
		
	}
	
	public FormaPgtoDTO(int id, String codEmpresa, String codFPgto, String descricao, Double multa, Double juros){
		this.id = id;
		this.codEmpresa = codEmpresa;
		this.codFPgto = codFPgto;
		this.descricao = descricao;
		this.multa = multa;
		this.juros = juros;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCodEmpresa() {
		return codEmpresa;
	}
	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}
	public String getCodFPgto() {
		return codFPgto;
	}
	public void setCodFPgto(String codFPgto) {
		this.codFPgto = codFPgto;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getMulta() {
		return multa;
	}
	public void setMulta(Double multa) {
		this.multa = multa;
	}
	public Double getJuros() {
		return juros;
	}
	public void setJuros(Double juros) {
		this.juros = juros;
	}
}
