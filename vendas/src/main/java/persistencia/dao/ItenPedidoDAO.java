package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.ItenPedidoDTO;
import venda.util.Global;

public class ItenPedidoDAO {

	private static String tableName = "ItensPedido";
	private SQLiteDatabase db;
	private static Context ctx;
	private static String[] columns = {"id", "codEmpresa", "codPedido", "codProduto", "quantidade", "preco", "unidade", "DA","DAValor"};
	
	public ItenPedidoDAO(){}
	
	public ItenPedidoDAO(Context ctx){
		this.ctx = ctx;
		this.db = new db(ctx).getWritableDatabase();
	}
	
	public void CloseConection(){
		db.close();
	}
	
	public boolean insert(ItenPedidoDTO dto){
		
		ContentValues ctv = new ContentValues();
		//ctv.put("id", dto.getId());
		ctv.put("codEmpresa", Global.codEmpresa);
		ctv.put("codPedido", dto.getCodPedido());
		ctv.put("codProduto", dto.getCodProduto());
		ctv.put("quantidade", dto.getQuantidade());
		ctv.put("preco", dto.getPreco());
		ctv.put("unidade", dto.getUnidade());
		ctv.put("DA", dto.getDA());
		ctv.put("DAValor", dto.getDAValor());
		
		return (db.insert(tableName, null, ctv) > 0);
	}
	
	public boolean delete(ItenPedidoDTO dto){
		
		return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
	}

	public boolean deleteByEmpresa(){

		return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
	}

	public boolean deleteByCodPedido(Integer codPedido){
		
		return (db.delete(tableName, "codPedido=? and codEmpresa=?", new String[]{codPedido.toString(), Global.codEmpresa}) > 0);
	}
	
	public boolean deleteAll(){

		return (db.delete(tableName,null , null) > 0);
	}

	public boolean update(ItenPedidoDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("codEmpresa", Global.codEmpresa);
		ctv.put("codPedido", dto.getCodPedido());
		ctv.put("codProduto", dto.getCodProduto());
		ctv.put("quantidade", dto.getQuantidade());
		ctv.put("preco", dto.getPreco());
		ctv.put("unidade", dto.getUnidade());
		ctv.put("DA", dto.getDA());
		ctv.put("DAValor", dto.getDAValor());
		
		return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public ItenPedidoDTO getById(Integer ID){

		Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		ItenPedidoDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new ItenPedidoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodPedido(rs.getInt(rs.getColumnIndex("codPedido")));
			dto.setCodProduto(rs.getLong(rs.getColumnIndex("codProduto")));
			dto.setQuantidade(rs.getDouble(rs.getColumnIndex("quantidade")));
			dto.setPreco(rs.getDouble(rs.getColumnIndex("preco")));
			dto.setUnidade(rs.getInt(rs.getColumnIndex("unidade")));
			dto.setDA(rs.getString(rs.getColumnIndex("DA")));
			dto.setDAValor(rs.getDouble(rs.getColumnIndex("DAValor")));
		}
		
		return dto;
	}
	
	public List<ItenPedidoDTO> getByCodPedido(Integer codPedido){

		Cursor rs = db.query(tableName, columns, "codPedido=? and codEmpresa=?", new String[]{codPedido.toString(), Global.codEmpresa}, null, null, null);
		
		ItenPedidoDTO dto = null;
		List<ItenPedidoDTO> lista = new ArrayList<ItenPedidoDTO>();
		
		while(rs.moveToNext()){
			dto = new ItenPedidoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodPedido(rs.getInt(rs.getColumnIndex("codPedido")));
			dto.setCodProduto(rs.getLong(rs.getColumnIndex("codProduto")));
			dto.setQuantidade(rs.getDouble(rs.getColumnIndex("quantidade")));
			dto.setPreco(rs.getDouble(rs.getColumnIndex("preco")));
			dto.setUnidade(rs.getInt(rs.getColumnIndex("unidade")));
			dto.setDA(rs.getString(rs.getColumnIndex("DA")));
			dto.setDAValor(rs.getDouble(rs.getColumnIndex("DAValor")));
			lista.add(dto);
		}
		
		return lista;
	}
	
	public ItenPedidoDTO getByCodProduto(Integer codPedido, Integer codProduto){

		Cursor rs = db.query(tableName, columns, "codPedido=? and codProduto=? and codEmpresa=?", new String[]{codPedido.toString(), codProduto.toString(), Global.codEmpresa}, null, null, null);
		
		ItenPedidoDTO dto = null;
		
		if(rs.getCount() > 0){
			rs.moveToFirst();
			dto = new ItenPedidoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodPedido(rs.getInt(rs.getColumnIndex("codPedido")));
			dto.setCodProduto(rs.getLong(rs.getColumnIndex("codProduto")));
			dto.setQuantidade(rs.getDouble(rs.getColumnIndex("quantidade")));
			dto.setPreco(rs.getDouble(rs.getColumnIndex("preco")));
			dto.setUnidade(rs.getInt(rs.getColumnIndex("unidade")));
			dto.setDA(rs.getString(rs.getColumnIndex("DA")));
			dto.setDAValor(rs.getDouble(rs.getColumnIndex("DAValor")));
		}
		
		return dto;
	}

