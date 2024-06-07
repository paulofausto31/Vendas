package vendas.telas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import persistencia.brl.ClienteBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.PedidoDTO;
import venda.util.Global;

public class PedidoTabContainer extends AppCompatActivity {

	public static int idCliente;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtabcontainer);

		ViewPager2 viewPager = findViewById(R.id.view_pager);
		TabLayout tabLayout = findViewById(R.id.tab_layout);

		viewPager.setAdapter(new TabAdapter(this));

		ClienteBRL cliBRL;
		ClienteDTO cliDTO;

		Intent it = getIntent();
		idCliente = it.getIntExtra("idCliente", 0);
		if (idCliente > 0){
			venda.util.Global.pedidoGlobalDTO = new PedidoDTO();

		}else{
			cliBRL = new ClienteBRL(getBaseContext());
			cliDTO = cliBRL.getByCodCliente(venda.util.Global.pedidoGlobalDTO.getCodCliente());
			idCliente = cliDTO.getId();
		}
		venda.util.Global.itemPedidoGlobalDTO = null;
		venda.util.Global.prodPesquisa = null;
		venda.util.Global.descontoAcrescimo = null;

		new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
			if (position == 0) {
				tab.setText("Pedido");
			} else if (position == 1) {
				tab.setText("Incluir Item");
			} else if (position == 2) {
				tab.setText("Itens");
			} else if (position == 3) {
				tab.setText("Inf. Adicional");
			} else {
				tab.setText("Histórico");
			}
		}).attach();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_pedido, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_voltar_pedido:
				Intent cliente = new Intent(this, RVClienteLista.class);
				startActivity(cliente);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private static class TabAdapter extends FragmentStateAdapter {


		public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
			super(fragmentActivity);
		}

		@NonNull
		@Override
		public Fragment createFragment(int position) {
			Global.idCliente = PedidoTabContainer.idCliente;
			if (position == 0) {
				//return PedidoBasico.newInstance(idCliente);
				return new PedidoBasico();
			} else if (position == 1) {
				return new PedidoItemNovo();
			} else if (position == 2) {
				return new PedidoItens();
			} else if (position == 3) {
				return new PedidoInfAdicional();
			} else {
				return new PedidoHistorico();
			}
		}

		@Override
		public int getItemCount() {
			return 5;
		}
	}
}



		/*extends TabActivity {
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		setContentView(R.layout.tabcontainer);
		this.setTitle(Global.tituloAplicacao);
		ClienteBRL cliBRL;
		ClienteDTO cliDTO;

		Intent it = getIntent();
	    int idCliente = it.getIntExtra("idCliente", 0);
	    if (idCliente > 0){
			venda.util.Global.pedidoGlobalDTO = new PedidoDTO();

	    }else{
			cliBRL = new ClienteBRL(getBaseContext());
			cliDTO = cliBRL.getByCodCliente(venda.util.Global.pedidoGlobalDTO.getCodCliente());
			idCliente = cliDTO.getId();
		}
		venda.util.Global.itemPedidoGlobalDTO = null;
		venda.util.Global.prodPesquisa = null;
		venda.util.Global.descontoAcrescimo = null;

		TabHost host = getTabHost();
		TabHost.TabSpec sec;
		//... Basico
		sec = host.
			newTabSpec("basico").
			setIndicator("Pedido").
			setContent(new Intent(this,PedidoBasico.class).putExtra("idCliente", idCliente))
		;
		host.addTab(sec);	
		//... Item Novo
		sec = host.
			newTabSpec("itemNovo").
			setIndicator("Incluir Item").
			setContent(new Intent(this,PedidoItemNovo.class))
		;
		host.addTab(sec);		
		//... Itens
		sec = host.
			newTabSpec("itens").
			setIndicator("Itens").
			setContent(new Intent(this,PedidoItens.class))
		;
		host.addTab(sec);		
		//... Pedido Inf. Adicional
		sec = host.
			newTabSpec("infAdicional").
			setIndicator("Inf. Adicional").
			setContent(new Intent(this,PedidoInfAdicional.class))
		;
		host.addTab(sec);		
		//... Itens
		sec = host.
			newTabSpec("historico").
			setIndicator("Histórico").
			setContent(new Intent(this,PedidoHistorico.class))
		;
		host.addTab(sec);		
		//======
		host.setCurrentTabByTag("basico");
	}
}*/
