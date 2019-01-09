package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.ClienteNaoPositivadoDTO;
import venda.util.Global;

public class ClienteNaoPositivadoDAO {
	private static String tableName = "ClienteNaoPositivado";
	private SQLiteDatabase db;
	private static Context ctx;
	private static String[] columns = {"id", "codEmpresa", "codCliente", "codJustificativa", "obs", "data",
		"hora", "dataFim", "horaFim", "baixado"};
	
	public ClienteNaoPositivadoDAO(){}
	
	public ClienteNaoPositivadoDAO(Context ctx){
		this.ctx = ctx;
		this.db = new db(ctx).getWritableDatabase();
	}

	public void CloseConection(){
		db.close();
	}
	public boolean insert(ClienteNaoPositivadoDTO dto){
		
		ContentValues ctv = new ContentValues();
		ctv.put("id", dto.getId());
		ctv.put("codEmpresa", Global.codEmpresa);
		ctv.put("codCliente", dto.getCodCliente());
		ctv.put("codJustificativa", dto.getCodJustificativa());
		ctv.put("obs", dto.getObs());
		ctv.put("data", dto.getData());
		ctv.put("hora", dto.getHora());
		ctv.put("dataFim", dto.getDataFim());
		ctv.put("horaFim", dto.getHoraFim());
		ctv.put("baixado", dto.getBaixado());
		
		return (db.insert(tableName, null, ctv) > 0);
	}
	
	public boolean delete(ClienteNaoPositivadoDTO dto){
		
		return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
	}

	public boolean deleteByEmpresa(){

		return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
	}

	public boolean deleteAll(){

		return (db.delete(tableName,null , null) > 0);
	}

	public boolean update(ClienteNaoPositivadoDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("codEmpresa", Global.codEmpresa);
		ctv.put("codCliente", dto.getCodCliente());
		ctv.put("codJustificativa", dto.getCodJustificativa());
		ctv.put("obs", dto.getObs());
		ctv.put("data", dto.getData());
		ctv.put("hora", dto.getHora());
		ctv.put("dataFim", dto.getDataFim());
		ctv.put("horaFim", dto.getHoraFim());
		ctv.put("baixado", dto.getBaixado());
		
		return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public ClienteNaoPositivadoDTO getById(Integer ID){

		Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		ClienteNaoPositivadoDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new ClienteNaoPositivadoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodCliente(rs.getInt(rs.getColumnIndex("codCliente")));
			dto.setCodJustificativa(rs.getInt(rs.getColumnIndex("codJustificativa")));
			dto.setObs(rs.getString(rs.getColumnIndex("obs")));
			dto.setData(rs.getString(rs.getColumnIndex("data")));
			dto.setHora(rs.getString(rs.getColumnIndex("hora")));
			dto.setDataFim(rs.getString(rs.getColumnIndex("dataFim")));
			dto.setHoraFim(rs.getString(rs.getColumnIndex("horaFim")));
			dto.setBaixado(rs.getInt(rs.getColumnIndex("baixado")));
		}
		
		return dto;
	}
	
	public List<ClienteNaoPositivadoDTO> getByCodCliente(Integer codCliente){

		Cursor rs = db.query(tableName, columns, "codCliente=? and codEmpresa=?", new String[]{codCliente.toString(), Global.codEmpresa}, null, null, null);
		
		ClienteNaoPositivadoDTO dto = null;
		List<ClienteNaoPositivadoDTO> lista = new ArrayList<ClienteNaoPositivadoDTO>();
		
		while(rs.moveToNext()){
			dto = new ClienteNaoPositivadoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodCliente(rs.getInt(rs.getColumnIndex("codCliente")));
			dto.setCodJustificativa(rs.getInt(rs.getColumnIndex("codJustificativa")));
			dto.setObs(rs.getString(rs.getColumnIndex("obs")));
			dto.setData(rs.getString(rs.getColumnIndex("data")));
			dto.setHora(rs.getString(rs.getColumnIndex("hora")));
			dto.setDataFim(rs.getString(rs.getColumnIndex("dataFim")));
			dto.setHoraFim(rs.getString(rs.getColumnIndex("horaFim")));
			dto.setBaixado(rs.getInt(rs.getColumnIndex("baixado")));
			lista.add(dto);
		}
		
		return lista;
	}
	
	public List<ClienteNaoPositivadoDTO> getAll(){

		Cursor rs = db.rawQuery("SELECT * FROM ClienteNaoPositivado WHERE codEmpresa = ".concat(Global.codEmpresa), null);
		
		List<ClienteNaoPositivadoDTO> lista = new ArrayList<ClienteNaoPositivadoDTO>();
		
		while(rs.moveToNext()){
			ClienteNaoPositivadoDTO dto = new ClienteNaoPositivadoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codCliente")),
					rs.getInt(rs.getColumnIndex("codJustificativa")), rs.getString(rs.getColumnIndex("obs")), rs.getString(rs.getColumnIndex("data")),
					rs.getString(rs.getColumnIndex("hora")), rs.getString(rs.getColumnIndex("dataFim")), rs.getString(rs.getColumnIndex("horaFim")), 
					rs.getInt(rs.getColumnIndex("baixado")));
			lista.add(dto);
		}
		
		return lista;
	}
	
	public List<ClienteNaoPositivadoDTO> getAllAberto(){

		Cursor rs = db.rawQuery("SELECT * FROM ClienteNaoPositivado where baixado = 0 and codEmpresa = ".concat(Global.codEmpresa), null);
		
		List<ClienteNaoPositivadoDTO> lista = new ArrayList<ClienteNaoPositivadoDTO>();
		
		while(rs.moveToNext()){
			ClienteNaoPositivadoDTO dto = new ClienteNaoPositivadoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codCliente")),
					rs.getInt(rs.getColumnIndex("codJustificativa")), rs.getString(rs.getColumnIndex("obs")), rs.getString(rs.getColumnIndex("data")),
					rs.getString(rs.getColumnIndex("hora")), rs.getString(rs.getColumnIndex("dataFim")), rs.getString(rs.getColumnIndex("horaFim")), 
					rs.getInt(rs.getColumnIndex("baixado")));
			lista.add(dto);
		}
		
		return lista;
	}
	
	public boolean updateBaixado(){

		ContentValues ctv = new ContentValues();
		ctv.put("baixado", 1);

		return (db.update(tableName, ctv, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
	}

	public int getCNPCliente(Integer codCliente){

		Cursor rs = db.rawQuery("SELECT * FROM ClienteNaoPositivado WHERE codCliente = ".concat(codCliente.toString()).concat(" and codEmpresa = ").concat(Global.codEmpresa), null);
		
		return rs.getCount();
	}

	public int getCNPClienteAberto(Integer codCliente){

		Cursor rs = db.rawQuery("SELECT * FROM ClienteNaoPositivado WHERE baixado = 0 and codCliente = ".concat(codCliente.toString()).concat(" and codEmpresa = ").concat(Global.codEmpresa), null);
		
		return rs.getCount();
	}
}
