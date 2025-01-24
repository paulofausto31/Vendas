package persistencia.brl;

import android.content.Context;

import java.util.List;

import persistencia.dao.RotaClienteDAO;
import persistencia.dto.RotaClienteDTO;
import venda.util.Global;

public class RotaClienteBRL {

    RotaClienteDAO rotaClienteDAO;
    Context ctx;

    public RotaClienteBRL(){}

    public RotaClienteBRL(Context ctx)
    {
        this.ctx = ctx;
        if (rotaClienteDAO == null)
            rotaClienteDAO = new RotaClienteDAO(ctx);
    }

    public void CloseConection(){ rotaClienteDAO.CloseConection();
    }

    public RotaClienteDTO InstanciaRotaCliente(String linha)
    {
        RotaClienteDTO dto = new RotaClienteDTO();
        //dto.setId(1);
        dto.setCodEmpresa(Global.codEmpresa);
        dto.setCodRota(Integer.parseInt(linha.substring(0, 4).trim()));
        dto.setCodCliente(Integer.parseInt(linha.substring(4, 10)));
        dto.setSeqVisita(Integer.parseInt(linha.substring(10, 14)));
        return dto;
    }

    public boolean InsereRotaCliente(RotaClienteDTO rotaClienteDTO)
    {
        try
        {
            return rotaClienteDAO.insert(rotaClienteDTO);
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
            return rotaClienteDAO.deleteAll();
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
            return rotaClienteDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public List<String> getComboRotaCliente(){
        return rotaClienteDAO.getComboRotaCliente();
    }
}
