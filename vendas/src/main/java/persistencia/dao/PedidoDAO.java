package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.PedidoDTO;
import venda.util.Global;

public class PedidoDAO {

	private static String tableName = "pedidos";
	private SQLiteDatabase db;
	private static Context ctx;
	private static String[] columns = {"id", "codEmpresa", "codCliente", "codVendedor", "formaPgto", "prazo",
		"parcela", "dataPedido", "horaPedido", "baixado", "dataPedidoFim", "horaPedidoFim", 
		"infAdicional", "latitude", "longitude", "fechado", "codPedidoMySQL", "dataPedidoEnvio", "horaPedidoEnvio",
	    "dataEntrega"};
	
	public PedidoDAO(){}
	
	public PedidoDAO(Context ctx){
		this.ctx = ctx;
		this.db = new db(ctx).getWritableDatabase();
	}
	
	public void CloseConection(){
		db.close();
	}
	
	public boolean insert(PedidoDTO dto){
		
		ContentValues ctv = new ContentValues();
		//ctv.put("id", dto.getId());
		ctv.put("codEmpresa", Global.codEmpresa);
		ctv.put("codCliente", dto.getCodCliente());
		ctv.put("codVendedor", dto.getCodVendedor());
		ctv.put("formaPgto", dto.getFormaPgto());
		ctv.put("prazo", dto.getPrazo());
		ctv.put("parcela", dto.getParcela());
		ctv.put("dataPedido", dto.getDataPedido());
		ctv.put("horaPedido", dto.getHoraPedido());
		ctv.put("baixado", dto.getBaixado());
		ctv.put("dataPedidoFim", dto.getDataPedidoFim());
		ctv.put("horaPedidoFim", dto.getHoraPedidoFim());
		ctv.put("infAdicional", dto.getInfAdicional());
		ctv.put("latitude", dto.getLatitude());
		ctv.put("longitude", dto.getLongitude());
		ctv.put("fechado", dto.getFechado());
		ctv.put("codPedidoMySQL", dto.getCodPedidoMySQL());
		ctv.put("dataPedidoEnvio", dto.getDataPedidoEnvio());
		ctv.put("horaPedidoEnvio", dto.getHoraPedidoEnvio());
		ctv.put("dataEntrega", dto.getDataEntrega());

		return (db.insert(tableName, null, ctv) > 0);
	}
	
	public boolean delete(PedidoDTO dto){
		
		return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
	}

	public boolean deleteByEmpresa(){

		return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
	}

	public boolean deleteAll(){

		return (db.delete(tableName,null , null) > 0);
	}

