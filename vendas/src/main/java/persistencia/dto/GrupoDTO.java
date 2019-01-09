package persistencia.dto;

public class GrupoDTO {

	Integer id;
	String codEmpresa;
	String codGrupo;
    String descricao;
    
	public GrupoDTO(){
		
	}
	
	public GrupoDTO(int id, String codEmpresa, String codGrupo, String descricao){
		this.id = id;
		this.codEmpresa = codEmpresa;
		this.codGrupo = codGrupo;
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
	public String getCodGrupo() {
		return codGrupo;
	}
	public void setCodGrupo(String codGrupo) {
		this.codGrupo = codGrupo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}    
}
