package persistencia.dto;

public class VendedorDTO {

	Integer id;
	String codEmpresa;
	Integer codigo;
	String nome;
	
	public VendedorDTO(){
		
	}
	
	public VendedorDTO(Integer id, String codEmpresa, Integer codigo, String nome){
		this.id = id;
		this.codEmpresa = codEmpresa;
		this.codigo = codigo;
		this.nome = nome;
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
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
