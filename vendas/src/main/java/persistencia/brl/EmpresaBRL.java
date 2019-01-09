package persistencia.brl;

import android.content.Context;

import java.util.List;

import persistencia.dao.EmpresaDAO;
import persistencia.dto.EmpresaDTO;

/**
 * Created by Paulo on 25/04/2018.
 */

public class EmpresaBRL {

    EmpresaDAO empresaDAO;
    Context ctx;

    public EmpresaBRL(){}

    public EmpresaBRL(Context ctx)
    {
        this.ctx = ctx;
        if (empresaDAO == null)
            empresaDAO = new EmpresaDAO(ctx);
    }

    public void CloseConection(){
        empresaDAO.CloseConection();
    }

    public EmpresaDTO InstanciaEmpresa(String linha)
    {
        EmpresaDTO dto = new EmpresaDTO();
        //dto.setId(1);
        dto.setCodEmpresa("1".concat(linha.substring(4, 18)));
        dto.setCnpj(linha.substring(4, 18));
        dto.setRazaoSocial(linha.substring(18, 118));
        dto.setFantasia(linha.substring(118, 218));
        return dto;
    }

    public boolean InsereEmpresa(EmpresaDTO empDto)
    {
        try
        {
            return empresaDAO.insert(empDto);
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
            return empresaDAO.deleteAll();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public boolean DeleteByEmpresa(String codEmpresa)
    {
        try
        {
            return empresaDAO.deleteByEmpresa(codEmpresa);
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public List<EmpresaDTO> getAll(){
        return empresaDAO.getAll();
    }

    public int getTotalEmpresas(){
        return empresaDAO.getTotalEmpresas();
    }

    public List<String> getCombo(String campo, String item0){
        return empresaDAO.getCombo(campo, item0);
    }

    public List<String> getCombo(String campo1, String campo2, String item0){
        return empresaDAO.getCombo(campo1, campo2, item0);
    }
}
