package persistencia.brl;

import android.content.Context;

import java.util.List;

import persistencia.dao.LocalizacaoDAO;
import persistencia.dto.LocalizacaoDTO;

public class LocalizacaoBRL {
    LocalizacaoDAO localizacaoDAO;
    Context ctx;

    public LocalizacaoBRL(){}
    
    public LocalizacaoBRL(Context ctx)
    {
    	this.ctx = ctx;
        if (localizacaoDAO == null)
        	localizacaoDAO = new LocalizacaoDAO(ctx);
    }
    
    public void CloseConection(){
    	localizacaoDAO.CloseConection();
    }

    public boolean InsereLocalizacao(LocalizacaoDTO locDto) 
    {
        try
        {
            return localizacaoDAO.insert(locDto);
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
            return localizacaoDAO.deleteAll();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
    		return false;
        }
    }  
    
    public List<LocalizacaoDTO> getAll(){
		LocalizacaoDAO dao = new LocalizacaoDAO(ctx);
		
		return dao.getAll();    	
    }



}