	public boolean produtoExistente(Integer codPedido, Integer codProduto){
		boolean retorno;

		Cursor rs = db.query(tableName, columns, "codPedido=? and codProduto=? and codEmpresa=?", new String[]{codPedido.toString(), codProduto.toString(), Global.codEmpresa}, null, null, null);
		
		if (rs.getCount() > 0)
			retorno = true;
		else
			retorno = false;
		
		return retorno;
	}

	public Double getTotalPedido(Integer codPedido){

		Cursor rs = db.query(tableName, columns, "codPedido=? and codEmpresa=?", new String[]{codPedido.toString(), Global.codEmpresa}, null, null, null);
		
		Double totalPedido = 0.0;
		
		while(rs.moveToNext()){
			totalPedido = totalPedido + (rs.getDouble(rs.getColumnIndex("quantidade")) * rs.getDouble(rs.getColumnIndex("preco")));
		}
		
		return totalPedido;
	}
	
	public List<ItenPedidoDTO> getAllAbertos(){

		Cursor rs = db.rawQuery("SELECT * FROM pedidos p, ItensPedido i where p.id = i.codPedido and p.baixado = 0 and p.fechado = 1 and p.codEmpresa = ".concat(Global.codEmpresa), null);
		
		List<ItenPedidoDTO> lista = new ArrayList<ItenPedidoDTO>();
		
		while(rs.moveToNext()){
			ItenPedidoDTO dto = new ItenPedidoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codPedido")), rs.getLong(rs.getColumnIndex("codProduto")),
					rs.getDouble(rs.getColumnIndex("quantidade")), rs.getDouble(rs.getColumnIndex("preco")), rs.getInt(rs.getColumnIndex("unidade")), rs.getString(rs.getColumnIndex("DA")), rs.getDouble(rs.getColumnIndex("DAValor")));
			lista.add(dto);
		}
		
		return lista;
	}
	
	public List<ItenPedidoDTO> getAll(){

		Cursor rs = db.rawQuery("SELECT * FROM ItensPedido WHERE codEmpresa = ".concat(Global.codEmpresa), null);
		
		List<ItenPedidoDTO> lista = new ArrayList<ItenPedidoDTO>();
		
		while(rs.moveToNext()){
			ItenPedidoDTO dto = new ItenPedidoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codPedido")), rs.getLong(rs.getColumnIndex("codProduto")),
					rs.getDouble(rs.getColumnIndex("quantidade")), rs.getDouble(rs.getColumnIndex("preco")), rs.getInt(rs.getColumnIndex("unidade")), rs.getString(rs.getColumnIndex("DA")), rs.getDouble(rs.getColumnIndex("DAValor")));
			lista.add(dto);
		}
		
		return lista;
	}

	public List<Long> getAllAberto(){

		Cursor rs = db.rawQuery("SELECT i.codProduto FROM ItensPedido i, Pedidos p, Produto o where p.id = i.codPedido and i.codProduto = o.codProduto and p.baixado = 0 and p.codEmpresa = ".concat(Global.codEmpresa).concat(" group by i.codProduto"), null);
		
		List<Long> lista = new ArrayList<Long>();
		
		while(rs.moveToNext()){
			lista.add(rs.getLong(rs.getColumnIndex("codProduto")));
		}
		
		return lista;
	}

	public Double getSumQtdAberto(Long codProduto){

		Cursor rs = db.rawQuery("SELECT sum(i.quantidade) as total FROM ItensPedido i, Pedidos p where p.id = i.codPedido and p.baixado = 0 and i.codProduto = ".concat(codProduto.toString()).concat(" and p.codEmpresa = ").concat(Global.codEmpresa), null);
		
		Double total = 0.0;

		if(rs.moveToFirst()){
			total = rs.getDouble(rs.getColumnIndex("total"));
		}
		
		return total;
	}

	public Double getSumTotalAberto(){

		Cursor rs = db.rawQuery("SELECT i.* FROM ItensPedido i, Pedidos p, Produto o where p.id = i.codPedido and i.codProduto = o.codProduto and p.baixado = 0 and p.codEmpresa = ".concat(Global.codEmpresa), null);
		
		Double total = 0.0;

		while(rs.moveToNext()){
			total = total + (rs.getDouble(rs.getColumnIndex("quantidade")) * rs.getDouble(rs.getColumnIndex("preco")));
		}
		
		return total;
	}

	public boolean ExisteDA(Integer codPedido){

		Cursor rs = db.rawQuery("SELECT sum(i.DAValor) as total FROM ItensPedido i where i.codPedido = ".concat(codPedido.toString()).concat(" and i.codEmpresa = ").concat(Global.codEmpresa), null);

		Double totalDA = 0.0;

		if(rs.moveToFirst()){
			totalDA = rs.getDouble(rs.getColumnIndex("total"));
		}
		
		if(totalDA > 0)
			return true;
		else
			return false;
	}
}

