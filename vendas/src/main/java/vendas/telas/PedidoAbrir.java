package vendas.telas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import venda.util.Global;

public class PedidoAbrir extends Activity {
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		setContentView(R.layout.pedido_abrir);
		this.setTitle(Global.tituloAplicacao);

		//... btnAbrir
		Button btnAbrir = (Button)findViewById(R.id.btnAbrir);
		btnAbrir.setOnClickListener(new Button.OnClickListener() {			
			@Override
			public void onClick(View v) { btnAbrir_click(); }
		});
	}
	
	private void btnAbrir_click(){
		
	}
}
