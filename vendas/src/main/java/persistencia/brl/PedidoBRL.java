package persistencia.brl;

import android.content.Context;
import java.util.List;
import persistencia.dao.*;
import persistencia.dto.*;

public class PedidoBRL {

    PedidoDAO pedidoDAO;
    Context ctx;

    public PedidoBRL(){}
    
    public PedidoBRL(Context ctx)
    {
    	this.ctx = ctx;
        if (pedidoDAO == null)
            pedidoDAO = new PedidoDAO(ctx);
    }
    
    public void CloseConection(){
    	pedidoDAO.CloseConection();
    }

    public boolean InserePedido(PedidoDTO pedDto) 
    {
        try
        {
            return pedidoDAO.insert(pedDto);
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
    		return false;
        }
    }    

    public boolean Update(PedidoDTO pedDto) 
    {
        try
        {
            return pedidoDAO.update(pedDto);
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
            return pedidoDAO.deleteAll();
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
            return pedidoDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public boolean UpdateBaixado()
    {
        try
        {
            return pedidoDAO.updateBaixado();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
    		return false;
        }
    } 

    public boolean delete(PedidoDTO dto){
    	return pedidoDAO.delete(dto);
    }
    
    public List<PedidoDTO> getAll(){
    	return pedidoDAO.getAll();
    }
    
    public PedidoDTO getById(Integer id){
    	return pedidoDAO.getById(id);
    }
    
    public List<PedidoDTO> getByCodCliente(Integer codCliente){
    	return pedidoDAO.getByCodCliente(codCliente);
    }

    public int getPedidoAbertoCliente(Integer codCliente){
    	return pedidoDAO.getPedidoAbertoCliente(codCliente);
    }
    
    public Integer getIdLast(){
    	return pedidoDAO.getIdLast();
    }
    
    public List<PedidoDTO> getAllPedAberto(){
    	return pedidoDAO.getAllPedAberto();
    }
    
    public List<PedidoDTO> getAllPedEnviado(){
    	return pedidoDAO.getAllPedEnviado();
    }

    public boolean AbrePedidoBaixado(Integer id){
    	return pedidoDAO.AbrePedidoBaixado(id);
    }

    public boolean AbrePedidoPendente(Integer id){
        return pedidoDAO.AbrePedidoPendente(id);
    }

    public boolean FechaPedidoPendente(Integer id){
        return pedidoDAO.FechaPedidoPendente(id);
    }

    public Integer getTotalPositivacao(){
    	return pedidoDAO.getTotalPositivacao();
    }
    
    public List<PedidoDTO> getAllAberto(){
    	return pedidoDAO.getAllAberto();
    }
}

