package persistencia.brl;

import android.content.Context;
import persistencia.dao.*;
import persistencia.dto.*;
import venda.util.Global;

public class ConfiguracaoBRL {
    ConfiguracaoDAO configuracaoDAO;
    Context ctx;

    public ConfiguracaoBRL(){}
    
    public ConfiguracaoBRL(Context ctx)
    {
    	this.ctx = ctx;
        if (configuracaoDAO == null)
            configuracaoDAO = new ConfiguracaoDAO(ctx);
    }
    
    public void CloseConection(){
    	configuracaoDAO.CloseConection();
    }

    public ConfiguracaoDTO InstanciaConfiguracao(String linha) 
    {
    	ConfiguracaoDTO dto = new ConfiguracaoDTO();
        //dto.setId(1);
    	dto.setDescontoMaximo(Double.parseDouble(linha.substring(0, 6).replace(',', '.')));
        dto.setDescontoAcrescimo(linha.substring(6, 7));
        dto.setCodEmpresa(Global.codEmpresa);
        dto.setCriticaEstoque(linha.substring(11, 12));
        dto.setPrazoMaximo(Integer.parseInt(linha.substring(12, 14)));
        dto.setParcelaMaxima(Integer.parseInt(linha.substring(14, 16)));
        dto.setDataCarga(linha.substring(16, 26));
        dto.setHoraCarga(linha.substring(26, 31));
        dto.setMensagem(linha.substring(31, 531));
        return dto;
    }
    
    public boolean InsereConfiguracao(ConfiguracaoDTO pgtDto) 
    {
        try
        {
            return configuracaoDAO.insert(pgtDto);
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
            return configuracaoDAO.deleteAll();
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
            return configuracaoDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public ConfiguracaoDTO getAll(){
    	return configuracaoDAO.getAll();
    }

    public String getMensagem(){
    	return configuracaoDAO.getMensagem();
    }

}
