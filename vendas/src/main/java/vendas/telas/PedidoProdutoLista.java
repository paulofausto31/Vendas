package vendas.telas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import persistencia.adapters.RVPedidoProdutoAdapter;
import persistencia.adapters.RVProdutoAdapter;
import persistencia.brl.ProdutoBRL;
import persistencia.dto.ProdutoDTO;
import venda.util.Global;

public class PedidoProdutoLista extends AppCompatActivity {
    private RVPedidoProdutoAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProdutoBRL brl = new ProdutoBRL(getBaseContext());
        Intent it = getIntent();
        Boolean param = it.getBooleanExtra("paramProduto", true);

        if (param) {
            List<ProdutoDTO> _list = brl.getByDescricao(Global.prodPesquisa);
            adapter = new RVPedidoProdutoAdapter(_list, this::showPopupMenu);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ProdutoBRL brl = new ProdutoBRL(getBaseContext());
        if (venda.util.Global.prodPesquisa == null){
            List<ProdutoDTO> _list = brl.getAllOrdenado();

            adapter = new RVPedidoProdutoAdapter(_list, this::showPopupMenu);
            recyclerView.setAdapter(adapter);
        }else{
            List<ProdutoDTO> _list = brl.getByDescricao(Global.prodPesquisa);

            adapter = new RVPedidoProdutoAdapter(_list, this::showPopupMenu);
            recyclerView.setAdapter(adapter);
        }
    }

    private void showPopupMenu(ProdutoDTO item) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("itemId", item.getCodProduto().toString());
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_produto, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_pesquisa_produto:
                AlertDialog.Builder pesquisa = new AlertDialog.Builder(this);

                pesquisa.setTitle("Pesquisa produto");
                pesquisa.setMessage("Informe o nome do produto");
                pesquisa.setCancelable(false);

                final EditText input = new EditText(this);
                pesquisa.setView(input);

                pesquisa.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = input.getText().toString();
                        if (value.length() < 3)
                            Toast.makeText(getBaseContext(), "A pesquisa deve conter no mínimo 3 caracteres", Toast.LENGTH_LONG).show();
                        else{
                            venda.util.Global.prodPesquisa = value;
                            ProdutoBRL brl = new ProdutoBRL(getBaseContext());
                            List<ProdutoDTO> _list = brl.getByDescricao(value);
                            AcaoPesquisa(_list);

                        }
                    }
                });

                pesquisa.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });

                AlertDialog alertDialog = pesquisa.create();

                alertDialog.show();
                return true;
            case R.id.menu_mostrar_todos:
                ProdutoBRL brl = new ProdutoBRL(getBaseContext());
                List<ProdutoDTO> _list = brl.getAllOrdenado();

                adapter = new RVPedidoProdutoAdapter(_list, this::showPopupMenu);
                recyclerView.setAdapter(adapter);
                return true;
            case R.id.menu_voltar_produto:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void AcaoPesquisa(List<ProdutoDTO> list) {
        adapter = new RVPedidoProdutoAdapter(list, this::showPopupMenu);
        recyclerView.setAdapter(adapter);
    }
}
