package persistencia.dto;

public class ClienteDTO {

	Integer id;
	String codEmpresa;
	Integer codCliente;
    String nome;
    String endereco;
    String telefone;
    String dataUltimaCompra;
    Double valorAtraso;
    Double valorVencer;
    String formaPgto;
    Integer prazo;
    String cpfCnpj;
    Integer seqVisita;
    String infAdicional;
    String razaoSocial;
    String bairro;
    String cidade;
    String rotaDia;
    Double limiteCredito;
    
	public ClienteDTO(){
		
	}
	
	public ClienteDTO(Integer id, String codEmpresa, Integer codCliente, String nome, String endereco
		, String telefone, String dataUltimaCompra, Double valorAtraso, Double valorVencer
		, String formaPgto, Integer prazo, String cpfCnpj, Integer seqVisita, String infAdicional,
		String razaoSocial, String bairro, String Cidade, String rotaDia, Double limiteCredito){
		this.id = id;
		this.codEmpresa = codEmpresa;
		this.codCliente = codCliente;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.dataUltimaCompra = dataUltimaCompra;
		this.valorAtraso = valorAtraso;
		this.valorVencer = valorVencer;
		this.formaPgto = formaPgto;
		this.prazo = prazo;
		this.cpfCnpj = cpfCnpj;
		this.seqVisita = seqVisita;
		this.infAdicional = infAdicional;
		this.razaoSocial = razaoSocial;
		this.bairro = bairro;
		this.cidade = cidade;
		this.rotaDia = rotaDia;
		this.limiteCredito = limiteCredito;
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getDataUltimaCompra() {
		return dataUltimaCompra;
	}
	public void setDataUltimaCompra(String dataUltimaCompra) {
		this.dataUltimaCompra = dataUltimaCompra;
	}
	public Double getValorAtraso() {
		return valorAtraso;
	}
	public void setValorAtraso(Double valorAtraso) {
		this.valorAtraso = valorAtraso;
	}
	public Double getValorVencer() {
		return valorVencer;
	}
	public void setValorVencer(Double valorVencer) {
		this.valorVencer = valorVencer;
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
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	public Integer getSeqVisita() {
		return seqVisita;
	}
	public void setSeqVisita(Integer seqVisita) {
		this.seqVisita = seqVisita;
	}
	public String getInfAdicional() {
		return infAdicional;
	}
	public void setInfAdicional(String infAdicional) {
		this.infAdicional = infAdicional;
	}

	public String getRotaDia() {
		return rotaDia;
	}
	public void setRotaDia(String rotaDia) {
		this.rotaDia = rotaDia;
	}
	public Double getLimiteCredito() {
		return limiteCredito;
	}
	public void setLimiteCredito(Double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}
}
