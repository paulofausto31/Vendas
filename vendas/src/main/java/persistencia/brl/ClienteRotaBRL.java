package persistencia.brl;

import android.content.Context;

import java.util.List;

import persistencia.dao.ClienteRotaDAO;
import persistencia.dto.ClienteRotaDTO;
import persistencia.dto.FormaPgtoDTO;
import persistencia.dto.RotaDTO;
import venda.util.Global;


public class ClienteRotaBRL {
    ClienteRotaDAO clienteRotaDAO;
    Context ctx;

    public ClienteRotaBRL(){}

    public ClienteRotaBRL(Context ctx)
    {
        this.ctx = ctx;
        if (clienteRotaDAO == null)
            clienteRotaDAO = new ClienteRotaDAO(ctx);
    }

    public ClienteRotaDTO InstanciaClienteRota(String linha)
    {
        ClienteRotaDTO dto = new ClienteRotaDTO();
        //dto.setId(1);
        dto.setCodEmpresa(Global.codEmpresa);
        dto.setCodRota(Integer.parseInt(linha.substring(0, 4).trim()));
        dto.setCodCliente(Integer.parseInt(linha.substring(4, 10)));
        dto.setSeqVisita(Integer.parseInt(linha.substring(10, 14)));
        return dto;
    }

    public void CloseConection(){
        clienteRotaDAO.CloseConection();
    }

    public boolean InsereClienteRota(ClienteRotaDTO cliDto)
    {
        try
        {
            return clienteRotaDAO.insert(cliDto);
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public boolean delete(ClienteRotaDTO dto)
    {
        try
        {
            return clienteRotaDAO.delete(dto);
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
            return clienteRotaDAO.deleteAll();
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
            return clienteRotaDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public boolean Update(ClienteRotaDTO cliDTO)
    {
        try
        {
            return clienteRotaDAO.update(cliDTO);
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }
}
