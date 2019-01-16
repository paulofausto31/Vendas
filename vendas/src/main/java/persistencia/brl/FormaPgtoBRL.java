package persistencia.brl;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import persistencia.dao.FormaPgtoDAO;
import persistencia.dto.FormaPgtoDTO;
import venda.util.Global;

public class FormaPgtoBRL {
    FormaPgtoDAO formaPgtoDAO;
    Context ctx;

    public FormaPgtoBRL(){}
    
    public FormaPgtoBRL(Context ctx)
    {
    	this.ctx = ctx;
        if (formaPgtoDAO == null)
            formaPgtoDAO = new FormaPgtoDAO(ctx);
    }

    public void CloseConection(){
    	formaPgtoDAO.CloseConection();
    }
    
    public FormaPgtoDTO InstanciaFormaPgto(String linha) 
    {
    	FormaPgtoDTO dto = new FormaPgtoDTO();
        //dto.setId(1);
        dto.setCodEmpresa(Global.codEmpresa);
        dto.setCodFPgto(linha.substring(0, 4).trim());
        dto.setDescricao(linha.substring(4, 24));
        dto.setMulta(Double.parseDouble(linha.substring(24, 27).replace(',', '.')));
        dto.setJuros(Double.parseDouble(linha.substring(27, 33).replace(',', '.')));
        return dto;
    }
    
    public boolean InsereFormaPgto(FormaPgtoDTO pgtDto)
    {
        try
        {
            return formaPgtoDAO.insert(pgtDto);
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
            return formaPgtoDAO.deleteAll();
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
            return formaPgtoDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }

    public List<String> getCombo(String campo, String item0){
    	return formaPgtoDAO.getCombo(campo, item0);
    }

    private Double getMulta(String codPgto){
    	return formaPgtoDAO.getMulta(codPgto);
    }

    private Double getJuros(String codPgto){
    	return formaPgtoDAO.getJuros(codPgto);
    }

    public String getMultaCalculado(String codFpto, Double valor){
        Double multa = (valor * getMulta(codFpto));
        return FormataValorNumerico(multa);
    }

    public String getJurosCalculado(String codFpto, Double valor, String dataVenc) throws ParseException {
        Date dateAtual = new Date();
        Date dateVenc = venda.util.Util.StringToDate(dataVenc);
        Double juros = (valor * (venda.util.Util.dataDiff(dateVenc, dateAtual) * getJuros(codFpto)));
        return FormataValorNumerico(juros);
    }

    private String FormataValorNumerico(Double valor){
        DecimalFormat formatador = new DecimalFormat("##,##00.00");
        return formatador.format(valor).replace(',', '.');
    }

    public String getValorAtualizado(String codFpto, Double valor, String dataVenc) throws ParseException{
        Date dateAtual = new Date();
        Date dateVenc = venda.util.Util.StringToDate(dataVenc);
		DecimalFormat formatador = new DecimalFormat("##,##00.00");  
	    String retornoFormatado = valor.toString();  
    	if (dateAtual.after(dateVenc)){
    		Double multa = (valor * getMulta(codFpto));
    		Double juros = (valor * (venda.util.Util.dataDiff(dateVenc, dateAtual) * getJuros(codFpto)));
    		retornoFormatado  = formatador.format(valor + multa + juros).replace(',', '.');
    	}
    	
    	return retornoFormatado;
    }
}
