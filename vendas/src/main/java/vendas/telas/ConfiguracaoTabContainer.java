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

public class ConfiguracaoTabContainer extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtabcontainer);

		ViewPager2 viewPager = findViewById(R.id.view_pager);
		TabLayout tabLayout = findViewById(R.id.tab_layout);

		viewPager.setAdapter(new TabAdapter(this));

		new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
			if (position == 0) {
				tab.setText("Local");
			} else if (position == 1) {
				tab.setText("Remoto");
			} else {
				tab.setText("Geral");
			}
		}).attach();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_configuracao, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_voltar_configuracao:
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
				return new ConfiguracaoLocal();
			} else if (position == 1) {
				return new ConfiguracaoRemoto();
			} else {
				return new ConfiguracaoGeral();
			}
		}

		@Override
		public int getItemCount() {
			return 3;
		}
	}
}

