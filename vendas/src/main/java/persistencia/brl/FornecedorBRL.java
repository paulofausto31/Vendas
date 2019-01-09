package persistencia.brl;

import android.content.Context;
import persistencia.dao.*;
import persistencia.dto.*;
import venda.util.Global;

public class FornecedorBRL {

    FornecedorDAO fornecedorDAO;
    Context ctx;

    public FornecedorBRL(){}
    
    public FornecedorBRL(Context ctx)
    {
    	this.ctx = ctx;
        if (fornecedorDAO == null)
            fornecedorDAO = new FornecedorDAO(ctx);
    }
    
    public void CloseConection(){
    	fornecedorDAO.CloseConection();
    }

    public FornecedorDTO InstanciaFornecedor(String linha) 
    {
        FornecedorDTO dto = new FornecedorDTO();
        //dto.setId(1);
        dto.setCodEmpresa(Global.codEmpresa);
        dto.setCodFornecedor(Integer.parseInt(linha.substring(0, 4)));
        dto.setDescricao(linha.substring(4, 33));
        return dto;
    }
    
    public boolean InsereFornecedor(FornecedorDTO forDto) 
    {
        try
        {
            return fornecedorDAO.insert(forDto);
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
            return fornecedorDAO.deleteAll();
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
            return fornecedorDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }
}
