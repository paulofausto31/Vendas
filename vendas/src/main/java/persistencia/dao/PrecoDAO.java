package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.PrecoDTO;
import venda.util.Global;

public class PrecoDAO {

	private static String tableName = "preco";
	private SQLiteDatabase db;
	private static Context ctx;
	private static String[] columns = {"id", "codEmpresa", "codPreco", "codProduto", "preco"};
	
	public PrecoDAO(){}
	
	public PrecoDAO(Context ctx){
		this.ctx = ctx;
		this.db = new db(ctx).getWritableDatabase();
	}
	
	public void CloseConection(){
		db.close();
	}
	
	public boolean insert(PrecoDTO dto){
		
		ContentValues ctv = new ContentValues();
		ctv.put("id", dto.getId());
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codProduto", dto.getCodProduto());
		ctv.put("preco", dto.getPreco());
		
		return (db.insert(tableName, null, ctv) > 0);
	}
	
	public boolean delete(PrecoDTO dto){
		
		return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
	}

	public boolean deleteByEmpresa(){

		return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
	}

	public boolean deleteAll(){

		return (db.delete(tableName,null , null) > 0);
	}

	public boolean update(PrecoDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codProduto", dto.getCodProduto());
		ctv.put("preco", dto.getPreco());
		
		return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public PrecoDTO getById(Integer ID){

		Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		PrecoDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new PrecoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodProduto(rs.getLong(rs.getColumnIndex("codProduto")));
			dto.setPreco(rs.getDouble(rs.getColumnIndex("preco")));
		}
		
		return dto;
	}
	
	public List<PrecoDTO> getByProduto(Long codProduto){

		Cursor rs = db.rawQuery("SELECT * FROM preco where codProduto = ".concat(codProduto.toString()).concat(" and codEmpresa = ").concat(Global.codEmpresa), null);
		
		List<PrecoDTO> lista = new ArrayList<PrecoDTO>();
		
		if(rs.moveToFirst()){
			PrecoDTO dto = new PrecoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getLong(rs.getColumnIndex("codProduto")), rs.getDouble(rs.getColumnIndex("preco")));
			lista.add(dto);
		}
		
		return lista;
	}

	public List<PrecoDTO> getAll(){

		Cursor rs = db.rawQuery("SELECT * FROM preco WHERE codEmpresa = ".concat(Global.codEmpresa), null);
		
		List<PrecoDTO> lista = new ArrayList<PrecoDTO>();
		
		while(rs.moveToNext()){
			PrecoDTO dto = new PrecoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getLong(rs.getColumnIndex("codProduto")), rs.getDouble(rs.getColumnIndex("preco")));
			lista.add(dto);
		}
		
		return lista;
	}

	public List<String> getCombo(String campo, String item0, Integer codProduto, Double desconto){

		Cursor rs = db.rawQuery("SELECT * FROM preco where codProduto = ".concat(codProduto.toString()).concat(" and codEmpresa = ").concat(Global.codEmpresa), null);
		
		List<String> lista = new ArrayList<String>();
		lista.add(item0);
		while(rs.moveToNext()){
			Double preco = Double.parseDouble(rs.getString(rs.getColumnIndex(campo)).toString().replace(',', '.')); 
			if (desconto > 0){
				preco = preco - ((preco * desconto)/100);
			}
			lista.add(preco.toString());
		}
		
		return lista;
	}

	public List<String> getCombo(String campo, Long codProduto, Double DA, String paramDA){

		Cursor rs = db.rawQuery("SELECT * FROM preco where codProduto = ".concat(codProduto.toString()).concat(" and codEmpresa = ").concat(Global.codEmpresa), null);
		
		List<String> lista = new ArrayList<String>();
		while(rs.moveToNext()){
			Double preco = Double.parseDouble(rs.getString(rs.getColumnIndex(campo)).toString().replace(',', '.')); 
			if (DA > 0){
				if (paramDA == "D")
					preco = preco - ((preco * DA)/100);
				else
					preco = preco + ((preco * DA)/100);
			}
			DecimalFormat formatador = new DecimalFormat("##,##00.00");  
		    String precoFormatado = formatador.format(preco);  
		    precoFormatado = precoFormatado.replace(',', '.'); 		
			lista.add(precoFormatado);
		}
		
		return lista;
	}
}
