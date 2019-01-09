package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import persistencia.db.db;
import persistencia.dto.VendedorDTO;
import venda.util.Global;

public class VendedorDAO {

	private static String tableName = "vendedor";
	private SQLiteDatabase db;
	private static Context ctx;
	private static String[] columns = {"id", "codEmpresa", "codigo", "nome"};
	
	public VendedorDAO(){}
	
	public VendedorDAO(Context ctx){
		this.ctx = ctx;
		this.db = new db(ctx).getWritableDatabase();
	}
	
	public void CloseConection(){
		db.close();
	}
	
	public boolean insert(VendedorDTO dto){
		
		ContentValues ctv = new ContentValues();
		ctv.put("id", dto.getId());
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codigo", dto.getCodigo());
		ctv.put("nome", dto.getNome());
		
		return (db.insert(tableName, null, ctv) > 0);
	}
	
	public boolean delete(VendedorDTO dto){
		
		return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public boolean deleteAll(){

		return (db.delete(tableName,null , null) > 0);
	}

	public boolean deleteByEmpresa(){

		return (db.delete(tableName,"codEmpresa=?" , new String[]{Global.codEmpresa}) > 0);
	}

	public boolean update(VendedorDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codigo", dto.getCodigo());
		ctv.put("nome", dto.getNome());
		
		return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public VendedorDTO getById(Integer ID){

		Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		VendedorDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new VendedorDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodigo(rs.getInt(rs.getColumnIndex("codigo")));
			dto.setNome(rs.getString(rs.getColumnIndex("nome")));
		}
		
		return dto;
	}
	
	public VendedorDTO getAll(){

		Cursor rs = db.rawQuery("SELECT * FROM vendedor WHERE codEmpresa = ".concat(Global.codEmpresa), null);
		
		VendedorDTO venDTO = null;
		
		if(rs.moveToFirst()){
			venDTO = new VendedorDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codigo")), rs.getString(rs.getColumnIndex("nome")));
		}
		
		return venDTO;
	}

	public boolean getVendedorEmpresa(String codEmpresa, String codVendedor){

		Cursor rs = db.query(tableName, columns, "codEmpresa=? and codigo=?", new String[]{codEmpresa.toString(), codVendedor.toString()}, null, null, null);

		return rs.getCount() > 0;
	}
}
