package vendas.telas;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import java.util.ArrayList;
import java.util.List;

import persistencia.adapters.RVClienteAdapter;
import persistencia.brl.ClienteBRL;
import persistencia.brl.ContaReceberBRL;
import persistencia.brl.RotaBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.RotaDTO;
import venda.util.Global;

public class RVClienteLista extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RVClienteAdapter adapter;
    private ClienteBRL brl;
    private RotaBRL rotBRL;
    private ContaReceberBRL crbrl;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Garante que o tema seja compatível com Material Components
        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_DayNight_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_clientes);

        // Configuração do Cabeçalho (Toolbar)
        configurarToolbar();

        // Inicialização de componentes
        recyclerView = findViewById(R.id.recycler_clientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView = findViewById(R.id.searchViewClientes);

        brl = new ClienteBRL(getBaseContext());
        rotBRL = new RotaBRL(getBaseContext());
        crbrl = new ContaReceberBRL(getBaseContext());

        configurarChips();
        configurarBusca();
    }

    private void configurarToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Lista de Clientes");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }

            // Ação de voltar para a tela principal
            toolbar.setNavigationOnClickListener(v -> finish());
        }
    }

    private void configurarBusca() {
        if (searchView == null) return;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                aplicarFiltros("Nome Fantasia", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 2) {
                    aplicarFiltros("Nome Fantasia", newText);
                } else if (newText.isEmpty()) {
                    atualizarLista();
                }
                return true;
            }
        });
    }

    private void configurarChips() {
        Chip chipRotas = findViewById(R.id.chipRotas);
        Chip chipFiltros = findViewById(R.id.chipFiltros);
        Chip chipSeqVisita = findViewById(R.id.chipSeqVisita);
        Chip chipComPedidos = findViewById(R.id.chipComPedidos);
        Chip chipTodos = findViewById(R.id.chipTodos);

        if (chipRotas != null) chipRotas.setOnClickListener(v -> ComboRotas());
        if (chipFiltros != null) chipFiltros.setOnClickListener(v -> showFilterDialog());

        if (chipSeqVisita != null) {
            chipSeqVisita.setOnClickListener(v -> {
                List<ClienteDTO> list = brl.getRotaDiaOrdenado("seqVisita");
                Global.lstClientes = list;
                configurarAdapter(list);
            });
        }

        if (chipComPedidos != null) {
            chipComPedidos.setOnClickListener(v -> {
                List<ClienteDTO> list = brl.getClientesPedidos("nome");
                Global.lstClientes = list;
                configurarAdapter(list);
            });
        }

        if (chipTodos != null) {
            chipTodos.setOnClickListener(v -> {
                Global.lstClientes = null;
                Global.codRota = null;
                configurarAdapter(brl.getTodosOrdenado("nome"));
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }

    private void atualizarLista() {
        List<ClienteDTO> list = (Global.lstClientes == null) ?
                brl.getRotaDiaOrdenado("nome") : Global.lstClientes;
        configurarAdapter(list);
    }

    private void configurarAdapter(List<ClienteDTO> novaLista) {
        if (recyclerView == null) return;
        adapter = new RVClienteAdapter(this, novaLista, position -> {
            AcaoDoClick(novaLista.get(position));
        });
        atualizarResumoFiltros(novaLista.size());
        recyclerView.setAdapter(adapter);
    }

    private void AcaoDoClick(ClienteDTO dto) {
        Double totReceber = crbrl.getTotalReceberCliente(dto.getCodCliente());
        String info = "Razão: " + dto.getRazaoSocial() + "\nSaldo: R$ " + (dto.getLimiteCredito() - totReceber);

        new AlertDialog.Builder(this)
                .setTitle("Detalhes do Cliente")
                .setMessage(info)
                .setPositiveButton("Fechar", null)
                .show();
    }

    private void showFilterDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_cliente_filtros, null);
        Spinner spinnerFilter = dialogView.findViewById(R.id.cbxFiltros);
        EditText editTextValue = dialogView.findViewById(R.id.txtFiltroClientes);

        new AlertDialog.Builder(this)
                .setTitle("Pesquisa Avançada")
                .setView(dialogView)
                .setPositiveButton("Filtrar", (dialog, id) -> {
                    aplicarFiltros(spinnerFilter.getSelectedItem().toString(), editTextValue.getText().toString());
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void aplicarFiltros(String filtro, String valor) {
        List<ClienteDTO> list = new ArrayList<>();
        switch (filtro) {
            case "Nome Fantasia": list = brl.getByNome(valor); break;
            case "Razão Social": list = brl.getByRazaoSocial(valor); break;
            case "Codigo": list = brl.getByCodigo(valor); break;
            case "CPF/CNPJ": list = brl.getByCPFCNPJ(valor); break;
        }
        Global.lstClientes = list;
        configurarAdapter(list);
    }

    private void atualizarResumoFiltros(int total) {
        TextView txtFiltros = findViewById(R.id.txtResumoFiltros);
        TextView txtTotal = findViewById(R.id.txtContagemClientes);

        // Global.codRota ou o nome da rota que você obteve no ComboRotas
        String rotaAtual = Global.codRota != null ? Global.codRota : "Todas";
        String ordenacao = "Seq. Visita"; // Ou a lógica que estiver usando

        txtFiltros.setText(String.format("Filtros: Rota: %s | Ord: %s", rotaAtual, ordenacao));
        txtTotal.setText("Total: " + total);
    }

    private void ComboRotas() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_cliente_rota, null);
        Spinner spinner = dialogView.findViewById(R.id.cbxRota);
        List<RotaDTO> rotas = rotBRL.getComboRota();

        ArrayAdapter<RotaDTO> adapterRotas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, rotas);
        spinner.setAdapter(adapterRotas);

        new AlertDialog.Builder(this)
                .setTitle("Mudar Rota")
                .setView(dialogView)
                .setPositiveButton("OK", (dialog, which) -> {
                    RotaDTO r = (RotaDTO) spinner.getSelectedItem();
                    Global.codRota = String.valueOf(r.getCodRota());
                    List<ClienteDTO> clientes = brl.getPorRotaOrdenado(Global.codRota, "nome");
                    Global.lstClientes = clientes;
                    configurarAdapter(clientes);
                })
                .show();
    }
}