	public boolean update(PedidoDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("codEmpresa", Global.codEmpresa);
		ctv.put("codCliente", dto.getCodCliente());
		ctv.put("codVendedor", dto.getCodVendedor());
		ctv.put("formaPgto", dto.getFormaPgto());
		ctv.put("prazo", dto.getPrazo());
		ctv.put("parcela", dto.getParcela());
		ctv.put("dataPedido", dto.getDataPedido());
		ctv.put("horaPedido", dto.getHoraPedido());
		ctv.put("baixado", dto.getBaixado());
		ctv.put("dataPedidoFim", dto.getDataPedidoFim());
		ctv.put("horaPedidoFim", dto.getHoraPedidoFim());
		ctv.put("infAdicional", dto.getInfAdicional());
		ctv.put("latitude", dto.getLatitude());
		ctv.put("longitude", dto.getLongitude());
		ctv.put("fechado", dto.getFechado());
		ctv.put("codPedidoMySQL", dto.getCodPedidoMySQL());
		ctv.put("dataPedidoEnvio", dto.getDataPedidoEnvio());
		ctv.put("horaPedidoEnvio", dto.getHoraPedidoEnvio());
		ctv.put("dataEntrega", dto.getDataEntrega());

		return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public boolean updateBaixado(){

		ContentValues ctv = new ContentValues();
		ctv.put("baixado", 1);
		ctv.put("dataPedidoEnvio", venda.util.Util.getDate());
		ctv.put("horaPedidoEnvio", venda.util.Util.getTime());
		
		return (db.update(tableName, ctv, "codEmpresa=? and fechado=1", new String[]{Global.codEmpresa}) > 0);
	}

	public boolean updatePedidoMySQL(Integer codPedido, Long codPedidoMySQL){

		ContentValues ctv = new ContentValues();
		ctv.put("codPedidoMySQL", codPedidoMySQL);

		return (db.update(tableName, ctv, "id=?", new String[]{codPedido.toString()}) > 0);
	}

	public boolean AbrePedidoBaixado(Integer id){

		ContentValues ctv = new ContentValues();
		ctv.put("baixado", 0);
		
		return (db.update(tableName, ctv, "id=?", new String[]{id.toString()}) > 0);
	}

	public boolean AbrePedidoPendente(Integer id){

		ContentValues ctv = new ContentValues();
		ctv.put("fechado", 0);

		return (db.update(tableName, ctv, "id=?", new String[]{id.toString()}) > 0);
	}

	public boolean FechaPedidoPendente(Integer id){

		ContentValues ctv = new ContentValues();
		ctv.put("fechado", 1);

		return (db.update(tableName, ctv, "id=?", new String[]{id.toString()}) > 0);
	}

	public PedidoDTO getById(Integer ID){

		Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		PedidoDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new PedidoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodCliente(rs.getInt(rs.getColumnIndex("codCliente")));
			dto.setCodVendedor(rs.getInt(rs.getColumnIndex("codVendedor")));
			dto.setFormaPgto(rs.getString(rs.getColumnIndex("formaPgto")));
			dto.setPrazo(rs.getInt(rs.getColumnIndex("prazo")));
			dto.setParcela(rs.getInt(rs.getColumnIndex("parcela")));
			dto.setDataPedido(rs.getString(rs.getColumnIndex("dataPedido")));
			dto.setHoraPedido(rs.getString(rs.getColumnIndex("horaPedido")));
			dto.setBaixado(rs.getInt(rs.getColumnIndex("baixado")));
			dto.setDataPedidoFim(rs.getString(rs.getColumnIndex("dataPedidoFim")));
			dto.setHoraPedidoFim(rs.getString(rs.getColumnIndex("horaPedidoFim")));
			dto.setInfAdicional(rs.getString(rs.getColumnIndex("infAdicional")));
			dto.setLatitude(rs.getString(rs.getColumnIndex("latitude")));
			dto.setLongitude(rs.getString(rs.getColumnIndex("longitude")));
			dto.setFechado(rs.getString(rs.getColumnIndex("fechado")));
			dto.setCodPedidoMySQL(rs.getInt(rs.getColumnIndex("codPedidoMySQL")));
			dto.setDataPedidoEnvio(rs.getString(rs.getColumnIndex("dataPedidoEnvio")));
			dto.setHoraPedidoEnvio(rs.getString(rs.getColumnIndex("horaPedidoEnvio")));
			dto.setDataEntrega(rs.getString(rs.getColumnIndex("dataEntrega")));
		}
		
		return dto;
	}
	
	public List<PedidoDTO> getByCodCliente(Integer codCliente){

		Cursor rs = db.query(tableName, columns, "codCliente=? and codEmpresa=?", new String[]{codCliente.toString(), Global.codEmpresa}, null, null, "id DESC");
		
		PedidoDTO dto = null;
		List<PedidoDTO> lista = new ArrayList<PedidoDTO>();
		
		while(rs.moveToNext()){
			dto = new PedidoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodCliente(rs.getInt(rs.getColumnIndex("codCliente")));
			dto.setCodVendedor(rs.getInt(rs.getColumnIndex("codVendedor")));
			dto.setFormaPgto(rs.getString(rs.getColumnIndex("formaPgto")));
			dto.setPrazo(rs.getInt(rs.getColumnIndex("prazo")));
			dto.setParcela(rs.getInt(rs.getColumnIndex("parcela")));
			dto.setDataPedido(rs.getString(rs.getColumnIndex("dataPedido")));
			dto.setHoraPedido(rs.getString(rs.getColumnIndex("horaPedido")));
			dto.setBaixado(rs.getInt(rs.getColumnIndex("baixado")));
			dto.setDataPedidoFim(rs.getString(rs.getColumnIndex("dataPedidoFim")));
			dto.setHoraPedidoFim(rs.getString(rs.getColumnIndex("horaPedidoFim")));
			dto.setInfAdicional(rs.getString(rs.getColumnIndex("infAdicional")));
			dto.setLatitude(rs.getString(rs.getColumnIndex("latitude")));
			dto.setLongitude(rs.getString(rs.getColumnIndex("longitude")));
			dto.setFechado(rs.getString(rs.getColumnIndex("fechado")));
			dto.setCodPedidoMySQL(rs.getInt(rs.getColumnIndex("codPedidoMySQL")));
			dto.setDataPedidoEnvio(rs.getString(rs.getColumnIndex("dataPedidoEnvio")));
			dto.setHoraPedidoEnvio(rs.getString(rs.getColumnIndex("horaPedidoEnvio")));
			dto.setDataEntrega(rs.getString(rs.getColumnIndex("dataEntrega")));
			lista.add(dto);
		}
		
		return lista;
	}
	
