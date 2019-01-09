package persistencia.brl;

import android.content.Context;

import java.util.List;

import persistencia.dao.ContaReceberDAO;
import persistencia.dto.ContaReceberDTO;
import venda.util.Global;

public class ContaReceberBRL {
	ContaReceberDAO contaReceberDAO;
	Context ctx;

    public ContaReceberBRL(){}
    
    public ContaReceberBRL(Context ctx)
    {
    	this.ctx = ctx;
        if (contaReceberDAO == null)
            contaReceberDAO = new ContaReceberDAO(ctx);
    }
    
    public void CloseConection(){
    	contaReceberDAO.CloseConection();
    }

    public ContaReceberDTO InstanciaContaReceber(String linha) 
    {
    	ContaReceberDTO dto = new ContaReceberDTO();
        //dto.setId(1);
        dto.setCodEmpresa(Global.codEmpresa);
        dto.setCodCliente(Integer.parseInt(linha.substring(0, 6)));
        dto.setDocumento(linha.substring(6, 16));
        dto.setDataVencimento(linha.substring(16, 26));
        dto.setDataPromessa(linha.substring(26, 36));
        dto.setValor(Double.parseDouble(linha.substring(36, 44).replace(',', '.')));
        dto.setFormaPgto(linha.substring(44, 47));
        return dto;
    }
    
    public boolean InsereContaReceber(ContaReceberDTO creDto) 
    {
        try
        {
            return contaReceberDAO.insert(creDto);
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
            return contaReceberDAO.deleteAll();
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
            return contaReceberDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public List<ContaReceberDTO> getAll(){
    	return contaReceberDAO.getAll();
    }
    
    public ContaReceberDTO getById(int id){
    	return contaReceberDAO.getById(id);
    }
    
    public List<ContaReceberDTO> getByCliente(Integer codCliente){
    	return contaReceberDAO.getByCliente(codCliente);
    }

}
