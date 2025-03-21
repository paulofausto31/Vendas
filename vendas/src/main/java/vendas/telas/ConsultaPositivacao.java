package vendas.telas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import persistencia.adapters.RVUtilitarioPedidoAdapter;
import persistencia.brl.ClienteBRL;
import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.PedidoBRL;
import persistencia.dto.PedidoDTO;
import venda.util.Global;
import venda.util.Util;

public class ConsultaPositivacao extends Fragment {

	private TextView txtTotalClientes;
	private TextView txtPositvacaoClientes;
	private TextView txtPercentualPositivacao;
	private TextView txtTotalVendas;
	private Button btnPrincipal;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_consulta_positivacao, container, false);

		txtTotalClientes = view.findViewById(R.id.lblConsultaTotalClientes);
		txtPositvacaoClientes = view.findViewById(R.id.lblConsultaClientesPositivados);
		txtPercentualPositivacao = view.findViewById(R.id.lblConsultaPercentagemPositivacao);
		txtTotalVendas = view.findViewById(R.id.lblConsultaTotalVendas);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		ClienteBRL cliBRL = new ClienteBRL(getContext());
		Double totalClientes = Double.parseDouble(cliBRL.getTotalClientes().toString());
		txtTotalClientes.setText(totalClientes.toString());
		//...
		PedidoBRL pedBRL = new PedidoBRL(getContext());
		Double totalPositivacao = Double.parseDouble(pedBRL.getTotalPositivacao().toString());
		txtPositvacaoClientes.setText(totalPositivacao.toString());
		//...
		Double percentualPositivacao = ((totalPositivacao / totalClientes) * 100);
		DecimalFormat formatador = new DecimalFormat("##,###0.00");
		String totalFormatado = formatador.format(percentualPositivacao);
		totalFormatado = totalFormatado.replace('.', ',');
		txtPercentualPositivacao.setText(totalFormatado + " %");
		//...
		ItenPedidoBRL itpBRL = new ItenPedidoBRL(getContext());
		Double totalVendas = itpBRL.getSumTotalAberto();
		totalFormatado = formatador.format(totalVendas);
		totalFormatado = totalFormatado.replace('.', ',');
		txtTotalVendas.setText(totalFormatado);
	}
}


		/*
		extends Activity {
	
	private TextView txtTotalClientes;
	private TextView txtPositvacaoClientes;
	private TextView txtPercentualPositivacao;
	private TextView txtTotalVendas;
	private Button btnPrincipal;
		
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		setContentView(R.layout.consulta_positivacao);
		this.setTitle(Global.tituloAplicacao);

		//...
		txtTotalClientes = (TextView) findViewById(R.id.lblConsultaTotalClientes);
		txtPositvacaoClientes = (TextView) findViewById(R.id.lblConsultaClientesPositivados);
		txtPercentualPositivacao = (TextView) findViewById(R.id.lblConsultaPercentagemPositivacao);
		txtTotalVendas = (TextView) findViewById(R.id.lblConsultaTotalVendas);
		btnPrincipal = (Button) findViewById(R.id.btnConsultaPrincipal);

		btnPrincipal.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				AbrePrincipal();
			}
		});
	}

	private void AbrePrincipal() {
		Intent principal = new Intent(this, Principal.class);
		startActivity(principal);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ClienteBRL cliBRL = new ClienteBRL(getBaseContext());
		Double totalClientes = Double.parseDouble(cliBRL.getTotalClientes().toString());
		txtTotalClientes.setText(totalClientes.toString());
		//...
		PedidoBRL pedBRL = new PedidoBRL(getBaseContext());
		Double totalPositivacao = Double.parseDouble(pedBRL.getTotalPositivacao().toString());
		txtPositvacaoClientes.setText(totalPositivacao.toString());
		//...
		Double percentualPositivacao = ((totalPositivacao / totalClientes) * 100); 
		DecimalFormat formatador = new DecimalFormat("##,###0.00");  
	    String totalFormatado = formatador.format(percentualPositivacao);  
	    totalFormatado = totalFormatado.replace('.', ','); 		
		txtPercentualPositivacao.setText(totalFormatado + " %");
		//...
		ItenPedidoBRL itpBRL = new ItenPedidoBRL(getBaseContext());
		Double totalVendas = itpBRL.getSumTotalAberto(); 
	    totalFormatado = formatador.format(totalVendas);  
	    totalFormatado = totalFormatado.replace('.', ','); 		
	    txtTotalVendas.setText(totalFormatado);
	}
}*/
