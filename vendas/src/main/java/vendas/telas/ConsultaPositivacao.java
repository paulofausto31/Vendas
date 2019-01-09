package vendas.telas;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

import persistencia.brl.ClienteBRL;
import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.PedidoBRL;
import venda.util.Global;

public class ConsultaPositivacao extends Activity {
	
	private TextView txtTotalClientes;
	private TextView txtPositvacaoClientes;
	private TextView txtPercentualPositivacao;
	private TextView txtTotalVendas;
		
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
}
