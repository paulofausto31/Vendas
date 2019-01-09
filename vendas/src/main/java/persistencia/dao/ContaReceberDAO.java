package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.ContaReceberDTO;
import venda.util.Global;

public class ContaReceberDAO {
	private static String tableName = "contaReceber";
	private SQLiteDatabase db;
	private static Context ctx;
	private static String[] columns = {"id", "codEmpresa", "codCliente", "documento", "dataVencimento", "dataPromessa", "valor", "formaPgto"};
	
	public ContaReceberDAO(){}
	
	public ContaReceberDAO(Context ctx){
		this.ctx = ctx;
		this.db = new db(ctx).getWritableDatabase();
	}
	
	public void CloseConection(){
		db.close();
	}
	
	public boolean insert(ContaReceberDTO dto){
		
		ContentValues ctv = new ContentValues();
		ctv.put("id", dto.getId());
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codCliente", dto.getCodCliente());
		ctv.put("documento", dto.getDocumento());
		ctv.put("dataVencimento", dto.getDataVencimento());
		ctv.put("dataPromessa", dto.getDataPromessa());
		ctv.put("valor", dto.getValor());
		ctv.put("formaPgto", dto.getFormaPgto());
		
		return (db.insert(tableName, null, ctv) > 0);
	}
	
	public boolean delete(ContaReceberDTO dto){
		
		return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
	}

	public boolean deleteByEmpresa(){

		return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
	}

	public boolean deleteAll(){

		return (db.delete(tableName,null , null) > 0);
	}

	public boolean update(ContaReceberDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codCliente", dto.getCodCliente());
		ctv.put("documento", dto.getDocumento());
		ctv.put("dataVencimento", dto.getDataVencimento());
		ctv.put("dataPromessa", dto.getDataPromessa());
		ctv.put("valor", dto.getValor());
		ctv.put("formaPgto", dto.getFormaPgto());
		
		return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public ContaReceberDTO getById(Integer ID){

		Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		ContaReceberDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new ContaReceberDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodCliente(rs.getInt(rs.getColumnIndex("codCliente")));
			dto.setDocumento(rs.getString(rs.getColumnIndex("documento")));
			dto.setDataVencimento(rs.getString(rs.getColumnIndex("dataVencimento")));
			dto.setDataPromessa(rs.getString(rs.getColumnIndex("dataPromessa")));
			dto.setValor(rs.getDouble(rs.getColumnIndex("valor")));
			dto.setFormaPgto(rs.getString(rs.getColumnIndex("formaPgto")));
		}
		
		return dto;
	}
	
	public List<ContaReceberDTO> getByCliente(Integer codCliente){

		Cursor rs = db.query(tableName, columns, "codCliente=? and codEmpresa=?", new String[]{codCliente.toString(), Global.codEmpresa}, null, null, null);
		
		ContaReceberDTO dto = null;
		List<ContaReceberDTO> lista = new ArrayList<ContaReceberDTO>();
		
		while(rs.moveToNext()){
			dto = new ContaReceberDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodCliente(rs.getInt(rs.getColumnIndex("codCliente")));
			dto.setDocumento(rs.getString(rs.getColumnIndex("documento")));
			dto.setDataVencimento(rs.getString(rs.getColumnIndex("dataVencimento")));
			dto.setDataPromessa(rs.getString(rs.getColumnIndex("dataPromessa")));
			dto.setValor(rs.getDouble(rs.getColumnIndex("valor")));
			dto.setFormaPgto(rs.getString(rs.getColumnIndex("formaPgto")));
			lista.add(dto);
		}
		
		return lista;
	}

	public List<ContaReceberDTO> getAll(){

		Cursor rs = db.rawQuery("SELECT * FROM contaReceber WHERE codEmpresa = ".concat(Global.codEmpresa), null);
		
		List<ContaReceberDTO> lista = new ArrayList<ContaReceberDTO>();
		
		while(rs.moveToNext()){
			ContaReceberDTO dto = new ContaReceberDTO(rs.getInt(rs.getColumnIndex("id")),rs.getString(rs.getColumnIndex("codEmpresa")),
					rs.getInt(rs.getColumnIndex("codCliente")), rs.getString(rs.getColumnIndex("documento")), 
					rs.getString(rs.getColumnIndex("dataVencimento")), rs.getString(rs.getColumnIndex("dataPromessa")), 
					rs.getDouble(rs.getColumnIndex("valor")), rs.getString(rs.getColumnIndex("formaPgto")));
			lista.add(dto);
		}
		
		return lista;
	}
}
