package persistencia.dto;

import java.util.Date;


/**
 * Classe de modelagem do objeto Pessoa
 * @author Adauto
 *
 */
public class SitLGPSDTO {

	/**
	 * Atributos da classe modelados de acordo com a tabela Pessoa do banco de dados
	 */
	Long sr_recno;
	double loclie;
	double lovend;
	Date lodata;
	String lohora;
	String logps1;
	String logps2;
	Character sr_deleted;

	

	/**
	 * Construtor que inicializa um objeto sem ID
	 * @param nome
	 */
	public SitLGPSDTO(double loclie, double lovend, Date lodata,
			String lohora, String logps1, String logps2, Character sr_deleted) {
		super();
		this.loclie = loclie;
		this.lovend = lovend;
		this.lodata = lodata;
		this.lohora = lohora;
		this.logps1 = logps1;
		this.logps2 = logps2;
		this.sr_deleted = sr_deleted;
	}

	public SitLGPSDTO(Long sr_recno, String logps1, String logps2) {
		super();
		this.sr_recno = sr_recno;
		this.logps1 = logps1;
		this.logps2 = logps2;
	}

	/**
	 * Construtor que inicializa um objeto com ID
	 * @param pessoaId
	 * @param nome
	 */
	public SitLGPSDTO(Long sr_recno, double loclie, double lovend, Date lodata,
			String lohora, String logps1, String logps2, Character sr_deleted) {
		super();
		this.sr_recno = sr_recno;
		this.loclie = loclie;
		this.lovend = lovend;
		this.lodata = lodata;
		this.lohora = lohora;
		this.logps1 = logps1;
		this.logps2 = logps2;
		this.sr_deleted = sr_deleted;
	}

	public SitLGPSDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getSr_recno() {
		return sr_recno;
	}

	public void setSr_recno(Long sr_recno) {
		this.sr_recno = sr_recno;
	}

	public double getLoclie() {
		return loclie;
	}

	public void setLoclie(double loclie) {
		this.loclie = loclie;
	}

	public double getLovend() {
		return lovend;
	}

	public void setLovend(double lovend) {
		this.lovend = lovend;
	}

	public Date getLodata() {
		return lodata;
	}

	public void setLodata(Date lodata) {
		this.lodata = lodata;
	}

	public String getLohora() {
		return lohora;
	}

	public void setLohora(String lohora) {
		this.lohora = lohora;
	}

	public String getLogps1() {
		return logps1;
	}

	public void setLogps1(String logps1) {
		this.logps1 = logps1;
	}

	public String getLogps2() {
		return logps2;
	}

	public void setLogps2(String logps2) {
		this.logps2 = logps2;
	}

	public Character getSr_deleted() {
		return sr_deleted;
	}

	public void setSr_deleted(Character sr_deleted) {
		this.sr_deleted = sr_deleted;
	}

	/**
	 * Método que exibe o valor dos atributos do objeto Pessoa
	 */
	@Override
	public String toString() {
		return this.sr_recno + " - " + this.logps1 + " - " + this.logps2;
	}
}
