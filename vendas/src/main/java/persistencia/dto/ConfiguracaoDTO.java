package persistencia.dto;

public class ConfiguracaoDTO {

	Integer id;
    String descontoAcrescimo;
    String codEmpresa;
    String criticaEstoque;
    Integer prazoMaximo;
    Integer parcelaMaxima;
    Double descontoMaximo;
    String dataCarga;
    String horaCarga;
    String mensagem;
    
	public ConfiguracaoDTO(){
		
	}
	
	public ConfiguracaoDTO(Integer id, String descontoAcrescimo, String codEmpresa, String criticaEstoque,
			Integer prazoMaximo, Integer parcelaMaxima, Double descontoMaximo, String dataCarga, String horaCarga,
			String mensagem){
		this.id = id;
		this.descontoAcrescimo = descontoAcrescimo;
		this.codEmpresa = codEmpresa;
		this.criticaEstoque = criticaEstoque;
		this.prazoMaximo = prazoMaximo;
		this.parcelaMaxima = parcelaMaxima;
		this.descontoMaximo = descontoMaximo;
		this.dataCarga = dataCarga;
		this.horaCarga = horaCarga;
		this.mensagem = mensagem;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescontoAcrescimo() {
		return descontoAcrescimo;
	}
	public void setDescontoAcrescimo(String descontoAcrescimo) {
		this.descontoAcrescimo = descontoAcrescimo;
	}
	public String getCodEmpresa() {
		return codEmpresa;
	}
	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}
	public String getCriticaEstoque() {
		return criticaEstoque;
	}
	public void setCriticaEstoque(String criticaEstoque) {
		this.criticaEstoque = criticaEstoque;
	}
	public int getPrazoMaximo() {
		return prazoMaximo;
	}
	public void setPrazoMaximo(Integer prazoMaximo) {
		this.prazoMaximo = prazoMaximo;
	}
	public int getParcelaMaxima() {
		return parcelaMaxima;
	}
	public void setParcelaMaxima(Integer parcelaMaxima) {
		this.parcelaMaxima = parcelaMaxima;
	}
	public Double getDescontoMaximo() {
		return descontoMaximo;
	}
	public void setDescontoMaximo(Double descontoMaximo) {
		this.descontoMaximo = descontoMaximo;
	}
	public String getDataCarga() {
		return dataCarga;
	}
	public void setDataCarga(String dataCarga) {
		this.dataCarga = dataCarga;
	}
	public String getHoraCarga() {
		return horaCarga;
	}
	public void setHoraCarga(String horaCarga) {
		this.horaCarga = horaCarga;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