	public List<PedidoDTO> getAll(){

		Cursor rs = db.rawQuery("SELECT * FROM pedidos WHERE codEmpresa = ".concat(Global.codEmpresa), null);
		
		List<PedidoDTO> lista = new ArrayList<PedidoDTO>();
		
		while(rs.moveToNext()){
			PedidoDTO dto = new PedidoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codCliente")), rs.getInt(rs.getColumnIndex("codVendedor")),
					rs.getString(rs.getColumnIndex("formaPgto")), rs.getInt(rs.getColumnIndex("prazo")), rs.getInt(rs.getColumnIndex("parcela")),
					rs.getString(rs.getColumnIndex("dataPedido")), rs.getString(rs.getColumnIndex("horaPedido")), rs.getInt(rs.getColumnIndex("baixado")),
					rs.getString(rs.getColumnIndex("dataPedidoFim")), rs.getString(rs.getColumnIndex("horaPedidoFim")), 
					rs.getString(rs.getColumnIndex("infAdicional")),rs.getString(rs.getColumnIndex("latitude")),rs.getString(rs.getColumnIndex("longitude")),rs.getString(rs.getColumnIndex("fechado")),rs.getInt(rs.getColumnIndex("codPedidoMySQL")),
					rs.getString(rs.getColumnIndex("dataPedidoEnvio")), rs.getString(rs.getColumnIndex("horaPedidoEnvio")), rs.getString(rs.getColumnIndex("dataEntrega")));
			lista.add(dto);
		}
		
