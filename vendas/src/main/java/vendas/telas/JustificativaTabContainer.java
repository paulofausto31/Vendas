package vendas.telas;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import persistencia.brl.ClienteBRL;
import persistencia.dto.ClienteDTO;
import venda.util.Global;

public class JustificativaTabContainer extends TabActivity {
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		setContentView(R.layout.tabcontainer);
		this.setTitle(Global.tituloAplicacao);

		Intent it = getIntent();
	    ClienteBRL cliBRL = new ClienteBRL(getBaseContext());
	    ClienteDTO cliDTO = new ClienteDTO();
	    int idCliente = it.getIntExtra("idCliente", 1);
	    cliDTO = cliBRL.getById(idCliente);
		
		TabHost host = getTabHost();
		TabHost.TabSpec sec;
		//... Básico
		sec = host.
			newTabSpec("Básico").
			setIndicator("Justificativa").
			setContent(new Intent(this,JustificativaBasico.class).putExtra("idCliente", idCliente))
		;
		host.addTab(sec);	
		//... Hist�rico
		sec = host.
			newTabSpec("historico").
			setIndicator("Histórico").
			setContent(new Intent(this,JustificativaHistorico.class).putExtra("codCliente", cliDTO.getCodCliente()))
		;
		host.addTab(sec);			
		//======
		host.setCurrentTabByTag("basico");
	}
}
