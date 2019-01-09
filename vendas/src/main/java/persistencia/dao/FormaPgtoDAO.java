package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.FormaPgtoDTO;
import venda.util.Global;

public class FormaPgtoDAO {
	private static String tableName = "formaPgto";
	private SQLiteDatabase db;
	private static Context ctx;
	private static String[] columns = {"id", "codEmpresa", "codFPgto", "descricao", "multa", "juros"};
	
	public FormaPgtoDAO(){}
	
	public FormaPgtoDAO(Context ctx){
		this.ctx = ctx;
		this.db = new db(ctx).getWritableDatabase();
	}
	
	public void CloseConection(){
		db.close();
	}

	public boolean insert(FormaPgtoDTO dto){
		
		ContentValues ctv = new ContentValues();
		ctv.put("id", dto.getId());
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codFPgto", dto.getCodFPgto());
		ctv.put("descricao", dto.getDescricao());
		ctv.put("multa", dto.getMulta());
		ctv.put("juros", dto.getJuros());
		
		return (db.insert(tableName, null, ctv) > 0);
	}
	
	public boolean delete(FormaPgtoDTO dto){
		
		return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
	}

	public boolean deleteByEmpresa(){

		return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
	}

	public boolean deleteAll(){

		return (db.delete(tableName,null , null) > 0);
	}

	public boolean update(FormaPgtoDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codFPgto", dto.getCodFPgto());
		ctv.put("descricao", dto.getDescricao());
		ctv.put("multa", dto.getMulta());
		ctv.put("juros", dto.getJuros());
		
		return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public FormaPgtoDTO getById(Integer ID){

		Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		FormaPgtoDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new FormaPgtoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodFPgto(rs.getString(rs.getColumnIndex("codFPgto")));
			dto.setDescricao(rs.getString(rs.getColumnIndex("descricao")));
			dto.setMulta(rs.getDouble(rs.getColumnIndex("multa")));
			dto.setJuros(rs.getDouble(rs.getColumnIndex("juros")));
		}
		
		return dto;
	}
	
	public Double getMulta(String codFPgto){

		Cursor rs = db.query(tableName, columns, "codFPgto=? and codEmpresa=?", new String[]{codFPgto, Global.codEmpresa}, null, null, null);
		
		Double multa = null;
		
		if(rs.moveToFirst()){
			multa = rs.getDouble(rs.getColumnIndex("multa")) / 100;
		}
		
		return multa;
	}

	public Double getJuros(String codFPgto){

		Cursor rs = db.query(tableName, columns, "codFPgto=? and codEmpresa=?", new String[]{codFPgto, Global.codEmpresa}, null, null, null);
		
		Double juros = null;
		
		if(rs.moveToFirst()){
			juros = ((rs.getDouble(rs.getColumnIndex("juros")) / 100) / 30);
		}
		
		return juros;
	}

	public List<FormaPgtoDTO> getAll(){

		Cursor rs = db.rawQuery("SELECT * FROM formaPgto WHERE codEmpresa = ".concat(Global.codEmpresa), null);
		
		List<FormaPgtoDTO> lista = new ArrayList<FormaPgtoDTO>();
		
		while(rs.moveToNext()){
			FormaPgtoDTO dto = new FormaPgtoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getString(rs.getColumnIndex("codFPgto")), rs.getString(rs.getColumnIndex("descricao")), rs.getDouble(rs.getColumnIndex("multa")), rs.getDouble(rs.getColumnIndex("juros")));
			lista.add(dto);
		}
		
		return lista;
	}
	
	public List<String> getCombo(String campo, String item0){

		Cursor rs = db.rawQuery("SELECT * FROM formaPgto WHERE codEmpresa = ".concat(Global.codEmpresa), null);
		
		List<String> lista = new ArrayList<String>();
		lista.add(item0);
		while(rs.moveToNext()){
			lista.add(rs.getString(rs.getColumnIndex(campo)).toString());
		}
		
		return lista;
	}
}
