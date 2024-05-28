package vendas.telas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import persistencia.brl.CaminhoFTPBRL;
import venda.util.Global;

public class ConfiguracaoGeral extends Activity {

	CaminhoFTPBRL ftpBRL;
	private EditText txtCaminhoFtp;
	private EditText txtCodigoEmpresa;
	private EditText txtEmailEmpresa;
	private EditText txtPortaFTP;
	private Button btnSalvar;
	private Button btnComunicacao;
	private static final int MENU_SALVAR = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracao_geral);
		this.setTitle(Global.tituloAplicacao);

		ftpBRL = new CaminhoFTPBRL(getBaseContext(), getParent());
		txtCaminhoFtp = (EditText) findViewById(R.id.txtCaminhoFtp);
		txtCodigoEmpresa = (EditText) findViewById(R.id.txtCodigoEmpresa);
        txtEmailEmpresa = (EditText) findViewById(R.id.txtEmailEmpresa);
		txtPortaFTP = (EditText) findViewById(R.id.txtPortaFtp);
		btnSalvar = (Button) findViewById(R.id.btnSalvar);
		btnComunicacao = (Button) findViewById(R.id.btnConfiguracaoPrincipal);

		btnComunicacao.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				AbrePrincipal();
			}
		});

		btnSalvar.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) { btnSalvar_click(); }

			private void btnSalvar_click() {
				 MontaEntidade();
				 if (ftpBRL.ValidaCaminhoFTP(1,Global.caminhoFTPDTO))
					 ftpBRL.SalvaCaminhoFTP(Global.caminhoFTPDTO);
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
		
		txtCaminhoFtp.setText(Global.caminhoFTPDTO.getCaminho());
		if (Global.caminhoFTPDTO.getCodEmpresa() != null && Global.caminhoFTPDTO.getCodEmpresa().length() == 15)
			txtCodigoEmpresa.setText(Global.caminhoFTPDTO.getCodEmpresa().substring(1,15));
		else
			txtCodigoEmpresa.setText(Global.caminhoFTPDTO.getCodEmpresa());
		txtPortaFTP.setText(Global.caminhoFTPDTO.getPortaFTP());
		txtEmailEmpresa.setText(Global.caminhoFTPDTO.getEmailEmpresa());
		
	    //MontaEntidade();
	    //ftpBRL.PostCaminhoFTP(Global.caminhoFTPDTO);
	}

	@Override
	protected void onPause() {
		super.onPause();

		MontaEntidade();
	}
	
	 private void MontaEntidade() {
		Global.caminhoFTPDTO.setCaminho(txtCaminhoFtp.getText().toString());
		Global.caminhoFTPDTO.setCodEmpresa("1".concat(txtCodigoEmpresa.getText().toString()));
		Global.caminhoFTPDTO.setEmailEmpresa(txtEmailEmpresa.getText().toString());
		Global.caminhoFTPDTO.setPortaFTP(txtPortaFTP.getText().toString());
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
			 CaminhoFTPBRL ftpBRL = new CaminhoFTPBRL(getBaseContext(), getParent());
			 MontaEntidade();
			 if (ftpBRL.ValidaCaminhoFTP(1, Global.caminhoFTPDTO))
				 ftpBRL.SalvaCaminhoFTP(Global.caminhoFTPDTO);
			 
			 return true;
		 }
		 return false;
	 }


}
