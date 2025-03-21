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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import persistencia.adapters.RVClienteAdapter;
import persistencia.brl.ClienteBRL;
import persistencia.brl.ContaReceberBRL;
import persistencia.brl.FornecedorBRL;
import persistencia.brl.GrupoBRL;
import persistencia.brl.RotaBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.GrupoDTO;
import persistencia.dto.RotaDTO;
import venda.util.Global;

public class RVClienteLista extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RVClienteAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ClienteBRL brl;
    RotaBRL rotBRL;
    ContaReceberBRL crbrl;


    public class Rota {
        private Integer codigo;
        private String descricao;

        public Rota(){}

        public Rota(Integer codigo, String descricao) {
            this.codigo = codigo;
            this.descricao = descricao;
        }

        public Integer getCodigo() {
            return codigo;
        }

        public String getDescricao() {
            return descricao;
        }

        @Override
        public String toString() {
            // Retorna a descrição para exibição no dropdown
            return descricao;
        }
    }

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
        List<ClienteDTO> _list;

        if (Global.lstClientes == null)
            _list = brl.getRotaDiaOrdenado("nome");
        else
            _list = Global.lstClientes;

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
            case R.id.menu_rota:
                ComboRotas();
                return true;
            case R.id.menu_filtros:
                showFilterDialog();
                return true;
            case R.id.menu_ordenar_seqvisita:
                _list = brl.getRotaDiaOrdenado("seqVisita");
                Global.lstClientes = _list;
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
            case R.id.menu_mostrar_todos:
                Global.lstClientes = null;
                Global.codRota = null;
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
            case R.id.menu_pedidos:
                _list = brl.getClientesPedidos("nome");
                Global.lstClientes = _list;
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

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione o filtro");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_cliente_filtros, null);
        builder.setView(dialogView);

        final Spinner spinnerFilter = dialogView.findViewById(R.id.cbxFiltros);
        final EditText editTextValue = dialogView.findViewById(R.id.txtFiltroClientes);

        // Configurar o Spinner (opcional, já definido no XML com android:entries)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(adapter);

        builder.setTitle("Filter Options")
                .setPositiveButton("OK", (dialog, id) -> {
                    // Pegar os valores do Spinner e EditText
                    String selectedFilter = spinnerFilter.getSelectedItem().toString();
                    String value = editTextValue.getText().toString();
                    // Faça algo com os valores (exemplo: filtrar lista)
                    aplicarFiltros(selectedFilter, value);
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void aplicarFiltros(String filtro, String valor) {
        ClienteBRL cliBRL = new ClienteBRL(getBaseContext());
        List<ClienteDTO> _list;

        if(filtro.equals("Nome Fantasia")){
            _list = cliBRL.getByNome(valor);
            Global.lstClientes = _list;
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
        else if (filtro.equals("Razão Social")){
            _list = cliBRL.getByRazaoSocial(valor);
            Global.lstClientes = _list;
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
        else if (filtro.equals("Codigo")){
            _list = cliBRL.getByCodigo(valor);
            Global.lstClientes = _list;
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
        else if (filtro.equals("CPF/CNPJ")){
            _list = cliBRL.getByCPFCNPJ(valor);
            Global.lstClientes = _list;
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

    private void ComboRotas(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione a rota");

        // Inflate the custom layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_cliente_rota, null);
        builder.setView(dialogView);

        // Initialize the spinner
        Spinner spinner = dialogView.findViewById(R.id.cbxRota);

        rotBRL = new RotaBRL(getBaseContext());
        List<RotaDTO> rotas = rotBRL.getComboRota();

        List<Rota> lstRota = new ArrayList<Rota>();
        lstRota.add(new Rota(-1, "Selecione uma rota ..."));
        for (RotaDTO rotaDTO : rotas) {
            Rota rotaAdd = new Rota(rotaDTO.getCodRota(), rotaDTO.getDescricao());
            lstRota.add(rotaAdd);
        }

        ArrayAdapter<Rota> adapterRotas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstRota);
        adapterRotas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterRotas);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle spinner selection
                Rota rotaSelecionada = (Rota) parent.getItemAtPosition(position);
                Integer codigoSelecionado = rotaSelecionada.getCodigo();
                //String rota = parent.getItemAtPosition(position).toString();
                // Do something with the selected item
                if (position > 0){
                    Global.codRota = rotaSelecionada.getCodigo().toString();
                    List<ClienteDTO> lstClientes = brl.getPorRotaOrdenado(codigoSelecionado.toString(), "nome");
                    Global.lstClientes = lstClientes;
                    adapter = new RVClienteAdapter(getBaseContext(), lstClientes, new RVClienteAdapter.OnItemClickListener() {
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
