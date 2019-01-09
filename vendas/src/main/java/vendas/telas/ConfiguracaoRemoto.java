package vendas.telas;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import persistencia.brl.CaminhoFTPBRL;
import venda.util.Global;

public class ConfiguracaoRemoto extends Activity
{
	CaminhoFTPBRL ftpBRL;
	private EditText txtServidorRemoto;
	private EditText txtUsuarioRemoto;
	private EditText txtSenhaRemoto;
	private static final int MENU_SALVAR = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracao_remoto);
		this.setTitle(Global.tituloAplicacao);

		ftpBRL = new CaminhoFTPBRL(getBaseContext(), getParent());
		txtServidorRemoto = (EditText) findViewById(R.id.txtServidorRemoto);
		txtUsuarioRemoto = (EditText) findViewById(R.id.txtUsuarioRemoto);
		txtSenhaRemoto = (EditText) findViewById(R.id.txtSenhaRemoto);

	}

	@Override
	protected void onResume() {
		super.onResume();
		
		txtServidorRemoto.setText(Global.caminhoFTPDTO.getServerRemoto());
		txtUsuarioRemoto.setText(Global.caminhoFTPDTO.getUserRemoto());
		txtSenhaRemoto.setText(Global.caminhoFTPDTO.getPasswordRemoto());
		
	    //MontaEntidade();
	    //ftpBRL.PostCaminhoFTP(Global.caminhoFTPDTO);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		MontaEntidade();
	}
	
	 private void MontaEntidade() {
		Global.caminhoFTPDTO.setServerRemoto(txtServidorRemoto.getText().toString());
		Global.caminhoFTPDTO.setUserRemoto(txtUsuarioRemoto.getText().toString());
		Global.caminhoFTPDTO.setPasswordRemoto(txtSenhaRemoto.getText().toString());
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
			 MontaEntidade();
			 if (ftpBRL.ValidaCaminhoFTP(1, Global.caminhoFTPDTO))
				 ftpBRL.SalvaCaminhoFTP(Global.caminhoFTPDTO);
			 
			 return true;
		 }
		 return false;
	 }

}
