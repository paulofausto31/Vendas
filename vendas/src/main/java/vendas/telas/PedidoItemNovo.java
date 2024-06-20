package vendas.telas;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import persistencia.brl.CaminhoFTPBRL;
import persistencia.brl.ClienteBRL;
import persistencia.brl.ConfiguracaoBRL;
import persistencia.brl.ContaReceberBRL;
import persistencia.brl.FormaPgtoBRL;
import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.PedidoBRL;
import persistencia.brl.PrecoBRL;
import persistencia.brl.ProdutoBRL;
import persistencia.brl.VendedorBRL;
import persistencia.dto.ConfiguracaoDTO;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.PedidoDTO;
import persistencia.dto.ProdutoDTO;
import venda.util.Global;
import venda.util.Util;

public class PedidoItemNovo extends Fragment {
	
	private static int RETORNO_PRODUTO = 1;
	private static final int MENU_TECLADO_NUMERICO = 1;
	private static final int MENU_TECLADO_ALFANUMERICO = 2;
	
	List<String> listaDescricao;
	//List<String> listaCodigo;
	String Preco = "";
	
	PedidoDTO pedDTO; // = new PedidoDTO();
	ItenPedidoDTO itemDTO; // = new ItenPedidoDTO();
	PedidoBRL pedBRL;
	ProdutoBRL proBRL;
	PrecoBRL preBRL;
	ItenPedidoBRL itpBRL;
	
	boolean itemNovo;
	int idItemPedido;
	int unidade2;

	//... Componentes da Tela
	EditText txtCodProduto;
	Button btnPesquisarProduto;
	TextView txtDescricaoProduto;
	TextView txtUnidade;
	EditText txtQuantidade;
	EditText txtDA;
	Spinner cbxPreco;
	RadioButton rdgDesconto;
	RadioButton rdgAcrescimo;
	Button btnIncluirProduto;
	//private static final String URL_sfevend = "http://pfsoft.esy.es/serverphp/sfevend.php";
	//private static final String URL_sfeveit = "http://pfsoft.esy.es/serverphp/sfeveit.php";


