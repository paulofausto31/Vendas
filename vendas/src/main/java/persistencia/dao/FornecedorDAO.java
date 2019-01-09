package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.FornecedorDTO;
import venda.util.Global;

public class FornecedorDAO {

	private static String tableName = "fornecedor";
	private SQLiteDatabase db;
	private static Context ctx;
	private static String[] columns = {"id", "codEmpresa", "codFornecedor", "descricao"};
	
	public FornecedorDAO(){}
	
	public FornecedorDAO(Context ctx){
		this.ctx = ctx;
		this.db = new db(ctx).getWritableDatabase();
	}
	
	public void CloseConection(){
		db.close();
	}
	
	public boolean insert(FornecedorDTO dto){
		
		ContentValues ctv = new ContentValues();
		ctv.put("id", dto.getId());
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codFornecedor", dto.getCodFornecedor());
		ctv.put("descricao", dto.getDescricao());
		
		return (db.insert(tableName, null, ctv) > 0);
	}
	
	public boolean delete(FornecedorDTO dto){
		
		return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
	}

	public boolean deleteByEmpresa(){

		return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
	}

	public boolean deleteAll(){

		return (db.delete(tableName,null , null) > 0);
	}

	public boolean update(FornecedorDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codFornecedor", dto.getCodFornecedor());
		ctv.put("descricao", dto.getDescricao());
		
		return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public FornecedorDTO getById(Integer ID){

		Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		FornecedorDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new FornecedorDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodFornecedor(rs.getInt(rs.getColumnIndex("codFornecedor")));
			dto.setDescricao(rs.getString(rs.getColumnIndex("descricao")));
		}
		
		return dto;
	}
	
	public List<FornecedorDTO> getAll(){

		Cursor rs = db.rawQuery("SELECT * FROM fornecedor WHERE codEmpresa = ".concat(Global.codEmpresa).concat(" order by Descricao"), null);
		
		List<FornecedorDTO> lista = new ArrayList<FornecedorDTO>();
		
		while(rs.moveToNext()){
			FornecedorDTO dto = new FornecedorDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codFornecedor")), rs.getString(rs.getColumnIndex("descricao")));
			lista.add(dto);
		}
		
		return lista;
	}

}
