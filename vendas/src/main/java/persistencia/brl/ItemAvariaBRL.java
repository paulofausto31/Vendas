package persistencia.brl;

import android.content.Context;

import java.util.List;

import persistencia.dao.ItemAvariaDAO;
import persistencia.dto.ItemAvariaDTO;


public class ItemAvariaBRL {
    ItemAvariaDAO itemAvariaDAO;
    Context ctx;

    public ItemAvariaBRL(){}

    public ItemAvariaBRL(Context ctx)
    {
        this.ctx = ctx;
        if (itemAvariaDAO == null)
            itemAvariaDAO = new ItemAvariaDAO(ctx);
    }

    public void CloseConetion(){
        itemAvariaDAO.CloseConection();
    }

    public long InsereItemAvaria(ItemAvariaDTO itaDTO)
    {
        try
        {
            return itemAvariaDAO.insert(itaDTO);
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return -1;
        }
    }

    public boolean DeleteAll()
    {
        try
        {
            return itemAvariaDAO.deleteAll();
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
            return itemAvariaDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public Boolean Update(ItemAvariaDTO itaDTO)
    {
        try
        {
            return itemAvariaDAO.update(itaDTO);
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public boolean delete(ItemAvariaDTO dto){
        return itemAvariaDAO.delete(dto);
    }

    public boolean deleteByCodPedido(Integer codAvaria){
        return itemAvariaDAO.deleteByCodAvaria(codAvaria);
    }

    public List<ItemAvariaDTO> getAll(){
        return itemAvariaDAO.getAll();
    }

    public ItemAvariaDTO getById(Integer id){
        return itemAvariaDAO.getById(id);
    }

    public List<ItemAvariaDTO> getByCodAvaria(Integer codAvaria){
        return itemAvariaDAO.getByCodAvaria(codAvaria);
    }

    public Double getTotalAvaria(Integer codAvaria){
        return itemAvariaDAO.getTotalAvaria(codAvaria);
    }

    public ItemAvariaDTO getByCodProduto(Integer codAvaria, Integer codProduto){
        return itemAvariaDAO.getByCodProduto(codAvaria, codProduto);
    }
}
