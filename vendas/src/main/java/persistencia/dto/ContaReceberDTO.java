package persistencia.dto;

public class ContaReceberDTO {

	Integer id;
	String codEmpresa;
	Integer codCliente;
    String documento;
    String dataVencimento;
    String dataPromessa;
    Double valor;
    String formaPgto;
    
	public ContaReceberDTO(){
		
	}
	
	public ContaReceberDTO(Integer id, String codEmpresa, Integer codCliente, String documento, String dataVencimento,
			String dataPromessa, Double valor, String formaPgto){
		this.id = id;
		this.codEmpresa = codEmpresa;
		this.codCliente = codCliente;
		this.documento = documento;
		this.dataVencimento = dataVencimento;
		this.dataPromessa = dataPromessa;
		this.valor = valor;
		this.formaPgto = formaPgto;
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
	public int getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getFormaPgto() {
		return formaPgto;
	}
	public void setFormaPgto(String formaPgto) {
		this.formaPgto = formaPgto;
	}
	public String getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public String getDataPromessa() {
		return dataPromessa;
	}
	public void setDataPromessa(String dataPromessa) {
		this.dataPromessa = dataPromessa;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
}
