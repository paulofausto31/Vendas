package vendas.telas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class NavUtilitarioTabContainer extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newtabcontainer, container, false);

        // Configuração do ViewPager e TabLayout
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        TabAdapter tabAdapter = new TabAdapter(this);
        viewPager.setAdapter(tabAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Enviados");
                    } else {
                        tab.setText("Pendentes");
                    }
                }).attach();

        return view;
    }

    private static class TabAdapter extends FragmentStateAdapter {

        public TabAdapter(@NonNull NavUtilitarioTabContainer fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new UtilitarioPedidoFechado();
            } else {
                return new UtilitarioPedidoPendente();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}