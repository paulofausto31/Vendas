package persistencia.brl;

import android.content.Context;

import java.util.List;

import persistencia.dao.FormaPgtoDAO;
import persistencia.dao.RotaDAO;
import persistencia.dto.FormaPgtoDTO;
import persistencia.dto.RotaDTO;
import venda.util.Global;

public class RotaBRL {
    RotaDAO rotaDAO;
    Context ctx;

    public RotaBRL(){}

    public RotaBRL(Context ctx)
    {
        this.ctx = ctx;
        if (rotaDAO == null)
            rotaDAO = new RotaDAO(ctx);
    }

    public void CloseConection(){
        rotaDAO.CloseConection();
    }

    public RotaDTO InstanciaRota(String linha)
    {
        RotaDTO dto = new RotaDTO();
        //dto.setId(1);
        dto.setCodEmpresa(Global.codEmpresa);
        dto.setCodRota(Integer.parseInt(linha.substring(0, 4).trim()));
        dto.setDescricao(linha.substring(4, 24));
        return dto;
    }

    public boolean InsereRota(RotaDTO rotaDTO)
    {
        try
        {
            return rotaDAO.insert(rotaDTO);
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
            return rotaDAO.deleteAll();
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
            return rotaDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public List<RotaDTO> getComboRota(){
        return rotaDAO.getComboRota();
    }
}
