package persistencia.brl;

import android.content.Context;

import persistencia.dao.VendedorDAO;
import persistencia.dto.VendedorDTO;
import venda.util.Global;

public class VendedorBRL {

    VendedorDAO vendedorDAO;
    Context ctx;

    public VendedorBRL(){}
    
    public VendedorBRL(Context ctx)
    {
    	this.ctx = ctx;
        if (vendedorDAO == null)
            vendedorDAO = new VendedorDAO(ctx);
    }
    
    public void CloseConection(){
    	vendedorDAO.CloseConection();
    }

    public VendedorDTO InstanciaVendedor(String linha) 
    {
        VendedorDTO dto = new VendedorDTO();
        //dto.setId(1);
        dto.setCodEmpresa(Global.codEmpresa);
        dto.setCodigo(Integer.parseInt(linha.substring(0, 4)));
        dto.setNome(linha.substring(4, 13));
        return dto;
    }
    
    public boolean InsereVendedor(VendedorDTO venDto) 
    {
        try
        {
            return vendedorDAO.insert(venDto);
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
            return vendedorDAO.deleteAll();
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
            return vendedorDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public VendedorDTO getAll(){
    	return vendedorDAO.getAll();
    }

    public boolean getVendedorEmpresa(String codEmpresa, String codVendedor){
        return vendedorDAO.getVendedorEmpresa(codEmpresa, codVendedor);
    }

    public VendedorDTO getVendedorEmpresa(String codEmpresa){
        return vendedorDAO.getVendedorEmpresa(codEmpresa);
    }

}