		return lista;
	}

	public List<PedidoDTO> getAllAberto(){

		Cursor rs = db.rawQuery("SELECT * FROM pedidos where baixado = 0 and fechado = 1 and codEmpresa = ".concat(Global.codEmpresa), null);
		
		List<PedidoDTO> lista = new ArrayList<PedidoDTO>();
		
		while(rs.moveToNext()){
			PedidoDTO dto = new PedidoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codCliente")), rs.getInt(rs.getColumnIndex("codVendedor")),
					rs.getString(rs.getColumnIndex("formaPgto")), rs.getInt(rs.getColumnIndex("prazo")), rs.getInt(rs.getColumnIndex("parcela")),
					rs.getString(rs.getColumnIndex("dataPedido")), rs.getString(rs.getColumnIndex("horaPedido")), rs.getInt(rs.getColumnIndex("baixado")),
					rs.getString(rs.getColumnIndex("dataPedidoFim")), rs.getString(rs.getColumnIndex("horaPedidoFim")), 
					rs.getString(rs.getColumnIndex("infAdicional")),rs.getString(rs.getColumnIndex("latitude")),rs.getString(rs.getColumnIndex("longitude")),rs.getString(rs.getColumnIndex("fechado")),rs.getInt(rs.getColumnIndex("codPedidoMySQL")),
					rs.getString(rs.getColumnIndex("dataPedidoEnvio")), rs.getString(rs.getColumnIndex("horaPedidoEnvio")), rs.getString(rs.getColumnIndex("dataEntrega")));
			lista.add(dto);
		}
		
		return lista;
	}

	public Integer getTotalPositivacao(){

		Cursor rs = db.rawQuery("SELECT * FROM Pedidos p, Cliente c where p.codCliente = c.codCliente and baixado = 0 and p.codEmpresa = ".concat(Global.codEmpresa), null);
		
		return rs.getCount();
	}

	public List<PedidoDTO> getAllPedAberto(){

		Cursor rs = db.rawQuery("SELECT p.id,p.codEmpresa,p.codCliente,p.codVendedor,p.formaPgto,p.prazo,p.parcela,p.dataPedido,p.horaPedido,p.baixado,p.dataPedidoFim,p.horaPedidoFim, p.infAdicional, p.latitude, p.longitude, p.fechado, p.codPedidoMySQL, p.dataPedidoEnvio, p.horaPedidoEnvio, p.dataEntrega FROM Pedidos p, Cliente c where p.codCliente = c.codCliente and baixado = 0 and p.codEmpresa = ".concat(Global.codEmpresa), null);
		
		List<PedidoDTO> lista = new ArrayList<PedidoDTO>();
		
		while(rs.moveToNext()){
			PedidoDTO dto = new PedidoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codCliente")), rs.getInt(rs.getColumnIndex("codVendedor")),
					rs.getString(rs.getColumnIndex("formaPgto")), rs.getInt(rs.getColumnIndex("prazo")), rs.getInt(rs.getColumnIndex("parcela")),
					rs.getString(rs.getColumnIndex("dataPedido")), rs.getString(rs.getColumnIndex("horaPedido")), rs.getInt(rs.getColumnIndex("baixado")),
					rs.getString(rs.getColumnIndex("dataPedidoFim")), rs.getString(rs.getColumnIndex("horaPedidoFim")), 
					rs.getString(rs.getColumnIndex("infAdicional")),rs.getString(rs.getColumnIndex("latitude")),rs.getString(rs.getColumnIndex("longitude")),rs.getString(rs.getColumnIndex("fechado")),rs.getInt(rs.getColumnIndex("codPedidoMySQL")),
					rs.getString(rs.getColumnIndex("dataPedidoEnvio")), rs.getString(rs.getColumnIndex("horaPedidoEnvio")), rs.getString(rs.getColumnIndex("dataEntrega")));
			lista.add(dto);
		}
		
		return lista;
	}

	public List<PedidoDTO> getAllPedEnviado(){

		Cursor rs = db.rawQuery("SELECT * FROM pedidos where baixado = 1 and codEmpresa = ".concat(Global.codEmpresa).concat(" order by id desc"), null);
		
		List<PedidoDTO> lista = new ArrayList<PedidoDTO>(); 
		
		while(rs.moveToNext()){
			PedidoDTO dto = new PedidoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codCliente")), rs.getInt(rs.getColumnIndex("codVendedor")),
					rs.getString(rs.getColumnIndex("formaPgto")), rs.getInt(rs.getColumnIndex("prazo")), rs.getInt(rs.getColumnIndex("parcela")),
					rs.getString(rs.getColumnIndex("dataPedido")), rs.getString(rs.getColumnIndex("horaPedido")), rs.getInt(rs.getColumnIndex("baixado")),
					rs.getString(rs.getColumnIndex("dataPedidoFim")), rs.getString(rs.getColumnIndex("horaPedidoFim")), 
					rs.getString(rs.getColumnIndex("infAdicional")),rs.getString(rs.getColumnIndex("latitude")),rs.getString(rs.getColumnIndex("longitude")),rs.getString(rs.getColumnIndex("fechado")),rs.getInt(rs.getColumnIndex("codPedidoMySQL")),
					rs.getString(rs.getColumnIndex("dataPedidoEnvio")), rs.getString(rs.getColumnIndex("horaPedidoEnvio")), rs.getString(rs.getColumnIndex("dataEntrega")));
			lista.add(dto);
		}
		
		return lista;
	}

	public Integer getIdLast(){

		Cursor rs = db.rawQuery("SELECT * FROM pedidos WHERE codEmpresa = ".concat(Global.codEmpresa), null);
		
		Integer id = null;
		
		if(rs.moveToLast()){
			id = rs.getInt(rs.getColumnIndex("id"));
		}
		
		return id;
	}

	public int getPedidoAbertoCliente(Integer codCliente){

		Cursor rs = db.rawQuery("SELECT * FROM pedidos WHERE baixado = 0 and codCliente = ".concat(codCliente.toString()).concat(" and codEmpresa = ").concat(Global.codEmpresa), null);
		
		return rs.getCount();
	}
}

