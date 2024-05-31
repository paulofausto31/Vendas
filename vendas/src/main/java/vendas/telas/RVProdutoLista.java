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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import persistencia.adapters.RVProdutoAdapter;
import persistencia.brl.PrecoBRL;
import persistencia.brl.ProdutoBRL;
import persistencia.dto.PrecoDTO;
import persistencia.dto.ProdutoDTO;
import venda.util.Global;

public class RVProdutoLista extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RVProdutoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ProdutoBRL brl;
    PrecoBRL preBRL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_produto);

        recyclerView = findViewById(R.id.recycler_produtos);

        // Use um LayoutManager para o RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Dados de exemplo
        brl = new ProdutoBRL(getBaseContext());
        preBRL = new PrecoBRL(getBaseContext());

        Intent it = getIntent();
        Boolean param = it.getBooleanExtra("paramProduto", true);

        if (param){
            List<ProdutoDTO> _list = brl.getByDescricao(Global.prodPesquisa);

            adapter = new RVProdutoAdapter(getBaseContext(), _list, new RVProdutoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    // Ação para quando um item é clicado
                    ProdutoDTO item = _list.get(position);
                    AcaoDoClick(item);
                }
            });
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

            adapter = new RVProdutoAdapter(getBaseContext(), _list, new RVProdutoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    // Ação para quando um item é clicado
                    ProdutoDTO item = _list.get(position);
                    AcaoDoClick(item);
                }
            });
            recyclerView.setAdapter(adapter);
        }else{
            List<ProdutoDTO> _list = brl.getByDescricao(Global.prodPesquisa);

            adapter = new RVProdutoAdapter(getBaseContext(), _list, new RVProdutoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    // Ação para quando um item é clicado
                    ProdutoDTO item = _list.get(position);
                    AcaoDoClick(item);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }

    private void AcaoDoClick(ProdutoDTO dto){
        List<PrecoDTO> listaPreco = preBRL.getByProduto(dto.getCodProduto());

        String longMessage = "Codigo: " + dto.getCodProduto().toString() + "\n"
                + "Descrição: " + dto.getDescricao() + "\n"
                + "Unidade: " + dto.getUnidade() + "\n"
                + "Estoque: " + dto.getEstoque().toString() + "\n"
                + "Preço: " + listaPreco.get(0).getPreco().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(longMessage)
                .setTitle("Mensagem")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Ação ao clicar no botão OK
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
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

                            adapter = new RVProdutoAdapter(getBaseContext(), _list, new RVProdutoAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    // Ação para quando um item é clicado
                                    ProdutoDTO item = _list.get(position);
                                    AcaoDoClick(item);
                                }
                            });
                            recyclerView.setAdapter(adapter);
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
                List<ProdutoDTO> _list = brl.getAllOrdenado();

                adapter = new RVProdutoAdapter(getBaseContext(), _list, new RVProdutoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        // Ação para quando um item é clicado
                        ProdutoDTO item = _list.get(position);
                        AcaoDoClick(item);
                    }
                });
                recyclerView.setAdapter(adapter);
                return true;
            case R.id.menu_voltar_produto:
                Intent principal = new Intent(this, Principal.class);
                startActivity(principal);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
