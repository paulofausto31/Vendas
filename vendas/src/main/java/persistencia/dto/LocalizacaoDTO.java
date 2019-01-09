package persistencia.dto;

public class LocalizacaoDTO {

	Integer id;
	String codEmpresa;
	String codVendedor;
	String dataHora;
	String latitude;
	String longitude;
	
	public LocalizacaoDTO(){}
	
	public LocalizacaoDTO(Integer id, String codEmpresa, String codVendedor,
			String dataHora, String latitude, String longitude) {
		super();
		this.id = id;
		this.codEmpresa = codEmpresa;
		this.codVendedor = codVendedor;
		this.dataHora = dataHora;
		this.latitude = latitude;
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

	public String getCodVendedor() {
		return codVendedor;
	}

	public void setCodVendedor(String codVendedor) {
		this.codVendedor = codVendedor;
	}

	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
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
	
	
}
