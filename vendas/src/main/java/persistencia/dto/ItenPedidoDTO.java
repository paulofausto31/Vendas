package persistencia.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItenPedidoDTO {

	Integer id;
	String codEmpresa;
	@SerializedName("idtabped")
	@Expose
    Integer codPedido;
	@SerializedName("codproduto")
	@Expose
    Long codProduto;
	@SerializedName("preco")
	@Expose
    Double preco;
	@SerializedName("quantidade")
	@Expose
    Double quantidade;
	@SerializedName("unidade")
	@Expose
    Integer unidade;
    String DA;
    Double DAValor;
    
	public ItenPedidoDTO(){
		
	}
	
	public ItenPedidoDTO(Integer id, String codEmpresa, Integer codPedido, Long codProduto, Double preco, Double quantidade, Integer unidade, String DA, Double DAValor){
		this.id = id;
		this.codEmpresa = codEmpresa;
		this.codPedido = codPedido;
		this.codProduto = codProduto;
		this.quantidade = quantidade;
		this.preco = preco;
		this.unidade = unidade;
		this.DA = DA;
		this.DAValor = DAValor;
	}
	
	public Integer getUnidade() {
		return unidade;
	}
	public void setUnidade(Integer unidade) {
		this.unidade = unidade;
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
	public Integer getCodPedido() {
		return codPedido;
	}
	public void setCodPedido(Integer codPedido) {
		this.codPedido = codPedido;
	}
	public Long getCodProduto() {
		return codProduto;
	}
	public void setCodProduto(Long codProduto) {
		this.codProduto = codProduto;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public String getDA() {
		return DA;
	}
	public void setDA(String DA) {
		this.DA = DA;
	}
	public Double getDAValor() {
		return DAValor;
	}
	public void setDAValor(Double DAValor) {
		this.DAValor = DAValor;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
}
