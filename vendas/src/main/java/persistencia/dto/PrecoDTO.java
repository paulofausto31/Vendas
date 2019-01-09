package persistencia.dto;

public class PrecoDTO {

	Integer id;
	String codEmpresa;
	Long codProduto;
    Double preco;
    
	public PrecoDTO(){
		
	}
	
	public PrecoDTO(Integer id, String codEmpresa, Long codProduto, Double preco){
		this.id = id;
		this.codEmpresa = codEmpresa;
		this.codProduto = codProduto;
		this.preco = preco;
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
	public void setCodEmpresa(String codEmpresa) { this.codEmpresa = codEmpresa; }
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
}
