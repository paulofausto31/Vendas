package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.ClienteDTO;
import venda.util.Global;

public class ClienteDAO {

	private static String tableName = "cliente";
	private static Context ctx;
	private SQLiteDatabase db;
	private static String[] columns = {"id", "codCliente", "codEmpresa", "nome", "endereco", "telefone",
		"dataUltimaCompra", "valorAtraso", "valorVencer", "formaPgto", "prazo", "cpfCnpj", "seqVisita",
		"infAdicional", "razaoSocial", "bairro", "cidade", "rotaDia", "limiteCredito"};
	
	public ClienteDAO(){}
	
	public ClienteDAO(Context ctx){
		this.ctx = ctx;
		this.db = new db(ctx).getWritableDatabase();
	}

	public void CloseConection(){
		db.close();
	}
	
	public boolean insert(ClienteDTO dto){
		
		ContentValues ctv = new ContentValues();
		ctv.put("id", dto.getId());
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codCliente", dto.getCodCliente());
		ctv.put("nome", dto.getNome());
		ctv.put("endereco", dto.getEndereco());
		ctv.put("telefone", dto.getTelefone());
		ctv.put("dataUltimaCompra", dto.getDataUltimaCompra());
		ctv.put("valorAtraso", dto.getValorAtraso());
		ctv.put("valorVencer", dto.getValorVencer());
		ctv.put("formaPgto", dto.getFormaPgto());
		ctv.put("prazo", dto.getPrazo());
		ctv.put("cpfCnpj", dto.getCpfCnpj());
		ctv.put("seqVisita", dto.getSeqVisita());
		ctv.put("infAdicional", dto.getInfAdicional());
		ctv.put("razaoSocial", dto.getRazaoSocial());
		ctv.put("bairro", dto.getBairro());
		ctv.put("cidade", dto.getCidade());
		ctv.put("rotaDia", dto.getRotaDia());
		ctv.put("limiteCredito", dto.getLimiteCredito());

		return (db.insert(tableName, null, ctv) > 0);
	}
	
	public boolean delete(ClienteDTO dto){
		
		return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
	}

	public boolean deleteByEmpresa(){

		return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
	}

	public boolean deleteAll(){

		return (db.delete(tableName,null , null) > 0);
	}

	public boolean update(ClienteDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codCliente", dto.getCodCliente());
		ctv.put("nome", dto.getNome());
		ctv.put("endereco", dto.getEndereco());
		ctv.put("telefone", dto.getTelefone());
		ctv.put("dataUltimaCompra", dto.getDataUltimaCompra());
		ctv.put("valorAtraso", dto.getValorAtraso());
		ctv.put("valorVencer", dto.getValorVencer());
		ctv.put("formaPgto", dto.getFormaPgto());
		ctv.put("prazo", dto.getPrazo());
		ctv.put("cpfCnpj", dto.getCpfCnpj());
		ctv.put("seqVisita", dto.getSeqVisita());
		ctv.put("infAdicional", dto.getInfAdicional());
		ctv.put("razaoSocial", dto.getRazaoSocial());
		ctv.put("bairro", dto.getBairro());
		ctv.put("cidade", dto.getCidade());
		ctv.put("rotaDia", dto.getRotaDia());
		ctv.put("limiteCredito", dto.getLimiteCredito());

		return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public ClienteDTO getById(Integer ID){

		Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		ClienteDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new ClienteDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodCliente(rs.getInt(rs.getColumnIndex("codCliente")));
			dto.setNome(rs.getString(rs.getColumnIndex("nome")));
			dto.setEndereco(rs.getString(rs.getColumnIndex("endereco")));
			dto.setTelefone(rs.getString(rs.getColumnIndex("telefone")));
			dto.setDataUltimaCompra(rs.getString(rs.getColumnIndex("dataUltimaCompra")));
			dto.setValorAtraso(rs.getDouble(rs.getColumnIndex("valorAtraso")));
			dto.setValorVencer(rs.getDouble(rs.getColumnIndex("valorVencer")));
			dto.setFormaPgto(rs.getString(rs.getColumnIndex("formaPgto")));
			dto.setPrazo(rs.getInt(rs.getColumnIndex("prazo")));
			dto.setCpfCnpj(rs.getString(rs.getColumnIndex("cpfCnpj")));
			dto.setSeqVisita(rs.getInt(rs.getColumnIndex("seqVisita")));
			dto.setInfAdicional(rs.getString(rs.getColumnIndex("infAdicional")));
			dto.setRazaoSocial(rs.getString(rs.getColumnIndex("razaoSocial")));
			dto.setBairro(rs.getString(rs.getColumnIndex("bairro")));
			dto.setCidade(rs.getString(rs.getColumnIndex("cidade")));
			dto.setRotaDia(rs.getString(rs.getColumnIndex("rotaDia")));
			dto.setLimiteCredito(rs.getDouble(rs.getColumnIndex("limiteCredito")));
		}
		
		return dto;
	}
	
