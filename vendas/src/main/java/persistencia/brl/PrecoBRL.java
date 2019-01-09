package persistencia.brl;

import android.content.Context;

import java.util.List;

import persistencia.dao.PrecoDAO;
import persistencia.dto.PrecoDTO;
import venda.util.Global;

public class PrecoBRL {

    PrecoDAO precoDAO;
    Context ctx;

    public PrecoBRL(){}
    
    public PrecoBRL(Context ctx)
    {
        if (precoDAO == null)
            precoDAO = new PrecoDAO(ctx);
    }
    
    public void CloseConection(){
    	precoDAO.CloseConection();
    }

    public PrecoDTO InstanciaPreco(String linha) 
    {
        PrecoDTO dto = new PrecoDTO();
        //dto.setId(1);
        dto.setCodEmpresa(Global.codEmpresa);
        dto.setCodProduto(Long.parseLong(linha.substring(0, 10)));
        dto.setPreco(Double.parseDouble(linha.substring(10, 20).replace(',', '.')));
        return dto;
    }
    
    public boolean InserePreco(PrecoDTO preDto)
    {
        try
        {
            return precoDAO.insert(preDto);
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
            return precoDAO.deleteAll();
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
            return precoDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public List<String> getCombo(String campo, String item0, Integer codProduto, Double desconto){
    	return precoDAO.getCombo(campo, item0, codProduto, desconto);
    }

    public List<String> getCombo(String campo, Long codProduto, Double desconto, String paramDA){
    	return precoDAO.getCombo(campo, codProduto, desconto, paramDA);
    }
    
    public List<PrecoDTO> getByProduto(Long codProduto){
    	return precoDAO.getByProduto(codProduto);
    }

    public PrecoDTO getById(Integer idPreco){
    	return precoDAO.getById(idPreco);
    }
}
