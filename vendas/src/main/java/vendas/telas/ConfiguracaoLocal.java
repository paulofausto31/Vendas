package vendas.telas;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import persistencia.brl.CaminhoFTPBRL;
import venda.util.Global;

public class ConfiguracaoLocal extends Activity
{
	CaminhoFTPBRL ftpBRL;
	public EditText txtServidorLocal;
	private EditText txtUsuarioLocal;
	private EditText txtSenhaLocal;
	private static final int MENU_SALVAR = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracao_local);
		this.setTitle(Global.tituloAplicacao);

		ftpBRL = new CaminhoFTPBRL(getBaseContext(), getParent());
		venda.util.Global.caminhoFTPDTO = ftpBRL.getByEmpresa();
		
		txtServidorLocal = (EditText) findViewById(R.id.txtServidorLocal);
		txtUsuarioLocal = (EditText) findViewById(R.id.txtUsuarioLocal);
		txtSenhaLocal = (EditText) findViewById(R.id.txtSenhaLocal);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		txtServidorLocal.setText(Global.caminhoFTPDTO.getServerLocal());
		txtUsuarioLocal.setText(Global.caminhoFTPDTO.getUserLocal());
		txtSenhaLocal.setText(Global.caminhoFTPDTO.getPasswordLocal());
		
	    //MontaEntidade();
	    //ftpBRL.PostCaminhoFTP(Global.caminhoFTPDTO);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		MontaEntidade();
	}
	
	 public void MontaEntidade() {
		Global.caminhoFTPDTO.setServerLocal(txtServidorLocal.getText().toString());
		Global.caminhoFTPDTO.setUserLocal(txtUsuarioLocal.getText().toString());
		Global.caminhoFTPDTO.setPasswordLocal(txtSenhaLocal.getText().toString());		
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
