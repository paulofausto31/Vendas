package persistencia.dto;

public class JustificativaPositivacaoDTO {

	Integer id;
	String codEmpresa;
    Integer codJustPos;
    String descricao;
    
	
	public JustificativaPositivacaoDTO(){
		
	}
	
	public JustificativaPositivacaoDTO(Integer id, String codEmpresa, Integer codJustPos, String descricao){
		this.id = id;
		this.codEmpresa = codEmpresa;
		this.codJustPos = codJustPos;
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
	public Integer getCodJustPos() {
		return codJustPos;
	}
	public void setCodJustPos(Integer codJustPos) {
		this.codJustPos = codJustPos;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
