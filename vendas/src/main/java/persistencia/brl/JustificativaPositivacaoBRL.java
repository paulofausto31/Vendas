package persistencia.brl;

import android.content.Context;

import java.util.List;

import persistencia.dao.JustificativaPositivacaoDAO;
import persistencia.dto.JustificativaPositivacaoDTO;
import venda.util.Global;


public class JustificativaPositivacaoBRL {
	JustificativaPositivacaoDAO justificativaPositivacaoDAO;
	Context ctx;

    public JustificativaPositivacaoBRL(){}
    
    public JustificativaPositivacaoBRL(Context ctx)
    {
    	this.ctx = ctx;
        if (justificativaPositivacaoDAO == null)
        	justificativaPositivacaoDAO = new JustificativaPositivacaoDAO(ctx);
    }
    
    public void CloseConection(){
    	justificativaPositivacaoDAO.CloseConection();
    }

    public JustificativaPositivacaoDTO InstanciaJustPositivacao(String linha) 
    {
    	JustificativaPositivacaoDTO dto = new JustificativaPositivacaoDTO();
        //dto.setId(1);
        dto.setCodEmpresa(Global.codEmpresa);
        dto.setCodJustPos(Integer.parseInt(linha.substring(0, 2).trim()));
        dto.setDescricao(linha.substring(2, 18));
        return dto;
    }
    
    public boolean InsereJustPositivacao(JustificativaPositivacaoDTO jpsDto) 
    {
        try
        {
            return justificativaPositivacaoDAO.insert(jpsDto);
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
            return justificativaPositivacaoDAO.deleteAll();
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
            return justificativaPositivacaoDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public List<JustificativaPositivacaoDTO> getAll(){
    	return justificativaPositivacaoDAO.getAll();
    	
    }
    
    public JustificativaPositivacaoDTO getById(Integer id){
    	return justificativaPositivacaoDAO.getById(id);
    }
    
    public JustificativaPositivacaoDTO getByCodJustificativa(Integer codJustificativa){
    	return justificativaPositivacaoDAO.getByCodJustificativa(codJustificativa);
    }
    
    public List<String> getCombo(String campo, String item0){
    	return justificativaPositivacaoDAO.getCombo(campo, item0);
    }

}
