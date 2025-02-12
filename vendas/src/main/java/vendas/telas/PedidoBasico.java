package vendas.telas;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import persistencia.brl.PedidoBRL;
import persistencia.brl.VendedorBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.PedidoDTO;
import persistencia.dto.VendedorDTO;
import venda.util.Global;
import venda.util.MaskWatcher;
import venda.util.dataHora;

public class PedidoBasico extends Fragment {

	List<String> listaDescricao;
	List<String> listaCodigo;
	String codFormaPgto = "";
	ClienteDTO cliDTO = new ClienteDTO();
	VendedorBRL venBRL;
	VendedorDTO venDTO = new VendedorDTO();
	PedidoBRL pedBRL;
	ConfiguracaoBRL cfgBRL;
	ContaReceberBRL recBRL;
	//private static final String URL = "http://pfsoft.esy.es/serverphp/sitlgps.php";

	//... Componentes da Tela
	EditText txtNumeroPedido;
	EditText txtDataPedido;
	EditText txtDataEntrega;
	EditText txtCliente;
	Spinner cbxFormaPgto;
	EditText txtPrazo;
	EditText txtParcela;
	Localizacao localizacao;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pedido_basico, container, false);

		FormaPgtoBRL pgtBRL = new FormaPgtoBRL(getContext());
		venBRL =  new VendedorBRL(getContext());
		venDTO = venBRL.getAll();
		pedBRL = new PedidoBRL(getContext());
		cfgBRL = new ConfiguracaoBRL(getContext());
		recBRL = new ContaReceberBRL(getContext());

		//Intent it = getIntent();
		ClienteBRL cliBRL = new ClienteBRL(getContext());
		int idCliente = Global.idCliente;
		cliDTO = cliBRL.getById(idCliente);
		try {
			Global.totalContasReceber = recBRL.getValorAbertoByCliente(cliDTO.getCodCliente());
			Global.totalLimiteCredito = cliDTO.getLimiteCredito();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		localizacao = new Localizacao(getContext());

		//exibeMensagemVendedor();

		listaDescricao = pgtBRL.getCombo("descricao", "Selecione");
		listaCodigo = pgtBRL.getCombo("codFPgto", "-1");

		txtNumeroPedido = view.findViewById(R.id.txtNumeroPedido);
		txtDataPedido = view.findViewById(R.id.txtDataPedido);
		txtDataEntrega = view.findViewById(R.id.txtDataEntrega);
		txtDataEntrega.addTextChangedListener(new MaskWatcher(txtDataEntrega));
		txtCliente = view.findViewById(R.id.txtClientePedido);
		cbxFormaPgto = view.findViewById(R.id.cbxFormaPgtoPedido);
		txtPrazo = view.findViewById(R.id.txtPrazoPedido);
		txtParcela = view.findViewById(R.id.txtParcelaPedido);
		CaminhoFTPBRL ftpBRL = new CaminhoFTPBRL(getContext(), requireActivity());
		Global.caminhoFTPDTO = ftpBRL.getByEmpresa();

		return view;
	}
