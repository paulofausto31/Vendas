package persistencia.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClienteNaoPositivadoDTO {

	Integer id;
	@SerializedName("codempresa")
	@Expose
	String codEmpresa;
	@SerializedName("codcliente")
	@Expose
    Integer codCliente;
	@SerializedName("codjustificativa")
	@Expose
    Integer codJustificativa;
	@SerializedName("obs")
	@Expose
    String obs;
	@SerializedName("dataini")
	@Expose
    String data;
	@SerializedName("horaini")
	@Expose
    String hora;
	@SerializedName("datafim")
	@Expose
    String dataFim;
	@SerializedName("horafim")
	@Expose
    String horaFim;
	@SerializedName("baixado")
	@Expose
    Integer baixado;
    
	public ClienteNaoPositivadoDTO(){
		
	}
	
	public ClienteNaoPositivadoDTO(Integer id, String codEmpresa, Integer codCliente, Integer codJustificativa, String obs, String data,
			String hora, String dataFim, String horaFim, Integer baixado){
		this.id = id;
		this.codEmpresa = codEmpresa;
		this.codCliente = codCliente;
		this.codJustificativa = codJustificativa;
		this.obs = obs;
		this.data = data;
		this.hora = hora;
		this.dataFim = dataFim;
		this.horaFim = horaFim;
		this.baixado = baixado;
	}
    
	public Integer getBaixado() {
		return baixado;
	}
	public void setBaixado(Integer baixado) {
		this.baixado = baixado;
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
	public Integer getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(Integer codCliente) {
		this.codCliente = codCliente;
	}
	public Integer getCodJustificativa() {
		return codJustificativa;
	}
	public void setCodJustificativa(Integer codJustificativa) {
		this.codJustificativa = codJustificativa;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getDataFim() {
		return dataFim;
	}
	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	public String getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}
}
