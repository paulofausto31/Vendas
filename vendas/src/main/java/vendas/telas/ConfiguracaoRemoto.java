package vendas.telas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import persistencia.brl.CaminhoFTPBRL;
import venda.util.Global;

public class ConfiguracaoRemoto extends Fragment {

	CaminhoFTPBRL ftpBRL;
	private EditText txtServidorRemoto;
	private EditText txtUsuarioRemoto;
	private EditText txtSenhaRemoto;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.configuracao_remoto, container, false);


		ftpBRL = new CaminhoFTPBRL(getContext(), requireActivity());
		Global.caminhoFTPDTO = ftpBRL.getByEmpresa();

		txtServidorRemoto = view.findViewById(R.id.txtServidorRemoto);
		txtUsuarioRemoto = view.findViewById(R.id.txtUsuarioRemoto);
		txtSenhaRemoto = view.findViewById(R.id.txtSenhaRemoto);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

		txtServidorRemoto.setText(Global.caminhoFTPDTO.getServerRemoto());
		txtUsuarioRemoto.setText(Global.caminhoFTPDTO.getUserRemoto());
		txtSenhaRemoto.setText(Global.caminhoFTPDTO.getPasswordRemoto());
	}

	@Override
	public void onPause() {
		super.onPause();

		MontaEntidade();
	}

	public void MontaEntidade() {
		Global.caminhoFTPDTO.setServerRemoto(txtServidorRemoto.getText().toString());
		Global.caminhoFTPDTO.setUserRemoto(txtUsuarioRemoto.getText().toString());
		Global.caminhoFTPDTO.setPasswordRemoto(txtSenhaRemoto.getText().toString());
	}
}

