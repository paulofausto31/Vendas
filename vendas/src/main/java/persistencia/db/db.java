package persistencia.db;

import static com.itextpdf.text.factories.RomanAlphabetFactory.getString;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vendas.telas.R;

public class db extends SQLiteOpenHelper {

	private static String dbName = "palmvenda.db";
	private static String sql; 
	private static int version = 33; //Integer.parseInt(getString(R.string.bco_versao));
	public db(Context ctx) {
		super(ctx, dbName, null, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		sql = "CREATE TABLE IF NOT EXISTS [rota] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codRota] INTEGER, [codCliente] INTEGER, [seqVisita] INTEGER)";
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS [Empresa] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [cnpj] VARCHAR(14), [RazaoSocial] VARCHAR(100), [Fantasia] VARCHAR(100))";
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS [LocalizacaoGPS] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codVendedor] VARCHAR(4), [DataHora] VARCHAR(20), [latitude] VARCHAR(20), [longitude] VARCHAR(20))";
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS [configuracaoFTP] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [serverLocal] VARCHAR(30), [userLocal] VARCHAR(30), [passwordLocal] VARCHAR(30), [serverRemoto] VARCHAR(30), [userRemoto] VARCHAR(30), [passwordRemoto] VARCHAR(30), [caminho] VARCHAR(60), [emailEmpresa] VARCHAR(60), [portaFTP] VARCHAR(2),[metodoEntrada] VARCHAR(1), [comDefault] VARCHAR(1))";
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS [formaPgto] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codFPgto] VARCHAR(4), [descricao] VARCHAR(40), [multa] DECIMAL(18,2), [juros] DECIMAL(18,2))";
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS [fornecedor] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codFornecedor] INTEGER, [descricao] VARCHAR(40))";
		db.execSQL(sql);					
		sql = "CREATE TABLE IF NOT EXISTS [grupo] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codGrupo] VARCHAR(3), [descricao] VARCHAR(40))";
		db.execSQL(sql);					
		sql = "CREATE TABLE IF NOT EXISTS [vendedor] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codigo] INTEGER, [nome] VARCHAR(40))";
		db.execSQL(sql);
		sql = "CREATE TABLE [configuracao] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [descontoAcrescimo] VARCHAR(1), [codEmpresa] VARCHAR(15), [criticaEstoque] VARCHAR(1), [prazoMaximo] INTEGER, [parcelaMaxima] INTEGER, [descontoMaximo] DECIMAL(18,2), [dataCarga] VARCHAR(10), [horaCarga] VARCHAR(8), [mensagem] VARCHAR(500))";
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS [cliente] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codCliente] INTEGER, [nome] VARCHAR(40), " +
				"[endereco] VARCHAR(40), [telefone] VARCHAR(13), [dataUltimaCompra] VARCHAR(10), [valorAtraso] DECIMAL(18,2), " +
				"[valorVencer] DECIMAL(18,2), [formaPgto] VARCHAR(4), [prazo] INTEGER, [cpfCnpj] VARCHAR(18), [seqVisita] INTEGER, " +
				"[infAdicional] VARCHAR(1), [razaoSocial] VARCHAR(100), [bairro] VARCHAR(50), [cidade] VARCHAR(50), [rotaDia] VARCHAR(1), [limiteCredito] DECIMAL(18,2))";
		db.execSQL(sql);					
		sql = "CREATE TABLE IF NOT EXISTS [produto] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codProduto] BIGINT, [descricao] VARCHAR(100), " +
				"[unidade] VARCHAR(6), [estoque] DECIMAL(18,2), [grupo] VARCHAR(3), [fornecedor] INTEGER, [unidade2] INTEGER)";
		db.execSQL(sql);					
		sql = "CREATE TABLE IF NOT EXISTS [preco] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codProduto] BIGINT, [preco] DECIMAL(18,2))";
		db.execSQL(sql);					
		// Cria tabela de Contas a Receber
		sql = "CREATE TABLE IF NOT EXISTS [contaReceber] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codCliente] INTEGER, [documento] VARCHAR(20), [dataVencimento] VARCHAR(20), [dataPromessa] VARCHAR(20), [valor] DECIMAL(18,2), [formaPgto] VARCHAR(5))";
		db.execSQL(sql);					
		// Cria tabela de Justificativa de Positivacao
		sql = "CREATE TABLE IF NOT EXISTS [justificativaPositivacao] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codJustPos] INTEGER, [descricao] VARCHAR(20))";
		db.execSQL(sql);		
		// Cria tabela de Clientes nao Positivados
		sql = "CREATE TABLE [ClienteNaoPositivado] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codCliente] INTEGER, [codJustificativa] INTEGER, [obs] VARCHAR(300), [data] VARCHAR(20), [hora] VARCHAR(20), [dataFim] VARCHAR(20), [horaFim] VARCHAR(20), [baixado] INTEGER)";
		db.execSQL(sql);		
		// Cria tabela de Pedidos
		sql = "CREATE TABLE [Pedidos] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codPedido] INTEGER, [codCliente] INTEGER, [codVendedor] INTEGER, [formaPgto] VARCHAR(10), [prazo] INTEGER, [parcela] INTEGER, [dataPedido] VARCHAR(20), [horaPedido] VARCHAR(20), [baixado] VARCHAR(1), [dataPedidoFim] VARCHAR(20), [horaPedidoFim] VARCHAR(20), [infAdicional] VARCHAR(250), [latitude] VARCHAR(50), [longitude] VARCHAR(50), [fechado] VARCHAR(1))";
		db.execSQL(sql);		
		// Cria tabela de Itens Pedidos
		sql = "CREATE TABLE [ItensPedido] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codPedido] INTEGER, [codProduto] BIGINT, [preco] NUMERIC(18,4), [quantidade] NUMERIC(18,2), [unidade] INTEGER, [DA] VARCHAR(1),[DAValor] NUMERIC(18,2))";
		db.execSQL(sql);			
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		sql = "DROP TABLE IF EXISTS [Rota]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [Empresa]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [localizacaoGPS]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [configuracao]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [vendedor]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [configuracaoFTP]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [formaPgto]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [fornecedor]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [grupo]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [cliente]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [produto]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [preco]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [contaReceber]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [justificativaPositivacao]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [ClienteNaoPositivado]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [Pedidos]";
		db.execSQL(sql);
		sql = "DROP TABLE IF EXISTS [ItensPedido]";
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS [rota] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codRota] INTEGER, [codCliente] INTEGER, [seqVisita] INTEGER)";
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS [Empresa] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [cnpj] VARCHAR(14), [RazaoSocial] VARCHAR(100), [Fantasia] VARCHAR(100))";
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS [LocalizacaoGPS] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codVendedor] VARCHAR(4), [DataHora] VARCHAR(20), [latitude] VARCHAR(20), [longitude] VARCHAR(20))";
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS [configuracaoFTP] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [serverLocal] VARCHAR(30), [userLocal] VARCHAR(30), [passwordLocal] VARCHAR(30), [serverRemoto] VARCHAR(30), [userRemoto] VARCHAR(30), [passwordRemoto] VARCHAR(30), [caminho] VARCHAR(60), [emailEmpresa] VARCHAR(60), [portaFTP] VARCHAR(2),[metodoEntrada] VARCHAR(1), [comDefault] VARCHAR(1))";
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS [formaPgto] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codFPgto] VARCHAR(4), [descricao] VARCHAR(40), [multa] DECIMAL(18,2), [juros] DECIMAL(18,2))";
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS [fornecedor] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codFornecedor] INTEGER, [descricao] VARCHAR(40))";
		db.execSQL(sql);					
		sql = "CREATE TABLE IF NOT EXISTS [grupo] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codGrupo] VARCHAR(3), [descricao] VARCHAR(40))";
		db.execSQL(sql);					
		sql = "CREATE TABLE IF NOT EXISTS [vendedor] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codigo] INTEGER, [nome] VARCHAR(40))";
		db.execSQL(sql);
		sql = "CREATE TABLE [configuracao] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [descontoAcrescimo] VARCHAR(1), [codEmpresa] VARCHAR(15), [criticaEstoque] VARCHAR(1), [prazoMaximo] INTEGER, [parcelaMaxima] INTEGER, [descontoMaximo] DECIMAL(18,2), [dataCarga] VARCHAR(10), [horaCarga] VARCHAR(8), [mensagem] VARCHAR(500))";
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS [cliente] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codCliente] INTEGER, [nome] VARCHAR(40), " +
				"[endereco] VARCHAR(40), [telefone] VARCHAR(13), [dataUltimaCompra] VARCHAR(10), [valorAtraso] DECIMAL(18,2), " +
				"[valorVencer] DECIMAL(18,2), [formaPgto] VARCHAR(4), [prazo] INTEGER, [cpfCnpj] VARCHAR(18), [seqVisita] INTEGER, " +
				"[infAdicional] VARCHAR(1), [razaoSocial] VARCHAR(100), [bairro] VARCHAR(50), [cidade] VARCHAR(50), [rotaDia] VARCHAR(1), [limiteCredito] DECIMAL(18,2))";
		db.execSQL(sql);					
		sql = "CREATE TABLE IF NOT EXISTS [produto] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codProduto] BIGINT, [descricao] VARCHAR(100), " +
				"[unidade] VARCHAR(6), [estoque] DECIMAL(18,2), [grupo] VARCHAR(3), [fornecedor] INTEGER, [unidade2] INTEGER)";
		db.execSQL(sql);					
		sql = "CREATE TABLE IF NOT EXISTS [preco] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codProduto] BIGINT, [preco] DECIMAL(18,2))";
		db.execSQL(sql);					
		// Cria tabela de Contas a Receber
		sql = "CREATE TABLE IF NOT EXISTS [contaReceber] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codCliente] INTEGER, [documento] VARCHAR(20), [dataVencimento] VARCHAR(20), [dataPromessa] VARCHAR(20), [valor] DECIMAL(18,2), [formaPgto] VARCHAR(5))";
		db.execSQL(sql);					
		// Cria tabela de Justificativa de Positiva��o
		sql = "CREATE TABLE IF NOT EXISTS [justificativaPositivacao] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codJustPos] INTEGER, [descricao] VARCHAR(20))";
		db.execSQL(sql);		
		// Cria tabela de Clientes n�o Positivados
		sql = "CREATE TABLE [ClienteNaoPositivado] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codCliente] INTEGER, [codJustificativa] INTEGER, [obs] VARCHAR(300), [data] VARCHAR(20), [hora] VARCHAR(20), [dataFim] VARCHAR(20), [horaFim] VARCHAR(20), [baixado] INTEGER)";
		db.execSQL(sql);		
		// Cria tabela de Pedidos
		sql = "CREATE TABLE [Pedidos] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codPedido] INTEGER, [codCliente] INTEGER, [codVendedor] INTEGER, [formaPgto] VARCHAR(10), [prazo] INTEGER, [parcela] INTEGER, [dataPedido] VARCHAR(20), [horaPedido] VARCHAR(20), [baixado] VARCHAR(1), [dataPedidoFim] VARCHAR(20), [horaPedidoFim] VARCHAR(20), [infAdicional] VARCHAR(250), [latitude] VARCHAR(50), [longitude] VARCHAR(50), [fechado] VARCHAR(1))";
		db.execSQL(sql);		
		// Cria tabela de Itens Pedidos
		sql = "CREATE TABLE [ItensPedido] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [codEmpresa] VARCHAR(15), [codPedido] INTEGER, [codProduto] BIGINT, [preco] NUMERIC(18,4), [quantidade] NUMERIC(18,2), [unidade] INTEGER, [DA] VARCHAR(1),[DAValor] NUMERIC(18,2))";
		db.execSQL(sql);		
	}

}