	private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
			new ActivityResultContracts.StartActivityForResult(),
			result -> {
				if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
					String itemId = result.getData().getStringExtra("itemId");
					txtCodProduto.setText(itemId);
					CarregaDescricaoProduto(txtCodProduto.getText().toString());
				}
			}
	);

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pedido_item_novo, container, false);

		pedDTO = new PedidoDTO();
		itemDTO = new ItenPedidoDTO();
		preBRL = new PrecoBRL(getContext());
		proBRL = new ProdutoBRL(getContext());
		itpBRL = new ItenPedidoBRL(getContext());
		pedBRL = new PedidoBRL(getContext());

		txtCodProduto = view.findViewById(R.id.txtCodProduto);
		btnPesquisarProduto = view.findViewById(R.id.btnPesquisarProduto);
		txtDescricaoProduto = view.findViewById(R.id.txtDescricaoProduto);
		txtUnidade = view.findViewById(R.id.txtUnidade);
		txtQuantidade = view.findViewById(R.id.txtQuantidade);
		txtDA = view.findViewById(R.id.txtDA);
		cbxPreco = view.findViewById(R.id.cbxPreco);
		rdgDesconto = view.findViewById(R.id.radio0);
		rdgAcrescimo = view.findViewById(R.id.radio1);
		btnIncluirProduto = view.findViewById(R.id.btnIncluirProduto);
		registerForContextMenu(txtCodProduto);

		txtDA.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (txtDescricaoProduto.getText().length() > 0){
					String paramDA = "D";
					if (rdgAcrescimo.isChecked())
						paramDA = "A";
					String valorDA = txtDA.getText().toString().trim();
					if (valorDA.length() > 0 && txtCodProduto.getText().toString().trim().length() > 0){
						if (ValidaDesconto(valorDA)){
							CarregaComboPreco(Long.parseLong(txtCodProduto.getText().toString().trim()),Double.parseDouble(valorDA), paramDA);
						}
						else
							CarregaComboPreco(Long.parseLong(txtCodProduto.getText().toString().trim()),0.0, paramDA);
					}
					else
						CarregaComboPreco(Long.parseLong(txtCodProduto.getText().toString().trim()),0.0, paramDA);
				}
			}
		});

		//... btnPesquisarProduto
		btnPesquisarProduto.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) { btnPesquisarProduto_click() ;}
		});
		//... btnIncluirProduto
		btnIncluirProduto.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) { btnIncluirProduto_click() ;}
		});

		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//Intent it = getIntent();
		itpBRL = new ItenPedidoBRL(getContext());
		//Integer extra = it.getIntExtra("codProduto", 0);
		ItenPedidoDTO itpDTO = venda.util.Global.itemPedidoGlobalDTO;
		if (itpDTO != null){
			//itpDTO = itpBRL.getByCodProduto(venda.util.Global.pedidoGlobalDTO.getId(), extra);
			txtCodProduto.setText(itpDTO.getCodProduto().toString());
			txtQuantidade.setText(itpDTO.getQuantidade().toString());
			CarregaDescricaoProduto(itpDTO.getCodProduto().toString());
			idItemPedido = itpDTO.getId();
			itemNovo = false;
			btnPesquisarProduto.setEnabled(false);
			txtCodProduto.setEnabled(false);
			txtUnidade.setEnabled(false);
		}
		else
			itemNovo = true;

		if(Global.caminhoFTPDTO.getMetodoEntrada() == "N")
			txtCodProduto.setInputType(InputType.TYPE_CLASS_NUMBER);
		else
			txtCodProduto.setInputType(InputType.TYPE_CLASS_TEXT);
	}

	/*
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		setContentView(R.layout.pedido_item_novo);
		//this.setTitle(Global.tituloAplicacao);

		pedDTO = new PedidoDTO();
		itemDTO = new ItenPedidoDTO();
		preBRL = new PrecoBRL(getBaseContext());
		 proBRL = new ProdutoBRL(getBaseContext());
		 itpBRL = new ItenPedidoBRL(getBaseContext());
         pedBRL = new PedidoBRL(getBaseContext());

		txtCodProduto = (EditText)findViewById(R.id.txtCodProduto);
		btnPesquisarProduto = (Button)findViewById(R.id.btnPesquisarProduto);
		txtDescricaoProduto = (TextView)findViewById(R.id.txtDescricaoProduto);
		txtUnidade = (TextView)findViewById(R.id.txtUnidade);
		txtQuantidade = (EditText)findViewById(R.id.txtQuantidade);
		txtDA = (EditText)findViewById(R.id.txtDA);
		cbxPreco = (Spinner)findViewById(R.id.cbxPreco);
		rdgDesconto = (RadioButton)findViewById(R.id.radio0);
		rdgAcrescimo = (RadioButton)findViewById(R.id.radio1);
		btnIncluirProduto = (Button)findViewById(R.id.btnIncluirProduto);
		registerForContextMenu(txtCodProduto);

		//Intent it = getIntent();
	    itpBRL = new ItenPedidoBRL(getBaseContext());
	    //Integer extra = it.getIntExtra("codProduto", 0);
	    ItenPedidoDTO itpDTO = venda.util.Global.itemPedidoGlobalDTO;
	    if (itpDTO != null){
	    	//itpDTO = itpBRL.getByCodProduto(venda.util.Global.pedidoGlobalDTO.getId(), extra);
	    	txtCodProduto.setText(itpDTO.getCodProduto().toString());
	    	txtQuantidade.setText(itpDTO.getQuantidade().toString());
	    	CarregaDescricaoProduto(itpDTO.getCodProduto().toString());
	    	idItemPedido = itpDTO.getId();
	    	itemNovo = false;
	    	btnPesquisarProduto.setEnabled(false);
	    	txtCodProduto.setEnabled(false);
	    	txtUnidade.setEnabled(false);
	    }
	    else
	    	itemNovo = true;
				
		txtDA.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (txtDescricaoProduto.getText().length() > 0){
				String paramDA = "D";
				if (rdgAcrescimo.isChecked())
					paramDA = "A";
		    	 String valorDA = txtDA.getText().toString().trim();
		    	 if (valorDA.length() > 0 && txtCodProduto.getText().toString().trim().length() > 0){
			    	 if (ValidaDesconto(valorDA)){
			    	 	CarregaComboPreco(Long.parseLong(txtCodProduto.getText().toString().trim()),Double.parseDouble(valorDA), paramDA);
			    	 }
			    	 else
			    	 	CarregaComboPreco(Long.parseLong(txtCodProduto.getText().toString().trim()),0.0, paramDA);			    		 
		    	 }
		    	 else
		    	 	CarregaComboPreco(Long.parseLong(txtCodProduto.getText().toString().trim()),0.0, paramDA);
				}
			}
		});

		//... btnPesquisarProduto
		btnPesquisarProduto.setOnClickListener(new Button.OnClickListener() {			
			@Override
			public void onClick(View v) { btnPesquisarProduto_click() ;}
		});
		//... btnIncluirProduto
		btnIncluirProduto.setOnClickListener(new Button.OnClickListener() {			
			@Override
			public void onClick(View v) { btnIncluirProduto_click() ;}
		});
	}
	*/
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		PedidoDTO pedDTO = venda.util.Global.pedidoGlobalDTO;
		pedDTO.setHoraPedidoFim(venda.util.Util.getTime());
		pedBRL.Update(pedDTO);
		venda.util.Global.pedidoGlobalDTO = pedDTO;
		venda.util.Global.itemPedidoGlobalDTO = null;
	}

	/*
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Sub Menu Opções de Entrada");
        menu.add(0,MENU_TECLADO_NUMERICO,0,"Teclado Numérico");
        menu.add(0,MENU_TECLADO_ALFANUMERICO,0,"Teclado AlfaNumerico");
    }

	@Override
	 public boolean onMenuItemSelected(int featureID, MenuItem menu){

		 CaminhoFTPBRL ftpBRL = new CaminhoFTPBRL(getBaseContext(), getParent());
		 
		 Global.caminhoFTPDTO = ftpBRL.getByEmpresa();

		 switch (menu.getItemId()){
		 
		 case MENU_TECLADO_NUMERICO: // mensagem 
			 txtCodProduto.setInputType(InputType.TYPE_CLASS_NUMBER);
			 Global.caminhoFTPDTO.setMetodoEntrada("N");
			 ftpBRL.SalvaCaminhoFTP(Global.caminhoFTPDTO);
			 
			 return true;
			  
		 case MENU_TECLADO_ALFANUMERICO: // mensagem 
			 txtCodProduto.setInputType(InputType.TYPE_CLASS_TEXT);
			 Global.caminhoFTPDTO.setMetodoEntrada("A");
			 ftpBRL.SalvaCaminhoFTP(Global.caminhoFTPDTO);
			 
			 return true;
		 }
		 return false;
	 }
*/
	private void LimpaCampos(){
		txtCodProduto.setText("");
		txtDescricaoProduto.setText("");
		txtUnidade.setText("");
		txtQuantidade.setText("");
		txtDA.setText("");
		CarregaComboPreco(Long.parseLong("0"),0.00, "D");
		btnPesquisarProduto.setEnabled(true);
		txtCodProduto.setEnabled(true);
		txtUnidade.setEnabled(true);
	}
	
	private Boolean ValidaQuantidade(String qtd, String codProduto){
		ConfiguracaoBRL cfgBRL = new ConfiguracaoBRL(getContext());
		
		ConfiguracaoDTO cfgDTO = cfgBRL.getAll();

		Double qtdTotal = itpBRL.getSumQtdAberto(Long.parseLong(codProduto));
		Double saldoEstoque = proBRL.getSaldoEstoque(codProduto);
		Double qtdVendida = Double.parseDouble(qtd); 
		String crtEstoque1 = cfgDTO.getCriticaEstoque().toUpperCase().trim();
		String crtEstoque2 = cfgDTO.getCriticaEstoque().toUpperCase().trim();
		
		if (crtEstoque1.equals("N"))
			return false;
		else if (crtEstoque2.contains("N"))
			return false;
		else if ((qtdTotal + qtdVendida) > saldoEstoque )
			return true;
		else
			return false;
	}

	private Boolean ValidaDesconto(final String valor) {
		ConfiguracaoBRL cfgBRL = new ConfiguracaoBRL(getContext());
		ConfiguracaoDTO cfgDTO = cfgBRL.getAll();
		
		if (cfgDTO.getDescontoAcrescimo().equals("N")){
			Toast.makeText(getContext(), "Desconto/Acrescimo não permitido", Toast.LENGTH_SHORT).show();
			return false;
		}
		else if (Double.parseDouble(valor) > cfgDTO.getDescontoMaximo()){
			Toast.makeText(getContext(), "O Desconto/Acrescimo máximo permitido �: " + cfgDTO.getDescontoMaximo().toString(), Toast.LENGTH_SHORT).show();
			return false;			
		}
		else if(venda.util.Global.descontoAcrescimo == null){
			 AlertDialog.Builder confirmacao = new AlertDialog.Builder(getContext());
			 
			 confirmacao.setTitle("Confirmação Desconto");
			 confirmacao.setMessage("Este desconto/acrescimo vale para todos os itens seguintes ?");
			 confirmacao.setCancelable(false);
			 
			 confirmacao.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					venda.util.Global.descontoAcrescimo = Double.parseDouble(valor);
					venda.util.Global.optionDesconto = rdgDesconto.isChecked();
				}
			});
			 
			 confirmacao.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					venda.util.Global.descontoAcrescimo = null;
					dialog.cancel();	
				}
			});
			 
			 AlertDialog alertDialog = confirmacao.create();

			 alertDialog.show();
		}
		return true;
	}
	
	private void CarregaComboPreco(Long codProduto, Double desconto, String paramDA) {
	    listaDescricao = preBRL.getCombo("preco", codProduto, desconto, paramDA);
		//listaCodigo = preBRL.getCombo("codProduto", "-1", codProduto);
	    
		ArrayAdapter<String> arrayAdapterPreco = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listaDescricao);
		cbxPreco.setAdapter(arrayAdapterPreco);	
		
		cbxPreco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View v,
					int posicao, long id) {
				Preco = listaDescricao.get(posicao);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void CarregaDescricaoProduto(String codProduto){
		ProdutoDTO proDTO = proBRL.getByCodProduto(Long.parseLong(codProduto));
		if (proDTO != null){
			txtDA.setEnabled(true);
			txtDescricaoProduto.setText(proDTO.getDescricao());
			txtUnidade.setText(proDTO.getUnidade());
			unidade2 = proDTO.getUnidade2();
			CarregaComboPreco(proDTO.getCodProduto(), 0.0, " ");
			txtQuantidade.setText("1");
			txtQuantidade.requestFocus();
			if(venda.util.Global.descontoAcrescimo != null){
				rdgDesconto.setChecked(venda.util.Global.optionDesconto);
				rdgAcrescimo.setChecked(!venda.util.Global.optionDesconto);
				txtDA.setText(venda.util.Global.descontoAcrescimo.toString());
			}
		}
		else
			Toast.makeText(getContext(), "Produto não cadastrado", Toast.LENGTH_SHORT).show();
	}

	private void btnPesquisarProduto_click() {
		if (Util.isNumeric(txtCodProduto.getText().toString().trim())){
			CarregaDescricaoProduto(txtCodProduto.getText().toString().trim());
		}
		else if (txtCodProduto.getText().toString().trim().length() > 0)
		{
			Global.prodPesquisa = txtCodProduto.getText().toString().trim();
			Intent intent = new Intent(getContext(), PedidoProdutoLista.class).putExtra("paramProduto", true);
			activityResultLauncher.launch(intent);
			//startActivityForResult(new Intent(getBaseContext(), RVProdutoLista.class).putExtra("paramProduto", true), RETORNO_PRODUTO);
		}else 
		{
			Intent intent = new Intent(getContext(), PedidoProdutoLista.class).putExtra("paramProduto", true);
			activityResultLauncher.launch(intent);
			//startActivityForResult(new Intent(getBaseContext(), RVProdutoLista.class).putExtra("paramProduto", true), RETORNO_PRODUTO);
		}
	}
	/*
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		 if (RETORNO_PRODUTO == requestCode){
			 if (resultCode == RESULT_OK){
				 txtCodProduto.setText(data.getStringExtra("codProduto"));
				 CarregaDescricaoProduto(txtCodProduto.getText().toString());
			 }
		 }
	 }
*/
		private Boolean ValidaPedido(PedidoDTO pedDTO) {
			if (pedDTO.getDataPedido().trim().length() <= 0){
				Toast.makeText(getContext(), "Informe a data do Pedido", Toast.LENGTH_SHORT).show();
				return false;
			}
			if (pedDTO.getFormaPgto().toString().trim().equals("-1")){
				Toast.makeText(getContext(), "Informe a Forma de Pgto", Toast.LENGTH_SHORT).show();
				return false;
			}
			if (pedDTO.getParcela() <= 0){
				Toast.makeText(getContext(), "Informe a Parcela", Toast.LENGTH_SHORT).show();
				return false;
			}
			if (pedDTO.getPrazo() <= 0){
				Toast.makeText(getContext(), "Informe o Prazo", Toast.LENGTH_SHORT).show();
				return false;
			}
			return true;		
		}

/*		private class Sfevend extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... urls) {
	            return SfevendPost();
	        }
	        
	        @Override
	        protected void onPostExecute(String result) {

	        }
	    }*/
		
/*	    private String SfevendPost() {
	        String mensagem = "";

	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost post = new HttpPost(URL_sfevend);
	        pedDTO = venda.util.Global.pedidoGlobalDTO;
	        try {
	            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
	            nameValuePair.add(new BasicNameValuePair("codCliente", pedDTO.getCodCliente().toString()));
	            nameValuePair.add(new BasicNameValuePair("codPedido", pedDTO.getId().toString()));
	            nameValuePair.add(new BasicNameValuePair("dataPedido", pedDTO.getDataPedido().replace("/", "-")));
	            nameValuePair.add(new BasicNameValuePair("codVendedor", pedDTO.getCodVendedor().toString()));
	            nameValuePair.add(new BasicNameValuePair("formaPgto", pedDTO.getFormaPgto()));
	            nameValuePair.add(new BasicNameValuePair("prazo", pedDTO.getPrazo().toString()));
	            nameValuePair.add(new BasicNameValuePair("parcela", pedDTO.getParcela().toString()));
	            nameValuePair.add(new BasicNameValuePair("horaPedido", pedDTO.getHoraPedido()));
	            nameValuePair.add(new BasicNameValuePair("dataPedidoFim", pedDTO.getDataPedidoFim()));
	            nameValuePair.add(new BasicNameValuePair("horaPedidoFim", pedDTO.getHoraPedidoFim()));
	            nameValuePair.add(new BasicNameValuePair("infAdicional", pedDTO.getInfAdicional()));
	            nameValuePair.add(new BasicNameValuePair("latitude", pedDTO.getLatitude()));
	            nameValuePair.add(new BasicNameValuePair("longitude", pedDTO.getLongitude()));

	            post.setEntity(new UrlEncodedFormEntity(nameValuePair));
	            //post.setHeader("Content-type", "application/json");
	            HttpResponse response = httpclient.execute(post);
	            mensagem = "";//inputStreamToString(response.getEntity().getContent()).toString();
	            BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-08"));
	            mensagem = reader.readLine();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return mensagem;
	    }*/

/*		private class Sfeveit extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... urls) {
	            return SfeveitPost();
	        }
	        
	        @Override
	        protected void onPostExecute(String result) {

	        }
	    }*/

/*        private String SfeveitPost() {
	        String mensagem = "";

	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost post = new HttpPost(URL_sfeveit);
	        try {
	            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
	            nameValuePair.add(new BasicNameValuePair("codPedido", itemDTO.getCodPedido().toString()));
	            nameValuePair.add(new BasicNameValuePair("codProduto", itemDTO.getCodProduto().toString()));
	            nameValuePair.add(new BasicNameValuePair("codVendedor", pedDTO.getCodVendedor().toString()));
	            nameValuePair.add(new BasicNameValuePair("preco", itemDTO.getPreco().toString()));
	            nameValuePair.add(new BasicNameValuePair("quantidade", itemDTO.getQuantidade().toString()));
	            nameValuePair.add(new BasicNameValuePair("unidade", itemDTO.getUnidade().toString()));
	            nameValuePair.add(new BasicNameValuePair("DA", itemDTO.getDA()));
	            nameValuePair.add(new BasicNameValuePair("DAValor", itemDTO.getDAValor().toString()));

	            post.setEntity(new UrlEncodedFormEntity(nameValuePair));
	            //post.setHeader("Content-type", "application/json");
	            HttpResponse response = httpclient.execute(post);
	            mensagem = "";//inputStreamToString(response.getEntity().getContent()).toString();
	            BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-08"));
	            mensagem = reader.readLine();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return mensagem;
	    }*/

	    private void IncluiProduto(){
			ItenPedidoDTO itpDTO = new ItenPedidoDTO();
			if (!itemNovo){
				itpDTO = itpBRL.getById(idItemPedido);
				itpBRL.delete(itpDTO);
			}
			itpDTO.setId(0);
			itpDTO.setCodPedido(venda.util.Global.pedidoGlobalDTO.getId());
			itpDTO.setCodProduto(Long.parseLong(txtCodProduto.getText().toString()));
			itpDTO.setQuantidade(Double.parseDouble(txtQuantidade.getText().toString()));
			itpDTO.setPreco(Double.parseDouble(Preco));
			itpDTO.setUnidade(unidade2);
			if (txtDA.getText().length() > 0){
				itpDTO.setDAValor(Double.parseDouble(txtDA.getText().toString()));
				if (rdgAcrescimo.isChecked())
					itpDTO.setDA("A");
				else
					itpDTO.setDA("D");
			}
			else{
				itpDTO.setDAValor(0.00);
				itpDTO.setDA("");
			}
			venda.util.Global.itemPedidoGlobalDTO = itpDTO;	
			itemDTO = itpDTO;
			try {  
				pedDTO = venda.util.Global.pedidoGlobalDTO;
				if (ValidaPedido(pedDTO)){
					itpDTO = venda.util.Global.itemPedidoGlobalDTO;
					if (itpDTO != null){
						// Insere Pedido
						if (pedDTO.getId() == 0){
							pedBRL.InserePedido(pedDTO);
							pedDTO.setId(pedBRL.getIdLast());
							//Sfevend sfevend = new Sfevend();
							//sfevend.execute();
						}
						// Insere Item Pedido
						if (itpDTO.getId() == 0){
							itpDTO.setCodPedido(pedDTO.getId());
							itpBRL.InsereItenPedido(itpDTO);
							//Sfeveit sfeveit = new Sfeveit();
							//sfeveit.execute();
						}
						LimpaCampos();
					}
				}/*
				else
				{
				    TabActivity tabs = (TabActivity) getParent();
				    tabs.getTabHost().setCurrentTab(0); 			 						
				}*/
			} catch (Exception e) {  
			    Toast.makeText(getContext(), "Erro ao incluir pedido", Toast.LENGTH_SHORT).show();
			} finally {  
				venda.util.Global.itemPedidoGlobalDTO = null;
			} 	    	
	    }

	    private void btnIncluirProduto_click(){
		if (ValidaInclusao()){
			if (itemNovo && itpBRL.produtoExistente(venda.util.Global.pedidoGlobalDTO.getId(), Integer.parseInt(txtCodProduto.getText().toString()))){
				 AlertDialog.Builder confirmacao = new AlertDialog.Builder(getContext());
				 
				 confirmacao.setTitle("Confirma Inclus�o");
				 confirmacao.setMessage("Este item j� foi inclu�do, deseja incluir novamente ?");
				 confirmacao.setCancelable(false);
				 
				 confirmacao.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						IncluiProduto();
						dialog.dismiss();
					}
				});
				 
				 confirmacao.setNegativeButton("N�o", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();	
						LimpaCampos();
					}
				});
				 
				 AlertDialog alertDialog = confirmacao.create();

				 alertDialog.show();
			}			
			else
				IncluiProduto();
		}
	}

	private Boolean ValidaLimiteCredito(String qtd, String preco){
		Integer codPedido = 0;
		Double totalPed = 0.00;
		codPedido = Global.pedidoGlobalDTO.getId();
		totalPed = itpBRL.getTotalPedido(codPedido);
		totalPed += Double.parseDouble(qtd) * Double.parseDouble(preco);
		if ((totalPed + Global.totalContasReceber) > Global.totalLimiteCredito)
			return true;
		else
			return false;
	}

	private boolean ValidaInclusao() {
		if (txtDescricaoProduto.getText().toString().trim().length() <= 0){
			Toast.makeText(getContext(), "Selecione um produto", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (txtQuantidade.getText().toString().trim().length() <= 0){
			Toast.makeText(getContext(), "Informe a Quantidade", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(ValidaQuantidade(txtQuantidade.getText().toString().trim(), txtCodProduto.getText().toString().trim())){
			Toast.makeText(getContext(), "Quantidade informada maior que o Saldo em Estoque", Toast.LENGTH_SHORT).show();
			return false;
		}

		if (Preco.trim().equals("Selecione")){
			Toast.makeText(getContext(), "Selecione um Preço", Toast.LENGTH_SHORT).show();
			return false;
		}

		if(!Global.pedidoGlobalDTO.getFormaPgto().equals("DIN")) {
			if (ValidaLimiteCredito(txtQuantidade.getText().toString().trim(), Preco.trim())) {
				Toast.makeText(getContext(), "Este pedido está execedendo o limite de crédito do cliente", Toast.LENGTH_SHORT).show();
			}
		}

			return true;
	}	
}
