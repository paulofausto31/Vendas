package persistencia.dto;

public class FornecedorDTO {

	Integer id;
	String codEmpresa;
	Integer codFornecedor;
    String descricao;
    
	public FornecedorDTO(){
		
	}
	
	public FornecedorDTO(Integer id, String codEmpresa, Integer codFornecedor, String descricao){
		this.id = id;
		this.codEmpresa = codEmpresa;
		this.codFornecedor = codFornecedor;
		this.descricao = descricao;
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
	public Integer getCodFornecedor() {
		return codFornecedor;
	}
	public void setCodFornecedor(Integer codFornecedor) {
		this.codFornecedor = codFornecedor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
