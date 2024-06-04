package vendas.telas;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import venda.util.Global;

public class ConsultaTabContainer extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtabcontainer);

		ViewPager2 viewPager = findViewById(R.id.view_pager);
		TabLayout tabLayout = findViewById(R.id.tab_layout);

		viewPager.setAdapter(new TabAdapter(this));

		new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
			if (position == 0) {
				tab.setText("Positivação");
			} else if (position == 1) {
				tab.setText("Vendas Produto");
			} else {
				tab.setText("Vendas Cliente");
			}
		}).attach();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_consulta, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_voltar_consulta:
				Intent principal = new Intent(this, Principal.class);
				startActivity(principal);
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
			if (position == 0) {
				return new ConsultaPositivacao();
			} else if (position == 1) {
				return new ConsultaVendasProduto();
			} else {
				return new ConsultaVendasCliente();
			}
		}

		@Override
		public int getItemCount() {
			return 3;
		}
	}
}







		/*extends TabActivity {
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		setContentView(R.layout.tabcontainer);
		this.setTitle(Global.tituloAplicacao);

		TabHost host = getTabHost();
		TabHost.TabSpec sec;
		//... Positivacao
		sec = host.
			newTabSpec("positivacao").
			setIndicator("Positivação").
			setContent(new Intent(this,ConsultaPositivacao.class))
		;
		host.addTab(sec);	
		//... Vendas produto
		sec = host.
			newTabSpec("vproduto").
			setIndicator("Vendas produto").
			setContent(new Intent(this,ConsultaVendasProduto.class))
		;
		host.addTab(sec);
		//... Vendas cliente
		sec = host.
			newTabSpec("vcliente").
			setIndicator("Vendas cliente").
			setContent(new Intent(this,ConsultaVendasCliente.class))
		;
		host.addTab(sec);		
		//======
		host.setCurrentTabByTag("positivacao");
	}
}*/
