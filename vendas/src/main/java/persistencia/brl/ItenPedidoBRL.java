package persistencia.brl;

import android.content.Context;

import java.util.List;

import persistencia.dao.ItenPedidoDAO;
import persistencia.dto.ItenPedidoDTO;

public class ItenPedidoBRL {

    ItenPedidoDAO itenPedidoDAO;
    Context ctx;

    public ItenPedidoBRL(){}
    
    public ItenPedidoBRL(Context ctx)
    {
    	this.ctx = ctx;
        if (itenPedidoDAO == null)
        	itenPedidoDAO = new ItenPedidoDAO(ctx);
    }
    
    public void CloseConetion(){
    	itenPedidoDAO.CloseConection();
    }

    public boolean InsereItenPedido(ItenPedidoDTO itpDTO) 
    {
        try
        {
            return itenPedidoDAO.insert(itpDTO);
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
            return itenPedidoDAO.deleteAll();
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
            return itenPedidoDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public boolean Update(ItenPedidoDTO itpDTO)
    {
        try
        {
            return itenPedidoDAO.update(itpDTO);
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
    		return false;
        }
    }

    public boolean delete(ItenPedidoDTO dto){
    	return itenPedidoDAO.delete(dto);
    }
    
    public boolean deleteByCodPedido(Integer codPedido){
    	return itenPedidoDAO.deleteByCodPedido(codPedido);
    }
    
    public List<ItenPedidoDTO> getAll(){
    	return itenPedidoDAO.getAll();
    }
    
    public ItenPedidoDTO getById(Integer id){
    	return itenPedidoDAO.getById(id);
    }
    
    public List<ItenPedidoDTO> getByCodPedido(Integer codPedido){
    	return itenPedidoDAO.getByCodPedido(codPedido);
    }
    
    public Double getTotalPedido(Integer codPedido){
    	return itenPedidoDAO.getTotalPedido(codPedido);
    }
    
    public List<Long> getAllAberto(){
    	return itenPedidoDAO.getAllAberto();
    }
    
    public Double getSumQtdAberto(Long codProduto){
    	return itenPedidoDAO.getSumQtdAberto(codProduto);
    } 
    
    public  Double getSumTotalAberto(){
    	return itenPedidoDAO.getSumTotalAberto();
    }
    
    public  boolean ExisteDA(Integer codPedido){
    	return itenPedidoDAO.ExisteDA(codPedido);
    }

    public List<ItenPedidoDTO> getAllAbertos(){
    	return itenPedidoDAO.getAllAbertos();
    }
    
    public boolean produtoExistente(Integer codPedido, Integer codProduto){
    	return itenPedidoDAO.produtoExistente(codPedido, codProduto);
    }

    public ItenPedidoDTO getByCodProduto(Integer codPedido, Integer codProduto){
    	return itenPedidoDAO.getByCodProduto(codPedido, codProduto);
    }
}

