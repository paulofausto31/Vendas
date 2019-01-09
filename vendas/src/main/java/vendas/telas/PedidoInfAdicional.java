package vendas.telas;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import persistencia.brl.PedidoBRL;
import persistencia.dto.PedidoDTO;
import venda.util.Global;

public class PedidoInfAdicional extends Activity {
	
	//... Componentes da Tela
	EditText txtInfAdicional;
	PedidoBRL pedBRL; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pedido_inf_adicional);
		this.setTitle(Global.tituloAplicacao);

		pedBRL = new PedidoBRL(getBaseContext());

		txtInfAdicional = (EditText)findViewById(R.id.txtInfAdicional);

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		PedidoDTO pedDTO = venda.util.Global.pedidoGlobalDTO;
		if (pedDTO.getId() != null){
			txtInfAdicional.setText(pedDTO.getInfAdicional());
		}
		else
		    txtInfAdicional.setText("");

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		PedidoDTO pedDTO = venda.util.Global.pedidoGlobalDTO;
		pedDTO.setInfAdicional(txtInfAdicional.getText().toString());
		venda.util.Global.pedidoGlobalDTO = pedDTO;	
        if (pedDTO.getBaixado() != 1)
			pedBRL.Update(pedDTO);
		
	}


}
