package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.JustificativaPositivacaoDTO;
import venda.util.Global;

public class JustificativaPositivacaoDAO {
	private static String tableName = "JustificativaPositivacao";
	private SQLiteDatabase db;
	private static Context ctx;
	private static String[] columns = {"id", "codEmpresa", "codJustPos", "descricao"};
	
	public JustificativaPositivacaoDAO(){}
	
	public JustificativaPositivacaoDAO(Context ctx){
		this.ctx = ctx;
		this.db = new db(ctx).getWritableDatabase();
	}
	
	public void CloseConection(){
		db.close();
	}
	
	public boolean insert(JustificativaPositivacaoDTO dto){
		
		ContentValues ctv = new ContentValues();
		ctv.put("id", dto.getId());
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codJustPos", dto.getCodJustPos());
		ctv.put("descricao", dto.getDescricao());
		
		return (db.insert(tableName, null, ctv) > 0);
	}
	
	public boolean delete(JustificativaPositivacaoDTO dto){
		
		return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
	}

	public boolean deleteByEmpresa(){

		return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
	}

	public boolean deleteAll(){

		return (db.delete(tableName,null , null) > 0);
	}

	public boolean update(JustificativaPositivacaoDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codJustPos", dto.getCodJustPos());
		ctv.put("descricao", dto.getDescricao());
		
		return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public JustificativaPositivacaoDTO getById(Integer ID){

		Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		JustificativaPositivacaoDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new JustificativaPositivacaoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodJustPos(rs.getInt(rs.getColumnIndex("codJustPos")));
			dto.setDescricao(rs.getString(rs.getColumnIndex("descricao")));
		}
		
		return dto;
	}
	
	public JustificativaPositivacaoDTO getByCodJustificativa(Integer codJustificativa){

		Cursor rs = db.query(tableName, columns, "codJustPos=? and codEmpresa=?", new String[]{codJustificativa.toString(), Global.codEmpresa}, null, null, null);
		
		JustificativaPositivacaoDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new JustificativaPositivacaoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodJustPos(rs.getInt(rs.getColumnIndex("codJustPos")));
			dto.setDescricao(rs.getString(rs.getColumnIndex("descricao")));
		}
		
		return dto;
	}
	
	public List<JustificativaPositivacaoDTO> getAll(){

		Cursor rs = db.rawQuery("SELECT * FROM JustificativaPositivacao WHERE codEmpresa = ".concat(Global.codEmpresa), null);
		
		List<JustificativaPositivacaoDTO> lista = new ArrayList<JustificativaPositivacaoDTO>();
		
		while(rs.moveToNext()){
			JustificativaPositivacaoDTO dto = new JustificativaPositivacaoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codJustPos")), rs.getString(rs.getColumnIndex("descricao")));
			lista.add(dto);
		}
		
		return lista;
	}

	public List<String> getCombo(String campo, String item0){

		Cursor rs = db.rawQuery("SELECT * FROM JustificativaPositivacao WHERE codEmpresa = ".concat(Global.codEmpresa), null);
		
		List<String> lista = new ArrayList<String>();
		lista.add(item0);
		while(rs.moveToNext()){
			lista.add(rs.getString(rs.getColumnIndex(campo)).toString());
		}
		
		return lista;
	}
}


