package vendas.telas;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import persistencia.brl.CaminhoFTPBRL;
import venda.util.Global;

public class ConfiguracaoTabContainer extends TabActivity {
	
	private static final int MENU_SALVAR = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabcontainer);
		this.setTitle(Global.tituloAplicacao);

		TabHost host = getTabHost();
		TabHost.TabSpec sec;
		//... Local
		sec = host.
			newTabSpec("local").
			setIndicator("Local").
			setContent(new Intent(this,ConfiguracaoLocal.class))
		;
		host.addTab(sec);	
		//... Remoto
		sec = host.
			newTabSpec("remoto").
			setIndicator("Remoto").
			setContent(new Intent(this,ConfiguracaoRemoto.class))
		;
		host.addTab(sec);
		//... Caminho
		sec = host.
			newTabSpec("geral").
			setIndicator("geral").
			setContent(new Intent(this,ConfiguracaoGeral.class))
		;
		host.addTab(sec);	
		//======
		host.setCurrentTabByTag("local");		
	}

	 @Override
	 public boolean onCreateOptionsMenu(Menu menu){
		// Menu
		MenuItem MenuSalvar = menu.add(0, MENU_SALVAR, 0, "Salvar");

		return true; 
	 }

	 @Override
	 public boolean onMenuItemSelected(int featureID, MenuItem menu){
		
		 switch (menu.getItemId()){
		 
		 case MENU_SALVAR: // mensagem 
			 CaminhoFTPBRL ftpBRL = new CaminhoFTPBRL(getBaseContext(), getCurrentActivity());
			 if (ftpBRL.ValidaCaminhoFTP(2, Global.caminhoFTPDTO)){
				 //ftpBRL.PostCaminhoFTP(Global.caminhoFTPDTO);
				TabHost host = getTabHost();
				int ind = host.getCurrentTab();
				if (ind == 0)
					host.setCurrentTab(1);
				else if (ind == 1)
					host.setCurrentTab(2);
				else if (ind == 2)
					host.setCurrentTab(0);
				// finish();
			 }
			 
			 return true;
		 }
		 return false;
	 }

}
