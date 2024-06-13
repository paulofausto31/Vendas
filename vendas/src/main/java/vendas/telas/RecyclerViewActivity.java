package vendas.telas;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

import persistencia.adapters.MyAdapter;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);

        recyclerView = findViewById(R.id.recycler_view);

        // Use um LayoutManager para o RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Dados de exemplo
        List<String> data = Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4", "Item 5");

        // Configurar o Adapter com os dados
        adapter = new MyAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cliente, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ordenar_nome:
                //setListAdapter(new ClienteAdapter(getBaseContext(), brl.getRotaDiaOrdenado("nome")));
                return true;
            case R.id.menu_ordenar_seqvisita:
                //setListAdapter(new ClienteAdapter(getBaseContext(), brl.getRotaDiaOrdenado("seqVisita")));
                return true;
            case R.id.menu_pesquisar_fantasia:
                //pesquisa(1);
                return true;
            case R.id.menu_pesquisa_rsocial:
                //pesquisa(2);
                return true;
            case R.id.menu_mostrar_todos:
                //setListAdapter(new ClienteAdapter(getBaseContext(), brl.getTodosOrdenado("nome")));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
