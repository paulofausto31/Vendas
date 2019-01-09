package persistencia.dto;

public class ProdutoDTO {

	Integer id;
	String codEmpresa;
	Long codProduto;
    String descricao;
    String unidade;
    Double estoque;
    String grupo;
    Integer fornecedor;
    Integer unidade2;
    
	public ProdutoDTO(){
		
	}
	
	public ProdutoDTO(Integer id, String codEmpresa, Long codProduto, String descricao, String unidade
		, Double estoque, String grupo, Integer fornecedor, Integer unidade2){
		this.id = id;
		this.codEmpresa = codEmpresa;
		this.codProduto = codProduto;
		this.descricao = descricao;
		this.unidade = unidade;
		this.estoque = estoque;
		this.grupo = grupo;
		this.fornecedor = fornecedor;
		this.unidade2 = unidade2;
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
	public Long getCodProduto() {
		return codProduto;
	}
	public void setCodProduto(Long codProduto) {
		this.codProduto = codProduto;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public Integer getUnidade2() {
		return unidade2;
	}
	public void setUnidade2(Integer unidade2) {
		this.unidade2 = unidade2;
	}
	public Double getEstoque() {
		return estoque;
	}
	public void setEstoque(Double estoque) {
		this.estoque = estoque;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public Integer getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Integer fornecedor) {
		this.fornecedor = fornecedor;
	}

}
