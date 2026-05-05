package persistencia.brl;

import android.content.Context;

import java.util.List;

import persistencia.dao.AvariaDAO;
import persistencia.dto.AvariaDTO;

public class AvariaBRL {
    AvariaDAO avariaDAO;
    Context ctx;

    public AvariaBRL(){}

    public AvariaBRL(Context ctx)
    {
        this.ctx = ctx;
        if (avariaDAO == null)
            avariaDAO = new AvariaDAO(ctx);
    }

    public void CloseConection(){
        avariaDAO.CloseConection();
    }

    public long InsereAvaria(AvariaDTO avaDTO)
    {
        try
        {
            return avariaDAO.insert(avaDTO);
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return -1;
        }
    }

    public boolean Update(AvariaDTO avaDTO)
    {
        try
        {
            return avariaDAO.update(avaDTO);
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
            return avariaDAO.deleteAll();
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
            return avariaDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public boolean delete(AvariaDTO dto){
        return avariaDAO.delete(dto);
    }

   // public List<AvariaDTO> getAll(){
   //     return avariaDAO.getAll();
   // }

    public AvariaDTO getById(Integer id){
        return avariaDAO.getById(id);
    }

    public List<AvariaDTO> getByCodCliente(Integer codCliente){
        return avariaDAO.getByCodCliente(codCliente);
    }

}
