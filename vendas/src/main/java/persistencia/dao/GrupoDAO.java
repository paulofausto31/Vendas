package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.GrupoDTO;
import venda.util.Global;

public class GrupoDAO {
	private static String tableName = "grupo";
	private SQLiteDatabase db;
	private static Context ctx;
	private static String[] columns = {"id", "codEmpresa", "codGrupo", "descricao"};
	
	public GrupoDAO(){}
	
	public GrupoDAO(Context ctx){
		this.ctx = ctx;
		this.db = new db(ctx).getWritableDatabase();
	}
	
	public void CloseConection(){
		db.close();
	}
	
	public boolean insert(GrupoDTO dto){
		
		ContentValues ctv = new ContentValues();
		ctv.put("id", dto.getId());
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codGrupo", dto.getCodGrupo());
		ctv.put("descricao", dto.getDescricao());
		
		return (db.insert(tableName, null, ctv) > 0);
	}
	
	public boolean delete(GrupoDTO dto){
		
		return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
	}

	public boolean deleteByEmpresa(){

		return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
	}

	public boolean deleteAll(){

		return (db.delete(tableName,null , null) > 0);
	}

	public boolean update(GrupoDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codGrupo", dto.getCodGrupo());
		ctv.put("descricao", dto.getDescricao());
		
		return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public GrupoDTO getById(Integer ID){

		Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		GrupoDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new GrupoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodGrupo(rs.getString(rs.getColumnIndex("codGrupo")));
			dto.setDescricao(rs.getString(rs.getColumnIndex("descricao")));
		}
		
		return dto;
	}
	
	public List<GrupoDTO> getAll(){

		Cursor rs = db.rawQuery("SELECT * FROM Grupo WHERE codEmpresa = ".concat(Global.codEmpresa).concat(" order by Descricao"), null);
		
		List<GrupoDTO> lista = new ArrayList<GrupoDTO>();
		
		while(rs.moveToNext()){
			GrupoDTO dto = new GrupoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getString(rs.getColumnIndex("codGrupo")), rs.getString(rs.getColumnIndex("descricao")));
			lista.add(dto);
		}
		
		return lista;
	}
}


