package persistencia.dto;

public class CaminhoFTPDTO {
	Integer id;
	String codEmpresa;
    String serverLocal;
    String userLocal;
    String passwordLocal;
    String serverRemoto;
    String userRemoto;
    String passwordRemoto;
    String caminho;
    String caminhoManual;
    String portaFTP;
    String metodoEntrada;
    String comDefault;
    
	public CaminhoFTPDTO(){
		
	}
	
	public CaminhoFTPDTO(Integer id, String serverLocal, String userLocal, String passwordLocal,
			String serverRemoto, String userRemoto, String passwordRemoto, String caminho, String codEmpresa,
			String caminhoManual, String portaFTP, String metodoEntrada, String comDefault){
		this.id = id;
		this.serverLocal = serverLocal;
		this.userLocal = userLocal;
		this.passwordLocal = passwordLocal;
		this.serverRemoto = serverRemoto;
		this.userRemoto = userRemoto;
		this.passwordRemoto = passwordRemoto;
		this.caminho = caminho;
		this.codEmpresa = codEmpresa;
		this.caminhoManual = caminhoManual;
		this.portaFTP = portaFTP;
		this.metodoEntrada = metodoEntrada;
		this.comDefault = comDefault;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPortaFTP() { return portaFTP; }
	public void setPortaFTP(String portaFTP) { this.portaFTP = portaFTP; }
	public String getCodEmpresa() {
		return codEmpresa;
	}
	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}
	public String getServerLocal() {
		return serverLocal;
	}
	public void setServerLocal(String serverLocal) {
		this.serverLocal = serverLocal;
	}
	public String getUserLocal() {
		return userLocal;
	}
	public void setUserLocal(String userLocal) {
		this.userLocal = userLocal;
	}
	public String getPasswordLocal() {
		return passwordLocal;
	}
	public void setPasswordLocal(String passwordLocal) {
		this.passwordLocal = passwordLocal;
	}
	public String getServerRemoto() {
		return serverRemoto;
	}
	public void setServerRemoto(String serverRemoto) {
		this.serverRemoto = serverRemoto;
	}
	public String getUserRemoto() {
		return userRemoto;
	}
	public void setUserRemoto(String userRemoto) {
		this.userRemoto = userRemoto;
	}
	public String getPasswordRemoto() {
		return passwordRemoto;
	}
	public void setPasswordRemoto(String passwordRemoto) {
		this.passwordRemoto = passwordRemoto;
	}
	public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	public String getCaminhoManual() {
		return caminhoManual;
	}
	public void setCaminhoManual(String caminho) {
		this.caminhoManual = caminho;
	}
	public String getMetodoEntrada() {
		return metodoEntrada;
	}
	public void setMetodoEntrada(String metodoEntrada) {
		this.metodoEntrada = metodoEntrada;
	}
	public String getComDefault() {
		return comDefault;
	}
	public void setComDefault(String comDefault) {
		this.comDefault = comDefault;
	}
}
