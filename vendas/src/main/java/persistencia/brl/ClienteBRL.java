package persistencia.brl;

import android.content.Context;
import java.util.List;
import persistencia.dao.*;
import persistencia.dto.*;
import venda.util.Global;
import venda.util.Util;

public class ClienteBRL {

    ClienteDAO clienteDAO;
    Context ctx;

    public ClienteBRL(){}
    
    public ClienteBRL(Context ctx)
    {
    	this.ctx = ctx;
        if (clienteDAO == null)
            clienteDAO = new ClienteDAO(ctx);
    }
    
    public void CloseConection(){
    	clienteDAO.CloseConection();
    }

    public ClienteDTO InstanciaCliente(String linha) 
    {
    	ClienteDTO dto = new ClienteDTO();
        //dto.setId(1);
        dto.setCodEmpresa(Global.codEmpresa);
        dto.setCodCliente(Integer.parseInt(linha.substring(0, 6)));
        dto.setNome(linha.substring(6, 41));
        dto.setEndereco(linha.substring(41, 81));
        dto.setTelefone(linha.substring(81, 94));
        dto.setDataUltimaCompra(linha.substring(94, 104));
        dto.setValorAtraso(Double.parseDouble(linha.substring(104, 115).replace(',', '.')));
        //String temp = linha.substring(112, 8);
        dto.setValorVencer(Double.parseDouble(linha.substring(115, 126).replace(',', '.')));
        dto.setFormaPgto(linha.substring(126, 130));
        dto.setPrazo(Integer.parseInt(linha.substring(130, 134)));
        dto.setCpfCnpj(Util.removerMascaraCPF(linha.substring(134, 152).trim()));
        dto.setSeqVisita(Integer.parseInt(linha.substring(152, 156)));
        dto.setLimiteCredito(Double.parseDouble(linha.substring(156,164).replace(',', '.')));
        dto.setInfAdicional(linha.substring(164, 173));
        dto.setRazaoSocial(linha.substring(173, 208));
        dto.setCidade(linha.substring(208, 228));
        dto.setBairro(linha.substring(228, 243));
        dto.setRotaDia(linha.substring(243, 244));
        return dto;
    }

    public ClienteDTO InstanciaClienteOLD(String linha)
    {
        ClienteDTO dto = new ClienteDTO();
        //dto.setId(1);
        dto.setCodEmpresa(Global.codEmpresa);
        dto.setCodCliente(Integer.parseInt(linha.substring(0, 6)));
        dto.setNome(linha.substring(6, 41));
        dto.setEndereco(linha.substring(41, 81));
        dto.setTelefone(linha.substring(81, 94));
        dto.setDataUltimaCompra(linha.substring(94, 104));
        dto.setValorAtraso(Double.parseDouble(linha.substring(104, 115).replace(',', '.')));
        //String temp = linha.substring(112, 8);
        dto.setValorVencer(Double.parseDouble(linha.substring(115, 126).replace(',', '.')));
        dto.setFormaPgto(linha.substring(126, 130));
        dto.setPrazo(Integer.parseInt(linha.substring(130, 134)));
        dto.setCpfCnpj(linha.substring(134, 152));
        dto.setSeqVisita(Integer.parseInt(linha.substring(152, 155)));
        dto.setLimiteCredito(Double.parseDouble(linha.substring(155,162).replace(',', '.')));
        dto.setInfAdicional(linha.substring(162, 170));
        dto.setRazaoSocial(linha.substring(170, 205));
        dto.setBairro(linha.substring(205, 225));
        dto.setCidade(linha.substring(225, 240));
        dto.setRotaDia(linha.substring(240, 241));
        return dto;
    }
    
    public boolean InsereCliente(ClienteDTO cliDto) 
    {
        try
        {
            return clienteDAO.insert(cliDto);
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
    		return false;
        }
    }    

    public boolean DeleteAll() 
    {
        try
        {
            return clienteDAO.deleteAll();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
    		return false;
        }
    }

    public boolean DeleteByEmpresa()
    {
        try
        {
            return clienteDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public List<ClienteDTO> getAll(){
    	return clienteDAO.getAll();
    }

    public ClienteDTO getById(int idCliente){
    	return clienteDAO.getById(idCliente);
    }

    public List<ClienteDTO> getRotaDiaOrdenado(String campoOrdenacao){
    	return clienteDAO.getRotaDiaOrdenado(campoOrdenacao);
    }

    public List<ClienteDTO> getPorRotaOrdenado(String codRota, String campoOrdenacao){
        return clienteDAO.getPorRotaOrdenado(codRota, campoOrdenacao);
    }

    public List<ClienteDTO> getTodosOrdenado(String campoOrdenacao){
        return clienteDAO.getTodosOrdenado(campoOrdenacao);
    }

    public List<ClienteDTO> getByNome(String nome){
    	return clienteDAO.getByNome(nome);
    }

    public List<ClienteDTO> getByCodigo(String codigo){
        return clienteDAO.getByCodigo(codigo);
    }

    public List<ClienteDTO> getByCPFCNPJ(String cpfCnpj){ return clienteDAO.getByCPFCNPJ(cpfCnpj);
    }
    
    public List<ClienteDTO> getByRazaoSocial(String razaoSocial){
    	return clienteDAO.getByRazaoSocial(razaoSocial);
    }

    public ClienteDTO getByCodCliente(Integer codCliente){
    	return clienteDAO.getByCodCliente(codCliente);
    }
    
    public Integer getTotalClientes(){
    	return clienteDAO.getTotalClientes();
    }
}