	public ClienteDTO getByCodCliente(Integer codCliente){

		Cursor rs = db.query(tableName, columns, "codCliente=? and codEmpresa=?", new String[]{codCliente.toString(), Global.codEmpresa}, null, null, null);
		
		ClienteDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new ClienteDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodCliente(rs.getInt(rs.getColumnIndex("codCliente")));
			dto.setNome(rs.getString(rs.getColumnIndex("nome")));
			dto.setEndereco(rs.getString(rs.getColumnIndex("endereco")));
			dto.setTelefone(rs.getString(rs.getColumnIndex("telefone")));
			dto.setDataUltimaCompra(rs.getString(rs.getColumnIndex("dataUltimaCompra")));
			dto.setValorAtraso(rs.getDouble(rs.getColumnIndex("valorAtraso")));
			dto.setValorVencer(rs.getDouble(rs.getColumnIndex("valorVencer")));
			dto.setFormaPgto(rs.getString(rs.getColumnIndex("formaPgto")));
			dto.setPrazo(rs.getInt(rs.getColumnIndex("prazo")));
			dto.setCpfCnpj(rs.getString(rs.getColumnIndex("cpfCnpj")));
			dto.setSeqVisita(rs.getInt(rs.getColumnIndex("seqVisita")));
			dto.setInfAdicional(rs.getString(rs.getColumnIndex("infAdicional")));
			dto.setRazaoSocial(rs.getString(rs.getColumnIndex("razaoSocial")));
			dto.setBairro(rs.getString(rs.getColumnIndex("bairro")));
			dto.setCidade(rs.getString(rs.getColumnIndex("cidade")));
			dto.setRotaDia(rs.getString(rs.getColumnIndex("rotaDia")));
			dto.setLimiteCredito(rs.getDouble(rs.getColumnIndex("limiteCredito")));
		}
		
