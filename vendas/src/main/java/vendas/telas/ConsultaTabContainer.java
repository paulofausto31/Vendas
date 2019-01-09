package vendas.telas;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import venda.util.Global;

public class ConsultaTabContainer extends TabActivity {
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		setContentView(R.layout.tabcontainer);
		this.setTitle(Global.tituloAplicacao);

		TabHost host = getTabHost();
		TabHost.TabSpec sec;
		//... Positivacao
		sec = host.
			newTabSpec("positivacao").
			setIndicator("Positivação").
			setContent(new Intent(this,ConsultaPositivacao.class))
		;
		host.addTab(sec);	
		//... Vendas produto
		sec = host.
			newTabSpec("vproduto").
			setIndicator("Vendas produto").
			setContent(new Intent(this,ConsultaVendasProduto.class))
		;
		host.addTab(sec);
		//... Vendas cliente
		sec = host.
			newTabSpec("vcliente").
			setIndicator("Vendas cliente").
			setContent(new Intent(this,ConsultaVendasCliente.class))
		;
		host.addTab(sec);		
		//======
		host.setCurrentTabByTag("positivacao");
	}
}
