package persistencia.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PedidoDTO {

    Integer id;
	@SerializedName("empresa")
	@Expose
	String codEmpresa;
	@SerializedName("CodCliente")
	@Expose
    Integer codCliente;
	@SerializedName("CodVendedor")
	@Expose
    Integer codVendedor;
	@SerializedName("FormaPgto")
	@Expose
    String formaPgto;
	@SerializedName("Prazo")
	@Expose
    Integer prazo;
	@SerializedName("Parcela")
	@Expose
    Integer parcela;
	@SerializedName("DataIni")
	@Expose
    String dataPedido;
	@SerializedName("HoraIni")
	@Expose
    String horaPedido;
    Integer baixado;
	@SerializedName("DataFim")
	@Expose
    String dataPedidoFim;
	@SerializedName("HoraFim")
	@Expose
    String horaPedidoFim;
    String infAdicional;
    String latitude;
    String longitude;
    String fechado;
	@SerializedName("ItemPedido")
	@Expose
	List<ItenPedidoDTO> itemPedido;
	Integer codPedidoMySQL;
	String dataPedidoEnvio;
	String horaPedidoEnvio;
	String dataEntrega;

    
	public PedidoDTO(){
		
	}
	
	public PedidoDTO(Integer id, String codEmpresa, Integer codCliente, Integer codVendedor, String formaPgto
		, Integer prazo, Integer parcela, String dataPedido, String horaPedido
		, Integer baixado, String dataPedidoFim, String horaPedidoFim, String infAdicional,
		String latitude, String longitude, String fechado, Integer codPedidoMySQL, String dataPedidoEnvio, String horaPedidoEnvio,
		String dataEntrega){
		this.id = id;
		this.codEmpresa = codEmpresa;
		this.codCliente = codCliente;
		this.codVendedor = codVendedor;
		this.formaPgto = formaPgto;
		this.prazo = prazo;
		this.parcela = parcela;
		this.dataPedido = dataPedido;
		this.horaPedido = horaPedido;
		this.baixado = baixado;
		this.dataPedidoFim = dataPedidoFim;
		this.horaPedidoFim = horaPedidoFim;
		this.infAdicional = infAdicional;
		this.latitude = latitude;
		this.longitude = longitude;
		this.fechado = fechado;
		this.codPedidoMySQL = codPedidoMySQL;
		this.dataPedidoEnvio = dataPedidoEnvio;
		this.horaPedidoEnvio = horaPedidoEnvio;
		this.dataEntrega = dataEntrega;
	}
    
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
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
	public Integer getCodVendedor() {
		return codVendedor;
	}
	public void setCodVendedor(Integer codVendedor) {
		this.codVendedor = codVendedor;
	}
	public String getFormaPgto() {
		return formaPgto;
	}
	public void setFormaPgto(String formaPgto) {
		this.formaPgto = formaPgto;
	}
	public Integer getPrazo() {
		return prazo;
	}
	public void setPrazo(Integer prazo) {
		this.prazo = prazo;
	}
	public Integer getParcela() {
		return parcela;
	}
	public void setParcela(Integer parcela) {
		this.parcela = parcela;
	}
	public String getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(String dataPedido) {
		this.dataPedido = dataPedido;
	}
	public String getHoraPedido() {
		return horaPedido;
	}
	public void setHoraPedido(String horaPedido) {
		this.horaPedido = horaPedido;
	}
	public Integer getBaixado() {
		return baixado;
	}
	public void setBaixado(Integer baixado) {
		this.baixado = baixado;
	}
	public String getDataPedidoFim() {
		return dataPedidoFim;
	}
	public void setDataPedidoFim(String dataPedidoFim) {
		this.dataPedidoFim = dataPedidoFim;
	}
	public String getHoraPedidoFim() {
		return horaPedidoFim;
	}
	public void setHoraPedidoFim(String horaPedidoFim) {
		this.horaPedidoFim = horaPedidoFim;
	}
	public String getInfAdicional() {
		return infAdicional;
	}
	public void setInfAdicional(String infAdicional) {
		this.infAdicional = infAdicional;
	}
	public String getFechado() {
		return fechado;
	}
	public void setFechado(String fechado) {
		this.fechado = fechado;
	}
	public List<ItenPedidoDTO> getItemPedido() {
		return itemPedido;
	}
	public void setItemPedido(List<ItenPedidoDTO> itemPedido) {
		this.itemPedido = itemPedido;
	}
	public Integer getCodPedidoMySQL() {
		return codPedidoMySQL;
	}
	public void setCodPedidoMySQL(Integer codPedidoMySQL) {
		this.codPedidoMySQL = codPedidoMySQL;
	}
	public String getDataPedidoEnvio() {
		return dataPedidoEnvio;
	}
	public void setDataPedidoEnvio(String dataPedidoEnvio) {
		this.dataPedidoEnvio = dataPedidoEnvio;
	}
	public String getHoraPedidoEnvio() {
		return horaPedidoEnvio;
	}
	public void setHoraPedidoEnvio(String horaPedidoEnvio) {
		this.horaPedidoEnvio = horaPedidoEnvio;
	}
	public String getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(String dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
}
