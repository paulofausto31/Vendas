package persistencia.constantes;


public class Constants {
	/**
	 * Namespace definido no servidor
	 */
	public static final String NAMESPACE = "http://sitbd_webservice/";
	
	/**
	 * Endereço do WSDL gerado pelo servidor
	 */
	public static String WSDL_URL = "http://192.168.100.4:8080/sit?wsdl";

	/**
	 * Nome dos métodos anotados com @WebMethod na interface de comunicação
	 * com o servidor (PessoaBD)
	 */
	public static final String INSERIR_LOCALIZACAO_METHOD = "inserirLocalizacao";
	public static final String BUSCAR_LOCALIZACAO_METHOD = "buscarLocalizacao";
	public static final String LISTAR_LOCALIZACAO_METHOD = "listarLocalizacao";
	public static final String EXCLUIR_LOCALIZACAO_METHOD = "excluirLocalizacao";
	
	/**
	 * Endereços para se fazer a requisito via protocolo SOAP
	 */
	public static final String INSERIR_LOCALIZACAO_ACTION = "\"http://sitbd_webservice/inserirLocalizacao\"";
	public static final String BUSCAR_LOCALIZACAO_ACTION = "\"http://sitbd_webservice/buscarLocalizacao\"";
	public static final String LISTAR_LOCALIZACAO_ACTION = "\"http://sitbd_webservice/listarLocalizacao\"";
	public static final String EXCLUIR_LOCALIZACAO_ACTION = "\"http://sitbd_webservice/excluirLocalizacao\"";
}