/*
	public static PedidoBasico newInstance(int idCliente) {
		PedidoBasico fragment = new PedidoBasico();
		Bundle args = new Bundle();
		args.putInt("idCliente", idCliente);
		fragment.setArguments(args);
		return fragment;
	}
*/

	private void exibeMensagemVendedor() {
		String msg = cfgBRL.getMensagem();
		if (msg.length() > 0)
			venda.util.mensagem.messageBox(getContext(), "Mensagem", msg, "Ok");
	}

	private void LimpaCamposPedido() {
		txtNumeroPedido.setText("0");
		txtDataPedido.setText(venda.util.Util.getDate());
		txtDataEntrega.setText("  /  /    ");
		txtCliente.setText(cliDTO.getNome());
		txtPrazo.setText("1");
		txtParcela.setText("1");
		CarregaComboFormaPgto();

	}

	private void CarregaComboFormaPgto() {
		ArrayAdapter<String> arrayAdapterFormaPgto = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listaDescricao);
		cbxFormaPgto.setAdapter(arrayAdapterFormaPgto);
		for (int i = 0; i < cbxFormaPgto.getCount(); i++) {
			if (listaCodigo.get(i).trim().equals("DIN")) {
				cbxFormaPgto.setSelection(i);
			}
		}

		cbxFormaPgto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View v,
									   int posicao, long id) {
				codFormaPgto = listaCodigo.get(posicao);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		PedidoDTO pedDTO = venda.util.Global.pedidoGlobalDTO;
		if (cliDTO != null)
			pedDTO.setCodCliente(cliDTO.getCodCliente());
		pedDTO.setCodVendedor(venDTO.getCodigo());
		if (dataHora.isDataValida(txtDataEntrega.getText().toString()))
			pedDTO.setDataEntrega(txtDataEntrega.getText().toString());
		else
			pedDTO.setDataEntrega("");
		pedDTO.setDataPedido(txtDataPedido.getText().toString());
		pedDTO.setFormaPgto(codFormaPgto);
		pedDTO.setHoraPedidoFim(venda.util.Util.getTime());
		pedDTO.setParcela(Integer.parseInt(txtParcela.getText().toString()));
		pedDTO.setPrazo(Integer.parseInt(txtPrazo.getText().toString()));
		pedDTO.setLatitude(localizacao.getLatitude());
		pedDTO.setLongitude(localizacao.getLongitude());
		if (pedDTO.getId() == null || pedDTO.getId() == 0){
			pedDTO.setId(0);
			pedDTO.setBaixado(0);
			pedDTO.setFechado("1");
			pedDTO.setHoraPedido(venda.util.Util.getTime());
		}else{
			if (pedDTO.getBaixado() == 1)
				Toast.makeText(getContext(), "Este pedido ja foi baixado e nao pode ser manipulado", Toast.LENGTH_SHORT).show();
			else
				pedBRL.Update(pedDTO);
		}
		venda.util.Global.pedidoGlobalDTO = pedDTO;
//		@SuppressWarnings("unused")
//		HttpAsyncPOST httpAsyncPost = new HttpAsyncPOST();
//		httpAsyncPost.execute();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		PedidoDTO pedDTO = venda.util.Global.pedidoGlobalDTO;
		if (pedDTO.getId() != null){
			CarregaComboFormaPgto();
			ClienteBRL cliBRL = new ClienteBRL(getContext());
			ClienteDTO cliDTO = cliBRL.getByCodCliente(pedDTO.getCodCliente());
			txtNumeroPedido.setText(pedDTO.getId().toString());
			txtDataPedido.setText(pedDTO.getDataPedido());
			txtDataEntrega.setText(pedDTO.getDataEntrega());
			txtCliente.setText(cliDTO.getNome());
			txtPrazo.setText(pedDTO.getPrazo().toString());
			txtParcela.setText(pedDTO.getParcela().toString());
			for (int i = 0; i < cbxFormaPgto.getCount(); i++) {
				if (listaCodigo.get(i).equals(pedDTO.getFormaPgto())) {
					cbxFormaPgto.setSelection(i);
				}
			}
		}
		else
			LimpaCamposPedido();

	}
}
	/*
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		venda.util.Global.itemPedidoGlobalDTO = null;
		setContentView(R.layout.pedido_basico);
		this.setTitle(Global.tituloAplicacao);
		FormaPgtoBRL pgtBRL = new FormaPgtoBRL(getBaseContext());
		venBRL =  new VendedorBRL(getBaseContext());
		venDTO = venBRL.getAll();
		pedBRL = new PedidoBRL(getBaseContext());
		cfgBRL = new ConfiguracaoBRL(getBaseContext());
		recBRL = new ContaReceberBRL(getBaseContext());
		
		Intent it = getIntent();
	    ClienteBRL cliBRL = new ClienteBRL(getBaseContext());
	    int idCliente = it.getIntExtra("idCliente", 1);
	    cliDTO = cliBRL.getById(idCliente);
		try {
			Global.totalContasReceber = recBRL.getValorAbertoByCliente(cliDTO.getCodCliente());
			Global.totalLimiteCredito = cliDTO.getLimiteCredito();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		localizacao = new Localizacao(getBaseContext());
	    
	    exibeMensagemVendedor();
	    
	    listaDescricao = pgtBRL.getCombo("descricao", "Selecione");
		listaCodigo = pgtBRL.getCombo("codFPgto", "-1");
	    
	    txtNumeroPedido = (EditText)findViewById(R.id.txtNumeroPedido);
	    txtDataPedido = (EditText)findViewById(R.id.txtDataPedido);
	    txtCliente = (EditText)findViewById(R.id.txtClientePedido);
	    cbxFormaPgto = (Spinner)findViewById(R.id.cbxFormaPgtoPedido);
	    txtPrazo = (EditText)findViewById(R.id.txtPrazoPedido);
	    txtParcela = (EditText)findViewById(R.id.txtParcelaPedido);
		btnVoltarClientes = (Button) findViewById(R.id.btnPedidoVoltarCliente);
    	CaminhoFTPBRL ftpBRL = new CaminhoFTPBRL(getBaseContext(), getParent());
		Global.caminhoFTPDTO = ftpBRL.getByEmpresa();

		btnVoltarClientes.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) { AbreCLientes(); }
		});

	}

	private void AbreCLientes() {
		Intent clientes = new Intent(this, RVClienteLista.class);
		startActivity(clientes);
	}
	
//	private class HttpAsyncPOST extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... urls) {
//            return post();
//        }
        
//        @Override
//        protected void onPostExecute(String result) {

//        }
//    }
    //private String post() {
    //    String mensagem = "";

//        HttpClient httpclient = new DefaultHttpClient();
//        HttpPost post = new HttpPost(URL);
//        try {
//            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
//            nameValuePair.add(new BasicNameValuePair("loclie", cliDTO.getCodCliente().toString()));
//            nameValuePair.add(new BasicNameValuePair("lodata", txtDataPedido.getText().toString().replace("/", "-")));
//            nameValuePair.add(new BasicNameValuePair("logps1", localizacao.getLatitude()));
//            nameValuePair.add(new BasicNameValuePair("logps2", localizacao.getLongitude()));
//            nameValuePair.add(new BasicNameValuePair("lohora", venda.util.Util.getTime()));
//            nameValuePair.add(new BasicNameValuePair("lovend", venDTO.getCodigo().toString()));

//            post.setEntity(new UrlEncodedFormEntity(nameValuePair));
            //post.setHeader("Content-type", "application/json");
//            HttpResponse response = httpclient.execute(post);
//            mensagem = "";//inputStreamToString(response.getEntity().getContent()).toString();
//            BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-08"));
//            mensagem = reader.readLine();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return mensagem;
//    }


	
	private void exibeMensagemVendedor() {
		String msg = cfgBRL.getMensagem();
		if (msg.length() > 0)
		    venda.util.mensagem.messageBox(this, "Mensagem", msg, "Ok");
	}

	private void LimpaCamposPedido() {
		txtNumeroPedido.setText("0");
		txtDataPedido.setText(venda.util.Util.getDate());
		txtCliente.setText(cliDTO.getNome());
		txtPrazo.setText("1");
		txtParcela.setText("1");
		CarregaComboFormaPgto();
 					
	}

	private void CarregaComboFormaPgto() {
		ArrayAdapter<String> arrayAdapterFormaPgto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaDescricao);
		cbxFormaPgto.setAdapter(arrayAdapterFormaPgto);	
		for (int i = 0; i < cbxFormaPgto.getCount(); i++) {  
            if (listaCodigo.get(i).trim().equals("DIN")) {  
            	cbxFormaPgto.setSelection(i);  
            }  
        } 		
		
		cbxFormaPgto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View v,
					int posicao, long id) {
				codFormaPgto = listaCodigo.get(posicao);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		PedidoDTO pedDTO = venda.util.Global.pedidoGlobalDTO;
		if (cliDTO != null)
			pedDTO.setCodCliente(cliDTO.getCodCliente());
		pedDTO.setCodVendedor(venDTO.getCodigo());
		pedDTO.setDataPedido(txtDataPedido.getText().toString());
		pedDTO.setFormaPgto(codFormaPgto);
		pedDTO.setHoraPedidoFim(venda.util.Util.getTime());
		pedDTO.setParcela(Integer.parseInt(txtParcela.getText().toString()));
		pedDTO.setPrazo(Integer.parseInt(txtPrazo.getText().toString()));
		pedDTO.setLatitude(localizacao.getLatitude());
		pedDTO.setLongitude(localizacao.getLongitude());
		pedDTO.setInfAdicional("");
		if (pedDTO.getId() == null || pedDTO.getId() == 0){
			pedDTO.setId(0);
			pedDTO.setBaixado(0);
			pedDTO.setFechado("1");
			pedDTO.setHoraPedido(venda.util.Util.getTime());
		}else{
			 if (pedDTO.getBaixado() == 1)
				 Toast.makeText(getBaseContext(), "Este pedido ja foi baixado e nao pode ser manipulado", Toast.LENGTH_SHORT).show();
			 else
				 pedBRL.Update(pedDTO);
		}
		venda.util.Global.pedidoGlobalDTO = pedDTO;
//		@SuppressWarnings("unused")
//		HttpAsyncPOST httpAsyncPost = new HttpAsyncPOST();
//		httpAsyncPost.execute();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		PedidoDTO pedDTO = venda.util.Global.pedidoGlobalDTO;
		if (pedDTO.getId() != null){
			CarregaComboFormaPgto();
			ClienteBRL cliBRL = new ClienteBRL(getBaseContext());
			ClienteDTO cliDTO = cliBRL.getByCodCliente(pedDTO.getCodCliente());
			txtNumeroPedido.setText(pedDTO.getId().toString());
			txtDataPedido.setText(pedDTO.getDataPedido());
			txtCliente.setText(cliDTO.getNome());
			txtPrazo.setText(pedDTO.getPrazo().toString());
			txtParcela.setText(pedDTO.getParcela().toString());
			for (int i = 0; i < cbxFormaPgto.getCount(); i++) {  
	            if (listaCodigo.get(i).equals(pedDTO.getFormaPgto())) {  
	            	cbxFormaPgto.setSelection(i);  
	            }  
	        }  			
		}
		else
		    LimpaCamposPedido();			

	}
	
	
}*/
