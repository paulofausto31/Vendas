package vendas.telas;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import persistencia.brl.PedidoBRL;
import persistencia.dto.PedidoDTO;
import venda.util.Global;

public class PedidoInfAdicional extends Fragment {
	
	//... Componentes da Tela
	EditText txtInfAdicional;
	PedidoBRL pedBRL;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pedido_inf_adicional, container, false);

		pedBRL = new PedidoBRL(getContext());
		txtInfAdicional = view.findViewById(R.id.txtInfAdicional);

		return view;
	}

	/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pedido_inf_adicional);
		this.setTitle(Global.tituloAplicacao);

		pedBRL = new PedidoBRL(getBaseContext());

		txtInfAdicional = (EditText)findViewById(R.id.txtInfAdicional);

	}
	*/
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		PedidoDTO pedDTO = venda.util.Global.pedidoGlobalDTO;
		if (pedDTO.getInfAdicional() == null){
			txtInfAdicional.setText("");
		}
		else
			txtInfAdicional.setText(pedDTO.getInfAdicional());


	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		PedidoDTO pedDTO = venda.util.Global.pedidoGlobalDTO;
		pedDTO.setInfAdicional(txtInfAdicional.getText().toString());
		venda.util.Global.pedidoGlobalDTO = pedDTO;	
        if (pedDTO.getBaixado() != 1)
			pedBRL.Update(pedDTO);
		
	}


}