		return dto;
	}
	
	public List<ClienteDTO> getByNome(String nome){

		Cursor rs;
/*		if (Global.codRota != null)
			rs = db.query(tableName, columns, "nome like ? and codEmpresa=? and codRota=?", new String[]{"%" + nome + "%", Global.codEmpresa, Global.codRota}, null, null, null);
		else*/
			rs = db.query(tableName, columns, "nome like ? and codEmpresa=?", new String[]{"%" + nome + "%", Global.codEmpresa}, null, null, null);

		ClienteDTO dto = null;
		List<ClienteDTO> lista = new ArrayList<ClienteDTO>();
		
		while(rs.moveToNext()){
			dto = new ClienteDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodCliente(rs.getInt(rs.getColumnIndex("codCliente")));
			dto.setNome(rs.getString(rs.getColumnIndex("nome")));
			dto.setEndereco(rs.getString(rs.getColumnIndex("endereco")));
			dto.setTelefone(rs.getString(rs.getColumnIndex("telefone")));
			dto.setDataUltimaCompra(rs.getString(rs.getColumnIndex("dataUltimaCompra")));
			dto.setValorAtraso(rs.getDouble(rs.getColumnIndex("valorAtraso")));
			dto.setValorVencer(rs.getDouble(rs.getColumnIndex("valorVencer")));
			dto.setFormaPgto(rs.getString(rs.getColumnIndex("formaPgto")));
			dto.setPrazo(rs.getInt(rs.getColumnIndex("prazo")));
			dto.setCpfCnpj(rs.getString(rs.getColumnIndex("cpfCnpj")));
			dto.setSeqVisita(rs.getInt(rs.getColumnIndex("seqVisita")));
			dto.setInfAdicional(rs.getString(rs.getColumnIndex("infAdicional")));
			dto.setRazaoSocial(rs.getString(rs.getColumnIndex("razaoSocial")));
			dto.setBairro(rs.getString(rs.getColumnIndex("bairro")));
			dto.setCidade(rs.getString(rs.getColumnIndex("cidade")));
			dto.setRotaDia(rs.getString(rs.getColumnIndex("rotaDia")));
			dto.setLimiteCredito(rs.getDouble(rs.getColumnIndex("limiteCredito")));
			lista.add(dto);
		}
	
		return lista;
	}
	
	public List<ClienteDTO> getByRazaoSocial(String rSocial){

		Cursor rs = db.query(tableName, columns, "razaoSocial like ? and codEmpresa=?", new String[]{"%" + rSocial + "%", Global.codEmpresa}, null, null, null);
		
		ClienteDTO dto = null;
		List<ClienteDTO> lista = new ArrayList<ClienteDTO>();
		
		while(rs.moveToNext()){
			dto = new ClienteDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodCliente(rs.getInt(rs.getColumnIndex("codCliente")));
			dto.setNome(rs.getString(rs.getColumnIndex("nome")));
			dto.setEndereco(rs.getString(rs.getColumnIndex("endereco")));
			dto.setTelefone(rs.getString(rs.getColumnIndex("telefone")));
			dto.setDataUltimaCompra(rs.getString(rs.getColumnIndex("dataUltimaCompra")));
			dto.setValorAtraso(rs.getDouble(rs.getColumnIndex("valorAtraso")));
			dto.setValorVencer(rs.getDouble(rs.getColumnIndex("valorVencer")));
			dto.setFormaPgto(rs.getString(rs.getColumnIndex("formaPgto")));
			dto.setPrazo(rs.getInt(rs.getColumnIndex("prazo")));
			dto.setCpfCnpj(rs.getString(rs.getColumnIndex("cpfCnpj")));
			dto.setSeqVisita(rs.getInt(rs.getColumnIndex("seqVisita")));
			dto.setInfAdicional(rs.getString(rs.getColumnIndex("infAdicional")));
			dto.setRazaoSocial(rs.getString(rs.getColumnIndex("razaoSocial")));
			dto.setBairro(rs.getString(rs.getColumnIndex("bairro")));
			dto.setCidade(rs.getString(rs.getColumnIndex("cidade")));
			dto.setRotaDia(rs.getString(rs.getColumnIndex("rotaDia")));
			dto.setLimiteCredito(rs.getDouble(rs.getColumnIndex("limiteCredito")));
			lista.add(dto);
		}
	
		return lista;
	}

	public List<ClienteDTO> getByCPFCNPJ(String cpf_cnpj){

		String sql;
		if (Global.codRota != null)
			sql = "SELECT c.* FROM cliente c, rota r where c.codCliente = r.codCliente and c.codEmpresa = r.codEmpresa and r.codEmpresa = ".concat(Global.codEmpresa).concat(" and r.codRota = ").concat(Global.codRota).concat(" and c.cpfCnpj = ").concat(cpf_cnpj);
		else
			sql = "SELECT * FROM cliente where cpfCnpj = ".concat(cpf_cnpj) + " and codEmpresa = ".concat(Global.codEmpresa);

		Cursor rs = db.rawQuery(sql, null);

		ClienteDTO dto = null;
		List<ClienteDTO> lista = new ArrayList<ClienteDTO>();

		while(rs.moveToNext()){
			dto = new ClienteDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodCliente(rs.getInt(rs.getColumnIndex("codCliente")));
			dto.setNome(rs.getString(rs.getColumnIndex("nome")));
			dto.setEndereco(rs.getString(rs.getColumnIndex("endereco")));
			dto.setTelefone(rs.getString(rs.getColumnIndex("telefone")));
			dto.setDataUltimaCompra(rs.getString(rs.getColumnIndex("dataUltimaCompra")));
			dto.setValorAtraso(rs.getDouble(rs.getColumnIndex("valorAtraso")));
			dto.setValorVencer(rs.getDouble(rs.getColumnIndex("valorVencer")));
			dto.setFormaPgto(rs.getString(rs.getColumnIndex("formaPgto")));
			dto.setPrazo(rs.getInt(rs.getColumnIndex("prazo")));
			dto.setCpfCnpj(rs.getString(rs.getColumnIndex("cpfCnpj")));
			dto.setSeqVisita(rs.getInt(rs.getColumnIndex("seqVisita")));
			dto.setInfAdicional(rs.getString(rs.getColumnIndex("infAdicional")));
			dto.setRazaoSocial(rs.getString(rs.getColumnIndex("razaoSocial")));
			dto.setBairro(rs.getString(rs.getColumnIndex("bairro")));
			dto.setCidade(rs.getString(rs.getColumnIndex("cidade")));
			dto.setRotaDia(rs.getString(rs.getColumnIndex("rotaDia")));
			dto.setLimiteCredito(rs.getDouble(rs.getColumnIndex("limiteCredito")));
			lista.add(dto);
		}

		return lista;
	}

	public List<ClienteDTO> getByCodigo(String codigo){

		String sql;
		if (Global.codRota != null)
			sql = "SELECT c.* FROM cliente c, rota r where c.codCliente = r.codCliente and c.codEmpresa = r.codEmpresa and r.codEmpresa = ".concat(Global.codEmpresa).concat(" and r.codRota = ").concat(Global.codRota).concat(" and r.codCliente = ").concat(codigo);
		else
			sql = "SELECT * FROM cliente where codCliente = ".concat(codigo) + " and codEmpresa = ".concat(Global.codEmpresa);

		Cursor rs = db.rawQuery(sql, null);

		ClienteDTO dto = null;
		List<ClienteDTO> lista = new ArrayList<ClienteDTO>();

		while(rs.moveToNext()){
			dto = new ClienteDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodCliente(rs.getInt(rs.getColumnIndex("codCliente")));
			dto.setNome(rs.getString(rs.getColumnIndex("nome")));
			dto.setEndereco(rs.getString(rs.getColumnIndex("endereco")));
			dto.setTelefone(rs.getString(rs.getColumnIndex("telefone")));
			dto.setDataUltimaCompra(rs.getString(rs.getColumnIndex("dataUltimaCompra")));
			dto.setValorAtraso(rs.getDouble(rs.getColumnIndex("valorAtraso")));
			dto.setValorVencer(rs.getDouble(rs.getColumnIndex("valorVencer")));
			dto.setFormaPgto(rs.getString(rs.getColumnIndex("formaPgto")));
			dto.setPrazo(rs.getInt(rs.getColumnIndex("prazo")));
			dto.setCpfCnpj(rs.getString(rs.getColumnIndex("cpfCnpj")));
			dto.setSeqVisita(rs.getInt(rs.getColumnIndex("seqVisita")));
			dto.setInfAdicional(rs.getString(rs.getColumnIndex("infAdicional")));
			dto.setRazaoSocial(rs.getString(rs.getColumnIndex("razaoSocial")));
			dto.setBairro(rs.getString(rs.getColumnIndex("bairro")));
			dto.setCidade(rs.getString(rs.getColumnIndex("cidade")));
			dto.setRotaDia(rs.getString(rs.getColumnIndex("rotaDia")));
			dto.setLimiteCredito(rs.getDouble(rs.getColumnIndex("limiteCredito")));
			lista.add(dto);
		}

		return lista;
	}

	public List<ClienteDTO> getAll(){

		Cursor rs = db.rawQuery("SELECT * FROM cliente where rotaDia = 'S' and codEmpresa = ".concat(Global.codEmpresa), null);
		
		List<ClienteDTO> lista = new ArrayList<ClienteDTO>();
		
		while(rs.moveToNext()){
			ClienteDTO dto = new ClienteDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codCliente")), rs.getString(rs.getColumnIndex("nome")),
					rs.getString(rs.getColumnIndex("endereco")), rs.getString(rs.getColumnIndex("telefone")), rs.getString(rs.getColumnIndex("dataUltimaCompra")),
					rs.getDouble(rs.getColumnIndex("valorAtraso")), rs.getDouble(rs.getColumnIndex("valorVencer")), rs.getString(rs.getColumnIndex("formaPgto")),
					rs.getInt(rs.getColumnIndex("prazo")), rs.getString(rs.getColumnIndex("cpfCnpj")), rs.getInt(rs.getColumnIndex("seqVisita")),
					rs.getString(rs.getColumnIndex("infAdicional")), rs.getString(rs.getColumnIndex("razaoSocial")), rs.getString(rs.getColumnIndex("bairro")),
					rs.getString(rs.getColumnIndex("cidade")), rs.getString(rs.getColumnIndex("rotaDia")), rs.getDouble(rs.getColumnIndex("limiteCredito")));
			lista.add(dto);
		}
		
		return lista;
	}

	public Integer getTotalClientes(){

		Cursor rs = db.rawQuery("SELECT * FROM cliente WHERE codEmpresa = ".concat(Global.codEmpresa), null);
		
		return rs.getCount();
	}

	public List<ClienteDTO> getTodosOrdenado(String campoOrdenacao){

		String sql;
		if (Global.codRota != null)
			sql = "SELECT c.* FROM cliente c, rota r where c.codCliente = r.codCliente and c.codEmpresa = r.codEmpresa and r.codEmpresa = ".concat(Global.codEmpresa).concat(" and r.codRota = ").concat(Global.codRota).concat(" order by ").concat(campoOrdenacao);
		else
			sql = "SELECT * FROM cliente where codEmpresa = ".concat(Global.codEmpresa).concat(" order by ").concat(campoOrdenacao);

		Cursor rs = db.rawQuery(sql, null);

		List<ClienteDTO> lista = new ArrayList<ClienteDTO>();

		while(rs.moveToNext()){
			ClienteDTO dto = new ClienteDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codCliente")), rs.getString(rs.getColumnIndex("nome")),
					rs.getString(rs.getColumnIndex("endereco")), rs.getString(rs.getColumnIndex("telefone")), rs.getString(rs.getColumnIndex("dataUltimaCompra")),
					rs.getDouble(rs.getColumnIndex("valorAtraso")), rs.getDouble(rs.getColumnIndex("valorVencer")), rs.getString(rs.getColumnIndex("formaPgto")),
					rs.getInt(rs.getColumnIndex("prazo")), rs.getString(rs.getColumnIndex("cpfCnpj")), rs.getInt(rs.getColumnIndex("seqVisita")),
					rs.getString(rs.getColumnIndex("infAdicional")), rs.getString(rs.getColumnIndex("razaoSocial")), rs.getString(rs.getColumnIndex("bairro")),
					rs.getString(rs.getColumnIndex("cidade")), rs.getString(rs.getColumnIndex("rotaDia")), rs.getDouble(rs.getColumnIndex("limiteCredito")));
			lista.add(dto);
		}

		return lista;
	}

	public List<ClienteDTO> getRotaDiaOrdenado(String campoOrdenacao){

		String sql;
		if (Global.codRota != null)
			sql = "SELECT c.* FROM cliente c, rota r where c.codCliente = r.codCliente and c.codEmpresa = r.codEmpresa and r.codEmpresa = ".concat(Global.codEmpresa).concat(" and r.codRota = ").concat(Global.codRota).concat(" order by ").concat(campoOrdenacao);
		else
			sql = "SELECT * FROM cliente where rotaDia = 'S' and codEmpresa = ".concat(Global.codEmpresa).concat(" order by ").concat(campoOrdenacao);
		//Cursor rs = db.rawQuery("SELECT * FROM cliente where codEmpresa = ".concat(Global.codEmpresa).concat(" order by ").concat(campoOrdenacao), null);
		Cursor rs = db.rawQuery(sql, null);
		List<ClienteDTO> lista = new ArrayList<ClienteDTO>();
		
		while(rs.moveToNext()){
			ClienteDTO dto = new ClienteDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codCliente")), rs.getString(rs.getColumnIndex("nome")),
					rs.getString(rs.getColumnIndex("endereco")), rs.getString(rs.getColumnIndex("telefone")), rs.getString(rs.getColumnIndex("dataUltimaCompra")),
					rs.getDouble(rs.getColumnIndex("valorAtraso")), rs.getDouble(rs.getColumnIndex("valorVencer")), rs.getString(rs.getColumnIndex("formaPgto")),
					rs.getInt(rs.getColumnIndex("prazo")), rs.getString(rs.getColumnIndex("cpfCnpj")), rs.getInt(rs.getColumnIndex("seqVisita")),
					rs.getString(rs.getColumnIndex("infAdicional")), rs.getString(rs.getColumnIndex("razaoSocial")), rs.getString(rs.getColumnIndex("bairro")),
					rs.getString(rs.getColumnIndex("cidade")), rs.getString(rs.getColumnIndex("rotaDia")), rs.getDouble(rs.getColumnIndex("limiteCredito")));
			lista.add(dto);
		}
		
		return lista;
	}

	public List<ClienteDTO> getPorRotaOrdenado(String codRota, String campoOrdenacao){

		String sql = "SELECT c.* FROM cliente c, rota r where c.codCliente = r.codCliente and c.codEmpresa = r.codEmpresa and r.codEmpresa = ".concat(Global.codEmpresa).concat(" and r.codRota = ").concat(codRota).concat(" order by ").concat(campoOrdenacao);
		Cursor rs = db.rawQuery(sql, null);
		//Cursor rs = db.rawQuery("SELECT * FROM cliente where codEmpresa = ".concat(Global.codEmpresa).concat(" order by ").concat(campoOrdenacao), null);
		List<ClienteDTO> lista = new ArrayList<ClienteDTO>();

		while(rs.moveToNext()){
			ClienteDTO dto = new ClienteDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codCliente")), rs.getString(rs.getColumnIndex("nome")),
					rs.getString(rs.getColumnIndex("endereco")), rs.getString(rs.getColumnIndex("telefone")), rs.getString(rs.getColumnIndex("dataUltimaCompra")),
					rs.getDouble(rs.getColumnIndex("valorAtraso")), rs.getDouble(rs.getColumnIndex("valorVencer")), rs.getString(rs.getColumnIndex("formaPgto")),
					rs.getInt(rs.getColumnIndex("prazo")), rs.getString(rs.getColumnIndex("cpfCnpj")), rs.getInt(rs.getColumnIndex("seqVisita")),
					rs.getString(rs.getColumnIndex("infAdicional")), rs.getString(rs.getColumnIndex("razaoSocial")), rs.getString(rs.getColumnIndex("bairro")),
					rs.getString(rs.getColumnIndex("cidade")), rs.getString(rs.getColumnIndex("rotaDia")), rs.getDouble(rs.getColumnIndex("limiteCredito")));
			lista.add(dto);
		}

		return lista;
	}

	public List<ClienteDTO> getAllCidade(){

		Cursor rs = db.rawQuery("SELECT Cidade FROM cliente WHERE codEmpresa = ".concat(Global.codEmpresa).concat(" group by cidade "), null);
		
		List<ClienteDTO> lista = new ArrayList<ClienteDTO>();
		
		while(rs.moveToNext()){
			ClienteDTO dto = new ClienteDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codCliente")), rs.getString(rs.getColumnIndex("nome")),
					rs.getString(rs.getColumnIndex("endereco")), rs.getString(rs.getColumnIndex("telefone")), rs.getString(rs.getColumnIndex("dataUltimaCompra")),
					rs.getDouble(rs.getColumnIndex("valorAtraso")), rs.getDouble(rs.getColumnIndex("valorVencer")), rs.getString(rs.getColumnIndex("formaPgto")),
					rs.getInt(rs.getColumnIndex("prazo")), rs.getString(rs.getColumnIndex("cpfCnpj")), rs.getInt(rs.getColumnIndex("seqVisita")),
					rs.getString(rs.getColumnIndex("infAdicional")), rs.getString(rs.getColumnIndex("razaoSocial")), rs.getString(rs.getColumnIndex("bairro")),
					rs.getString(rs.getColumnIndex("cidade")), rs.getString(rs.getColumnIndex("rotaDia")), rs.getDouble(rs.getColumnIndex("limiteCredito")));
			lista.add(dto);
		}
		
		return lista;
	}
}

