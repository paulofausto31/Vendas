package persistencia.brl;

import android.content.Context;

import java.util.List;

import persistencia.dao.ProdutoDAO;
import persistencia.dto.ProdutoDTO;
import venda.util.Global;

public class ProdutoBRL {

    ProdutoDAO produtoDAO;
    Context ctx;

    public ProdutoBRL(){}
    
    public ProdutoBRL(Context ctx)
    {
    	this.ctx = ctx;
        if (produtoDAO == null)
            produtoDAO = new ProdutoDAO(ctx);
    }

    public void CloseConection(){
    	produtoDAO.CloseConection();
    }
    
    public ProdutoDTO InstanciaProduto(String linha) 
    {
    	ProdutoDTO dto = new ProdutoDTO();
        //dto.setId(1);
        dto.setCodEmpresa(Global.codEmpresa);
        dto.setCodProduto(Long.parseLong(linha.substring(0, 10)));
        dto.setDescricao(linha.substring(10, 54));
        dto.setUnidade(linha.substring(54, 60));
        dto.setEstoque(Double.parseDouble(linha.substring(60, 70).replace(',', '.')));
        dto.setGrupo(linha.substring(70, 73));
        dto.setFornecedor(Integer.parseInt(linha.substring(73, 77)));
        dto.setUnidade2(Integer.parseInt(linha.substring(77, 83)));
        return dto;
    }
    
    public List<ProdutoDTO> getAllOrdenado(){
		return produtoDAO.getAllOrdenado();	    	
    }
    
    public List<ProdutoDTO> getByFornecedor(Integer codFornecedor){
		return produtoDAO.getByFornecedor(codFornecedor);	    	
    }
    
    public Double getSaldoEstoque(String codProduto){
		return produtoDAO.getSaldoEstoque(codProduto);	    	
    }

    public List<ProdutoDTO> getByDescricao(String descricao){
		return produtoDAO.getByDescricao(descricao);	    	
    }

    public List<ProdutoDTO> getByGrupo(String codGrupo){
		return produtoDAO.getByGrupo(codGrupo);	    	
    }
    
    public ProdutoDTO getById(Integer id){
    	return produtoDAO.getById(id);
    }
    
    public ProdutoDTO getByCodProduto(Long codProduto){
    	return produtoDAO.getByCodProduto(codProduto);
    }

    public boolean InsereProduto(ProdutoDTO proDto) 
    {
        try
        {
            return produtoDAO.insert(proDto);
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
            return produtoDAO.deleteAll();
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
            return produtoDAO.deleteByEmpresa();
        }
        catch (Exception e)
        {
            venda.util.mensagem.trace(ctx, e.toString());
            return false;
        }
    }
}

