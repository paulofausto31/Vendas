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

public class ConfiguracaoLocal extends Fragment {

    private EditText txtServidorLocal;
    private EditText txtUsuarioLocal;
    private EditText txtSenhaLocal;
    CaminhoFTPBRL ftpBRL;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.configuracao_local, container, false);


        ftpBRL = new CaminhoFTPBRL(getContext(), requireActivity());
        Global.caminhoFTPDTO = ftpBRL.getByEmpresa();

        txtServidorLocal = view.findViewById(R.id.txtServidorLocal);
        txtUsuarioLocal = view.findViewById(R.id.txtUsuarioLocal);
        txtSenhaLocal = view.findViewById(R.id.txtSenhaLocal);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        txtServidorLocal.setText(Global.caminhoFTPDTO.getServerLocal());
        txtUsuarioLocal.setText(Global.caminhoFTPDTO.getUserLocal());
        txtSenhaLocal.setText(Global.caminhoFTPDTO.getPasswordLocal());
    }

    @Override
    public void onPause() {
        super.onPause();

        MontaEntidade();
    }

    public void MontaEntidade() {
        Global.caminhoFTPDTO.setServerLocal(txtServidorLocal.getText().toString());
        Global.caminhoFTPDTO.setUserLocal(txtUsuarioLocal.getText().toString());
        Global.caminhoFTPDTO.setPasswordLocal(txtSenhaLocal.getText().toString());
    }
}

