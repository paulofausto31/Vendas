package vendas.telas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import persistencia.adapters.RVClienteAdapter;
import persistencia.brl.ClienteBRL;
import persistencia.brl.ContaReceberBRL;
import persistencia.brl.RotaBRL;
import persistencia.dto.ClienteDTO;
import venda.util.Global;

public class NavClienteLista  extends Fragment {
    private RecyclerView recyclerView;
    private RVClienteAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ClienteBRL brl;
    RotaBRL rotBRL;
    ContaReceberBRL crbrl;

    public NavClienteLista() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.recyclerview_clientes, container, false);

        recyclerView = view.findViewById(R.id.recycler_clientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Habilita o menu no fragmento
    }

    @Override
    public void onResume() {
        super.onResume();
        // Dados de exemplo
        brl = new ClienteBRL(getContext());
        crbrl = new ContaReceberBRL(getContext());
        List<ClienteDTO> _list;

        if (Global.codRota == null) {
            _list = brl.getRotaDiaOrdenado("nome");
        }else {
            _list = brl.getPorRotaOrdenado(Global.codRota, "nome");
        }

        // Configurar o Adapter com os dados
        adapter = new RVClienteAdapter(getContext(), _list, new RVClienteAdapter.OnItemClickListener() {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_cliente, menu); // Infla o menu
    }
    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_cliente, menu);

            return true;
        }
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        List<ClienteDTO> _list;
        switch (item.getItemId()) {
            case R.id.menu_rota:
                ComboRotas();
                return true;
            case R.id.menu_ordenar_nome:
                _list = brl.getRotaDiaOrdenado("nome");
                adapter = new RVClienteAdapter(getContext(), _list, new RVClienteAdapter.OnItemClickListener() {
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
                adapter = new RVClienteAdapter(getContext(), _list, new RVClienteAdapter.OnItemClickListener() {
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
                adapter = new RVClienteAdapter(getContext(), _list, new RVClienteAdapter.OnItemClickListener() {
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
                Intent principal = new Intent(getContext(), Principal.class);
                startActivity(principal);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void ComboRotas(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Selecione a rota");

        // Inflate the custom layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_cliente_rota, null);
        builder.setView(dialogView);

        // Initialize the spinner
        Spinner spinner = dialogView.findViewById(R.id.cbxRota);

        rotBRL = new RotaBRL(getContext());
        List<String> rotas = rotBRL.getComboRota();

        ArrayAdapter<String> adapterRotas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, rotas);
        adapterRotas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterRotas);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle spinner selection
                String rota = parent.getItemAtPosition(position).toString();
                // Do something with the selected item
                if (position > 0){
                    Global.codRota = rota;
                    List<ClienteDTO> lstClientes = brl.getPorRotaOrdenado(rota, "nome");
                    adapter = new RVClienteAdapter(getContext(), lstClientes, new RVClienteAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            // Ação para quando um item é clicado
                            ClienteDTO item = lstClientes.get(position);
                            AcaoDoClick(item);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no item selected
            }
        });

        // Add OK and Cancel buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Handle OK button press
            String selectedItem = spinner.getSelectedItem().toString();
            // Do something with the selected item
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void pesquisa(final int tipoPesquisa){
        AlertDialog.Builder pesquisa = new AlertDialog.Builder(getContext());

        pesquisa.setTitle("Pesquisa cliente");
        pesquisa.setMessage("Informe o nome do cliente");
        pesquisa.setCancelable(false);

        final EditText input = new EditText(getContext());
        pesquisa.setView(input);

        pesquisa.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = input.getText().toString();
                ClienteBRL cliBRL = new ClienteBRL(getContext());
                List<ClienteDTO> _list;

                if (tipoPesquisa == 1){
                    _list = cliBRL.getByNome(value);
                    adapter = new RVClienteAdapter(getContext(), _list, new RVClienteAdapter.OnItemClickListener() {
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
                    adapter = new RVClienteAdapter(getContext(), _list, new RVClienteAdapter.OnItemClickListener() {
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
