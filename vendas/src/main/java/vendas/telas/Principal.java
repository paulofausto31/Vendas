package vendas.telas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import persistencia.brl.ClienteBRL;
import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.PedidoBRL;
import venda.util.Global;

public class Principal extends AppCompatActivity {

    private String chave;
    private TextView txtTotalVendas, txtQtdPedidos, txtPercentual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Aplica o tema Material Components para compatibilidade com os novos widgets
        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_DayNight_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Configura a Toolbar no padrão moderno (Título: PalmVenda, Subtítulo: Cliente)
        configurarToolbar();

        // Mapeamento dos cliques nos Cards
        vincularEventosCards();

        txtTotalVendas = findViewById(R.id.txtTotalVendas);
        txtQtdPedidos = findViewById(R.id.txtQtdPedidos);
        txtPercentual = findViewById(R.id.txtPercentual);

        atualizarDashboard();
    }

    private void configurarToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                // Título principal
                getSupportActionBar().setTitle("PalmVenda");

                // Exibe o nome do cliente/licenciado no subtítulo
                if (Global.tituloAplicacao != null) {
                    getSupportActionBar().setSubtitle(Global.tituloAplicacao);
                } else {
                    getSupportActionBar().setSubtitle("Vendas Externas");
                }

                // Na tela principal não exibimos a seta de voltar
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    private void vincularEventosCards() {
        findViewById(R.id.cardCliente).setOnClickListener(v -> btnCliente_click());
        findViewById(R.id.cardProduto).setOnClickListener(v -> btnProduto_click());
        findViewById(R.id.cardConsulta).setOnClickListener(v -> btnConsulta_click());
        findViewById(R.id.cardComunicacao).setOnClickListener(v -> btnComunicacao_click());
        findViewById(R.id.cardUtilitario).setOnClickListener(v -> btnUtilitario_click());
        findViewById(R.id.cardConfiguracao).setOnClickListener(v -> {
            try {
                btnConfiguracao_click();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    private void atualizarDashboard() {
        try {
            DecimalFormat formatador = new DecimalFormat("##,###0.00");
            ItenPedidoBRL itpBRL = new ItenPedidoBRL(this);
            Double totalVendas = itpBRL.getSumTotalAberto();

            ClienteBRL cliBRL = new ClienteBRL(this);
            Double totalClientes = Double.parseDouble(cliBRL.getTotalClientes().toString());

            PedidoBRL pedBRL = new PedidoBRL(this);
            Double totalPositivacao = Double.parseDouble(pedBRL.getTotalPositivacao().toString());

            Double percentualPositivacao = (totalClientes > 0) ? ((totalPositivacao / totalClientes) * 100) : 0.0;

            txtTotalVendas.setText(String.format("R$ %.2f", totalVendas));
            txtQtdPedidos.setText(String.valueOf(totalPositivacao.intValue()));
            txtPercentual.setText(String.format("%.1f%%", percentualPositivacao));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Criamos o item de Menu programaticamente para o botão Sair, substituindo o XML de menu antigo
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem itemSair = menu.add(Menu.NONE, 1, Menu.NONE, "Sair");
        itemSair.setIcon(android.R.drawable.ic_lock_power_off);
        itemSair.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1) {
            confirmarSaida();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmarSaida() {
        new AlertDialog.Builder(this)
                .setTitle("Sair do Aplicativo")
                .setMessage("Deseja realmente encerrar o PalmVenda?")
                .setPositiveButton("Sim", (dialog, which) -> finishAffinity())
                .setNegativeButton("Não", null)
                .show();
    }

    // Ações de clique mantidas do seu código original
    private void btnCliente_click(){
        startActivity(new Intent(this, RVClienteLista.class));
    }

    private void btnProduto_click(){
        startActivity(new Intent(this, RVProdutoLista.class).putExtra("paramProduto", false));
    }

    private void btnConsulta_click(){
        startActivity(new Intent(this, ConsultaTabContainer.class));
    }

    private void btnComunicacao_click(){
        startActivity(new Intent(this, Comunicacao.class));
    }

    private void btnUtilitario_click(){
        startActivity(new Intent(this, UtilitarioTabContainer.class));
    }

    private void btnConfiguracao_click() throws ParseException{
        startActivity(new Intent(this, ConfiguracaoTabContainer.class));
    }

    private void RecuperaChave(){
        AlertDialog.Builder pesquisa = new AlertDialog.Builder(this);
        pesquisa.setTitle("Recupera Chave");
        pesquisa.setMessage("Informe a chave");
        pesquisa.setCancelable(false);

        final EditText input = new EditText(this);
        pesquisa.setView(input);

        pesquisa.setPositiveButton("Ok", (dialog, which) -> {
            String value = input.getText().toString();
            if (value.length() < 1)
                Toast.makeText(getBaseContext(), "Informe a chave", Toast.LENGTH_LONG).show();
            else {
                chave = value;
            }
        });

        pesquisa.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());
        pesquisa.create().show();
    }
}