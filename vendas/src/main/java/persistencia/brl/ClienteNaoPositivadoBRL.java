package persistencia.brl;

import android.content.Context;

import java.util.List;

import persistencia.dao.ClienteNaoPositivadoDAO;
import persistencia.dto.ClienteNaoPositivadoDTO;

public class ClienteNaoPositivadoBRL {
	ClienteNaoPositivadoDAO clienteNaoPositivadoDAO;
	Context ctx;

    public ClienteNaoPositivadoBRL(){}
    
    public ClienteNaoPositivadoBRL(Context ctx)
    {
    	this.ctx = ctx;
        if (clienteNaoPositivadoDAO == null)
        	clienteNaoPositivadoDAO = new ClienteNaoPositivadoDAO(ctx);
    }
    
    public void CloseConection(){
    	clienteNaoPositivadoDAO.CloseConection();
    }

    public boolean InsereClienteNaoPositivado(Context ctx, ClienteNaoPositivadoDTO cnpDto) 
    {
        try
        {
            return clienteNaoPositivadoDAO.insert(cnpDto);
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
    		return false;
        }
    } 
    
    public boolean delete(ClienteNaoPositivadoDTO dto)
    {
        try
        {
            return clienteNaoPositivadoDAO.delete(dto);
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
            return clienteNaoPositivadoDAO.deleteAll();
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
            return clienteNaoPositivadoDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public boolean Update(ClienteNaoPositivadoDTO cnpDTO)
    {
        try
        {
            return clienteNaoPositivadoDAO.update(cnpDTO);
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
    		return false;
        }    	
    }
    public List<ClienteNaoPositivadoDTO> getAll(){
    	return clienteNaoPositivadoDAO.getAll();
    }
    
    public List<ClienteNaoPositivadoDTO> getAllAberto(){
    	return clienteNaoPositivadoDAO.getAllAberto();
    }

    public boolean updateBaixado(){
    	return clienteNaoPositivadoDAO.updateBaixado();
    }
    public ClienteNaoPositivadoDTO getById(int idCliente){
    	return clienteNaoPositivadoDAO.getById(idCliente);
    }
    
    public List<ClienteNaoPositivadoDTO> getByCodCliente(Integer codCliente){
    	return clienteNaoPositivadoDAO.getByCodCliente(codCliente);
    }
    
    public int getCNPCliente(Integer codCliente){
    	return clienteNaoPositivadoDAO.getCNPCliente(codCliente);
    }
    
    public int getCNPClienteAberto(Integer codCliente){
    	return clienteNaoPositivadoDAO.getCNPClienteAberto(codCliente);
    }
}
