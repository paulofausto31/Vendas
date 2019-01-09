package persistencia.dao;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.LocalizacaoDTO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LocalizacaoDAO {
	private static String tableName = "localizacaoGPS";
	private SQLiteDatabase db;
	private static Context ctx;
	private static String[] columns = {"id", "codEmpresa", "codVendedor", "dataHora", "latitude", "longitude"};
	
	public LocalizacaoDAO(){}
	
	public LocalizacaoDAO(Context ctx){
		this.ctx = ctx;
		this.db = new db(ctx).getWritableDatabase();
	}
	
	public void CloseConection(){
		db.close();
	}
	
	public boolean insert(LocalizacaoDTO dto){
		
		ContentValues ctv = new ContentValues();
		ctv.put("id", dto.getId());
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codVendedor", dto.getCodVendedor());
		ctv.put("dataHora", dto.getDataHora());
		ctv.put("latitude", dto.getLatitude());
		ctv.put("longitude", dto.getLongitude());
		
		return (db.insert(tableName, null, ctv) > 0);
	}
	
	public boolean delete(LocalizacaoDTO dto){
		
		return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public boolean deleteAll(){

		return (db.delete(tableName,null , null) > 0);
	}

	public boolean update(LocalizacaoDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codVendedor", dto.getCodVendedor());
		ctv.put("dataHora", dto.getDataHora());
		ctv.put("latitude", dto.getLatitude());
		ctv.put("longitude", dto.getLongitude());
		
		return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public LocalizacaoDTO getById(Integer ID){

		Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		LocalizacaoDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new LocalizacaoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodVendedor(rs.getString(rs.getColumnIndex("codVendedor")));
			dto.setDataHora(rs.getString(rs.getColumnIndex("dataHora")));
			dto.setLatitude(rs.getString(rs.getColumnIndex("latitude")));
			dto.setLongitude(rs.getString(rs.getColumnIndex("longitude")));
		}
		
		return dto;
	}
	
	public List<LocalizacaoDTO> getAll(){

		Cursor rs = db.rawQuery("SELECT * FROM localizacaoGPS order by DataHora", null);
		
		List<LocalizacaoDTO> lista = new ArrayList<LocalizacaoDTO>();
		
		while(rs.moveToNext()){
			LocalizacaoDTO dto = new LocalizacaoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getString(rs.getColumnIndex("codVendedor")), rs.getString(rs.getColumnIndex("DataHora")), rs.getString(rs.getColumnIndex("latitude")), rs.getString(rs.getColumnIndex("longitude")));
			lista.add(dto);
		}
		
		return lista;
	}

}
