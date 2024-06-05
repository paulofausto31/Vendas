package vendas.telas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import persistencia.brl.CaminhoFTPBRL;
import venda.util.Global;

public class ConfiguracaoGeral extends Fragment {

	CaminhoFTPBRL ftpBRL;
	private EditText txtCaminhoFtp;
	private EditText txtCodigoEmpresa;
	private EditText txtEmailEmpresa;
	private EditText txtPortaFTP;
	private Button btnSalvar;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.configuracao_geral, container, false);


		ftpBRL = new CaminhoFTPBRL(getContext(), requireActivity());
		Global.caminhoFTPDTO = ftpBRL.getByEmpresa();

		txtCaminhoFtp = view.findViewById(R.id.txtCaminhoFtp);
		txtCodigoEmpresa = view.findViewById(R.id.txtCodigoEmpresa);
		txtEmailEmpresa = view.findViewById(R.id.txtEmailEmpresa);
		txtPortaFTP = view.findViewById(R.id.txtPortaFtp);
		btnSalvar = view.findViewById(R.id.btnSalvar);

		btnSalvar.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) { btnSalvar_click(); }

			private void btnSalvar_click() {
				MontaEntidade();
				if (ftpBRL.ValidaCaminhoFTP(1,Global.caminhoFTPDTO))
					ftpBRL.SalvaCaminhoFTP(Global.caminhoFTPDTO);
			}
		});

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

		txtCaminhoFtp.setText(Global.caminhoFTPDTO.getCaminho());
		if (Global.caminhoFTPDTO.getCodEmpresa() != null && Global.caminhoFTPDTO.getCodEmpresa().length() == 15)
			txtCodigoEmpresa.setText(Global.caminhoFTPDTO.getCodEmpresa().substring(1,15));
		else
			txtCodigoEmpresa.setText(Global.caminhoFTPDTO.getCodEmpresa());
		txtPortaFTP.setText(Global.caminhoFTPDTO.getPortaFTP());
		txtEmailEmpresa.setText(Global.caminhoFTPDTO.getEmailEmpresa());

	}

	@Override
	public void onPause() {
		super.onPause();

		MontaEntidade();
	}

	public void MontaEntidade() {
		Global.caminhoFTPDTO.setCaminho(txtCaminhoFtp.getText().toString());
		Global.caminhoFTPDTO.setCodEmpresa("1".concat(txtCodigoEmpresa.getText().toString()));
		Global.caminhoFTPDTO.setEmailEmpresa(txtEmailEmpresa.getText().toString());
		Global.caminhoFTPDTO.setPortaFTP(txtPortaFTP.getText().toString());
	}
}

