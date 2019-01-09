package vendas.telas;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import venda.util.Global;

public class ClienteTabContainer extends TabActivity {
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		this.setTitle(Global.tituloAplicacao);
		setContentView(R.layout.tabcontainer);
		TabHost host = getTabHost();
		TabHost.TabSpec sec;
		//... Basico
		sec = host.
			newTabSpec("basico").
			setIndicator("Lista").
			setContent(new Intent(this,ClienteLista.class))
		;
		host.addTab(sec);
		//... Complemento
		sec = host.
			newTabSpec("complemento").
			setIndicator("Complemento").
			setContent(new Intent(this,ClienteComplemento.class))
		;
		host.addTab(sec);
		//... Contas a receber
		sec = host.
			newTabSpec("creceber").
			setIndicator("C. Receber").
			setContent(new Intent(this,ClienteContasReceber.class))
		;
		host.addTab(sec);		
		//======
		host.setCurrentTabByTag("basico");
	}
}
