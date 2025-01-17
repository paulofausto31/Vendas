package venda.util;

import java.util.ArrayList;
import java.util.List;

import persistencia.dto.CaminhoFTPDTO;
import persistencia.dto.ClienteDTO;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.PedidoDTO;

public class Global {
	public static PedidoDTO pedidoGlobalDTO;
	public static ItenPedidoDTO itemPedidoGlobalDTO;
	public static List<ClienteDTO> lstClientes;
	public static String pedidoWS;
	public static CaminhoFTPDTO caminhoFTPDTO;
	public static String codEmpresa;
	public static String codRota;
	public static String tituloAplicacao;
	public static String codVendedor;
	public static int idCliente;
	public static String prodPesquisa;
	public static Double descontoAcrescimo;
	public static boolean optionDesconto;
	public static String filtroLinha;
	public static Integer filtroFornecedor;
	public static String retornoThead;
	public static Double totalContasReceber;
	public static Double totalLimiteCredito;
	public static ArrayList<String> retornoimportacao;
}
