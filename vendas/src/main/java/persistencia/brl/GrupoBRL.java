package persistencia.brl;

import android.content.Context;

import java.util.List;

import persistencia.dao.GrupoDAO;
import persistencia.dto.GrupoDTO;
import venda.util.Global;


public class GrupoBRL {
    GrupoDAO grupoDAO;
    Context ctx;

    public GrupoBRL(){}
    
    public GrupoBRL(Context ctx)
    {
    	this.ctx = ctx;
        if (grupoDAO == null)
            grupoDAO = new GrupoDAO(ctx);
    }
    
    public void CloseConection(){
    	grupoDAO.CloseConection();
    }

    public GrupoDTO InstanciaGrupo(String linha) 
    {
    	GrupoDTO dto = new GrupoDTO();
        //dto.setId(1);
        dto.setCodEmpresa(Global.codEmpresa);
        dto.setCodGrupo(linha.substring(0, 3));
        dto.setDescricao(linha.substring(3, 18));
        return dto;
    }
    
    public boolean InsereGrupo(GrupoDTO grpDto) 
    {
        try
        {
            return grupoDAO.insert(grpDto);
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
            return grupoDAO.deleteAll();
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
            return grupoDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public List<GrupoDTO> getAll(){
		GrupoDAO dao = new GrupoDAO(ctx);
		
		return dao.getAll();    	
    }

}
