package vendas.telas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import persistencia.adapters.RVClienteAdapter;
import persistencia.brl.ClienteBRL;
import persistencia.brl.ContaReceberBRL;
import persistencia.dto.ClienteDTO;

public class RVClienteLista extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RVClienteAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ClienteBRL brl;
    ContaReceberBRL crbrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_clientes);

        recyclerView = findViewById(R.id.recycler_clientes);

        // Use um LayoutManager para o RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Dados de exemplo
        brl = new ClienteBRL(getBaseContext());
        crbrl = new ContaReceberBRL(getBaseContext());
        List<ClienteDTO> _list = brl.getRotaDiaOrdenado("nome");

        // Configurar o Adapter com os dados
        adapter = new RVClienteAdapter(getBaseContext(), _list, new RVClienteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Ação para quando um item é clicado
                ClienteDTO item = _list.get(position);
                AcaoDoClick(item);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void AcaoDoClick(ClienteDTO dto){
        Double totReceber = crbrl.getTotalReceberCliente(dto.getCodCliente());

        String longMessage = "Codigo: " + dto.getCodCliente().toString() + "\n"
                + "R.Social: " + dto.getRazaoSocial() + "\n"
                + "Fantasia: " + dto.getNome() + "\n"
                + "CPF/CNPJ: " + dto.getCpfCnpj() + "\n"
                + "Endereço: " + dto.getEndereco() + "\n"
                + "Telefone: " + dto.getTelefone() + "\n"
                + "Limite Crédito: " + dto.getLimiteCredito() + "\n"
                + "Saldo disponível: " + (dto.getLimiteCredito() - totReceber)  + "\n"
                + "C.Receber em aberto: " + totReceber + "\n"
                + "Prazo: " + dto.getPrazo().toString();
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
        inflater.inflate(R.menu.menu_cliente, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        List<ClienteDTO> _list;
        switch (item.getItemId()) {
            case R.id.menu_ordenar_nome:
                _list = brl.getRotaDiaOrdenado("nome");
                adapter = new RVClienteAdapter(getBaseContext(), _list, new RVClienteAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        // Ação para quando um item é clicado
                        ClienteDTO item = _list.get(position);
                        AcaoDoClick(item);
                    }
                });
                recyclerView.setAdapter(adapter);
                return true;
            case R.id.menu_ordenar_seqvisita:
                _list = brl.getRotaDiaOrdenado("seqVisita");
                adapter = new RVClienteAdapter(getBaseContext(), _list, new RVClienteAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        // Ação para quando um item é clicado
                        ClienteDTO item = _list.get(position);
                        AcaoDoClick(item);
                    }
                });
                recyclerView.setAdapter(adapter);
                return true;
            case R.id.menu_pesquisar_fantasia:
                pesquisa(1);
                return true;
            case R.id.menu_pesquisa_rsocial:
                pesquisa(2);
                return true;
            case R.id.menu_mostrar_todos:
                _list = brl.getTodosOrdenado("nome");
                adapter = new RVClienteAdapter(getBaseContext(), _list, new RVClienteAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        // Ação para quando um item é clicado
                        ClienteDTO item = _list.get(position);
                        AcaoDoClick(item);
                    }
                });
                recyclerView.setAdapter(adapter);
                return true;
            case R.id.menu_principal:
                Intent principal = new Intent(this, Principal.class);
                startActivity(principal);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void pesquisa(final int tipoPesquisa){
        AlertDialog.Builder pesquisa = new AlertDialog.Builder(this);

        pesquisa.setTitle("Pesquisa cliente");
        pesquisa.setMessage("Informe o nome do cliente");
        pesquisa.setCancelable(false);

        final EditText input = new EditText(this);
        pesquisa.setView(input);

        pesquisa.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = input.getText().toString();
                ClienteBRL cliBRL = new ClienteBRL(getBaseContext());
                List<ClienteDTO> _list;

                if (tipoPesquisa == 1){
                    _list = cliBRL.getByNome(value);
                    adapter = new RVClienteAdapter(getBaseContext(), _list, new RVClienteAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            // Ação para quando um item é clicado
                            ClienteDTO item = _list.get(position);
                            AcaoDoClick(item);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
                else{
                    _list = cliBRL.getByRazaoSocial(value);
                    adapter = new RVClienteAdapter(getBaseContext(), _list, new RVClienteAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            // Ação para quando um item é clicado
                            ClienteDTO item = _list.get(position);
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

    }
}
