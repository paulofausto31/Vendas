package vendas.telas;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import persistencia.dto.PedidoDTO;
import venda.util.Global;

public class PedidoTabContainer extends TabActivity {
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		setContentView(R.layout.tabcontainer);
		this.setTitle(Global.tituloAplicacao);

		Intent it = getIntent();
	    int idCliente = it.getIntExtra("idCliente", 0);
	    if (idCliente > 0){
			venda.util.Global.pedidoGlobalDTO = new PedidoDTO();
			venda.util.Global.itemPedidoGlobalDTO = null;
			venda.util.Global.prodPesquisa = null;
			venda.util.Global.descontoAcrescimo = null;
	    }


		TabHost host = getTabHost();
		TabHost.TabSpec sec;
		//... Basico
		sec = host.
			newTabSpec("basico").
			setIndicator("Pedido").
			setContent(new Intent(this,PedidoBasico.class).putExtra("idCliente", idCliente))
		;
		host.addTab(sec);	
		//... Item Novo
		sec = host.
			newTabSpec("itemNovo").
			setIndicator("Incluir Item").
			setContent(new Intent(this,PedidoItemNovo.class))
		;
		host.addTab(sec);		
		//... Itens
		sec = host.
			newTabSpec("itens").
			setIndicator("Itens").
			setContent(new Intent(this,PedidoItens.class))
		;
		host.addTab(sec);		
		//... Pedido Inf. Adicional
		sec = host.
			newTabSpec("infAdicional").
			setIndicator("Inf. Adicional").
			setContent(new Intent(this,PedidoInfAdicional.class))
		;
		host.addTab(sec);		
		//... Itens
		sec = host.
			newTabSpec("historico").
			setIndicator("Histórico").
			setContent(new Intent(this,PedidoHistorico.class))
		;
		host.addTab(sec);		
		//======
		host.setCurrentTabByTag("basico");
	}
}
