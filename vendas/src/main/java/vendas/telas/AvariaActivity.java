package vendas.telas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import persistencia.adapters.AvariaAdapter;
import persistencia.brl.AvariaBRL;
import persistencia.brl.ClienteBRL;
import persistencia.brl.ItemAvariaBRL;
import persistencia.brl.ProdutoBRL;
import persistencia.dto.AvariaDTO;
import persistencia.dto.ClienteDTO;
import persistencia.dto.ItemAvariaDTO;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.ProdutoDTO;
import venda.util.Global;
import venda.util.PDFGenerator;
import venda.util.Util;

public class AvariaActivity extends AppCompatActivity {

    // Declaração dos componentes de interface
    private EditText txtCodProduto;
    private EditText txtDescricaoProduto;
    private EditText txtQuantidade;
    private EditText txtUnidade;
    private EditText txtPrecoUnitario;
    private EditText txtInfAdicional;

    private Button btnPesquisar;
    private Button btnAdicionar;
    private Button btnGerarPdf;
    private RecyclerView recyclerViewAvarias;
    ProdutoBRL proBRL;
    private AvariaDTO avariaAtual = null;
    private AvariaBRL avaBRL;
    private ItemAvariaBRL itaBRL;
    private static int idCliente;
    private AvariaAdapter adapter;
    private ItemAvariaDTO itemEmEdicao = null;
    private int posicaoEmEdicao = -1;

    private List<ItemAvariaDTO> listaItensAvaria = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaria);

        proBRL = new ProdutoBRL(this);
        avaBRL = new AvariaBRL(this);
        itaBRL = new ItemAvariaBRL(this);
        Intent it = getIntent();
        idCliente = it.getIntExtra("idCliente", 0);


        inicializarComponentes();

        configurarRecyclerView();

        configurarListener();
    }

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String itemId = result.getData().getStringExtra("itemId");
                    txtCodProduto.setText(itemId);
                    CarregaDescricaoProduto(txtCodProduto.getText().toString());
                }
            }
    );

    private void confirmarExclusaoItem(int position, ItemAvariaDTO itaDTO) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir Item")
                .setMessage("Tem certeza que deseja remover este item da avaria?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 1. Deleta do Banco de Dados

                        boolean sucesso = itaBRL.delete(itaDTO);

                        if (sucesso) {
                            // 2. Remove da lista visual e atualiza o grid
                            listaItensAvaria.remove(position);
                            adapter.notifyItemRemoved(position);
                            adapter.notifyItemRangeChanged(position, listaItensAvaria.size());
                            Toast.makeText(AvariaActivity.this, "Item excluído.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AvariaActivity.this, "Erro ao excluir do banco.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    private void prepararEdicaoItem(int position, ItemAvariaDTO item) {
        itemEmEdicao = item;
        posicaoEmEdicao = position;

        // Joga os dados de volta pros campos
        txtCodProduto.setText(String.valueOf(item.getCodProduto()));

        ProdutoDTO proDTO = proBRL.getByCodProduto(item.getCodProduto());
        if (proDTO != null) {
            txtDescricaoProduto.setText(proDTO.getDescricao());
        }

        txtQuantidade.setText(String.valueOf(item.getQuantidade()));
        txtUnidade.setText(item.getUnidade());
        txtPrecoUnitario.setText(String.valueOf(item.getPreco()));

        // Muda a cara do botão
        btnAdicionar.setText("Salvar Alteração");
    }

    private void realizarPesquisa() {
        if (Util.isNumeric(txtCodProduto.getText().toString().trim())){
            CarregaDescricaoProduto(txtCodProduto.getText().toString().trim());
        } else if (txtCodProduto.getText().toString().trim().length() > 0) {
            Global.prodPesquisa = txtCodProduto.getText().toString().trim();
            List<ProdutoDTO> _list = proBRL.getByDescricao(Global.prodPesquisa);
            Global.lstProdutos = _list;
            Intent intent = new Intent(this, PedidoProdutoLista.class).putExtra("paramProduto", true);
            activityResultLauncher.launch(intent);
            //startActivityForResult(new Intent(getBaseContext(), RVProdutoLista.class).putExtra("paramProduto", true), RETORNO_PRODUTO);
        }else
        {
            Intent intent = new Intent(this, PedidoProdutoLista.class).putExtra("paramProduto", true);
            activityResultLauncher.launch(intent);
            //startActivityForResult(new Intent(getBaseContext(), RVProdutoLista.class).putExtra("paramProduto", true), RETORNO_PRODUTO);
        }
    }

    private void CarregaDescricaoProduto(String codProduto){
        ProdutoDTO proDTO = proBRL.getByCodProduto(Long.parseLong(codProduto));
        if (proDTO != null){
            txtDescricaoProduto.setText(proDTO.getDescricao());
            txtUnidade.setText("UND");
            txtQuantidade.setText("");
            txtPrecoUnitario.setText("");
            txtQuantidade.requestFocus();
        }
        else
            Toast.makeText(this, "Produto não cadastrado", Toast.LENGTH_SHORT).show();
    }

    private void inicializarComponentes() {
        txtCodProduto = findViewById(R.id.etBuscaProduto);
        txtDescricaoProduto = findViewById(R.id.etDescricaoProduto);
        txtQuantidade = findViewById(R.id.etQuantidade);
        txtUnidade = findViewById(R.id.etUnidade);
        txtPrecoUnitario = findViewById(R.id.etPrecoUnitario);
        txtInfAdicional = findViewById(R.id.etInfAdicional);

        btnPesquisar = findViewById(R.id.btnPesquisar);
        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnGerarPdf = findViewById(R.id.btnGerarPdf);

        recyclerViewAvarias = findViewById(R.id.recyclerViewAvarias);


    }

    private void configurarListener() {
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarPesquisa();
            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarAvaria();
            }
        });

        btnGerarPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GravaInfAdicional();
                gerarPdfAvaria();
            }
        });
    }

    private void gerarPdfAvaria() {
        // 1. Validações de segurança
        if (avariaAtual == null || listaItensAvaria.isEmpty()) {
            Toast.makeText(this, "Adicione pelo menos um item para gerar o PDF da avaria.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // 2. Busca os dados completos do Cliente usando o idCliente que você recebeu no Intent
            ClienteBRL cliBRL = new ClienteBRL(this);
            ClienteDTO clienteAtual = cliBRL.getById(idCliente);

            if (clienteAtual == null) {
                Toast.makeText(this, "Erro: Dados do cliente não encontrados.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 3. Monta o nome do arquivo dinamicamente (ex: Avaria_105.pdf)
            String nomeArquivo = "Avaria_" + avariaAtual.getId() + ".pdf";


            PDFGenerator pdfGen = new PDFGenerator();
            pdfGen.createPDFAvaria(this, nomeArquivo, listaItensAvaria, clienteAtual, avariaAtual);

            enviarPdfWhatsApp(nomeArquivo);

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Erro ao acessar o arquivo para gravação.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(this, "Erro inesperado ao gerar PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        GravaInfAdicional();
    }

    private void GravaInfAdicional() {

        // Só atualiza as informações adicionais se a Avaria Mestre já existir no banco
        // (Isso evita criar uma avaria vazia se o cara só digitou no campo e saiu da tela)
        if (avariaAtual != null) {
            String obs = txtInfAdicional.getText().toString();

            // Seta o valor atualizado na variável global
            avariaAtual.setInfAdicional(obs);

            // Manda o BRL atualizar o banco de dados.

            avaBRL.Update(avariaAtual);
        }
    }

    private void enviarPdfWhatsApp(String nomeArquivo) {
        try {
            // Pega exatamente o mesmo caminho onde o PDF foi salvo
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), nomeArquivo);

            if (!file.exists()) {
                Toast.makeText(this, "Erro: Arquivo PDF não encontrado para envio.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gera a URI segura usando o FileProvider que configuramos no Manifest
            String autorizacao = getApplicationContext().getPackageName() + ".provider";
            Uri uri = FileProvider.getUriForFile(this, autorizacao, file);

            // Cria a ação de enviar
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Dá permissão pro WhatsApp ler

            // Força a abertura específica do WhatsApp
            intent.setPackage("com.whatsapp");
            // NOTA: Se você usa o WhatsApp Business para testar, mude para "com.whatsapp.w4b"

            startActivity(intent);

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "O WhatsApp não está instalado neste dispositivo.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao compartilhar: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Mude "menu_avaria" se você usou outro nome no Passo 2
        getMenuInflater().inflate(R.menu.menu_avaria, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Verifica se o cara clicou no nosso botão de ID "action_voltar"
        if (item.getItemId() == R.id.action_voltar) {

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void adicionarAvaria() {
        String produtoCod = txtCodProduto.getText().toString();

        if (produtoCod.isEmpty() || !Util.isNumeric(produtoCod)) {
            Toast.makeText(this, "Pesquise e selecione um produto primeiro.", Toast.LENGTH_SHORT).show();
            return;
        }

        String qtdStr = txtQuantidade.getText().toString();
        String precoStr = txtPrecoUnitario.getText().toString();
        String unidadeStr = txtUnidade.getText().toString();

        if (qtdStr.isEmpty() || precoStr.isEmpty()) {
            Toast.makeText(this, "Preencha a quantidade e o preço.", Toast.LENGTH_SHORT).show();
            return;
        }

        double quantidade = Double.parseDouble(qtdStr);
        double preco = Double.parseDouble(precoStr);

        // Grava a Avaria Mestre (se ainda não existir)
        if (avariaAtual == null) {
            avariaAtual = new AvariaDTO();
            avariaAtual.setCodEmpresa(Global.codEmpresa);
            avariaAtual.setCodCliente(idCliente);
            avariaAtual.setCodVendedor(Integer.parseInt(Global.codVendedor));
            avariaAtual.setInfAdicional(txtInfAdicional.getText().toString());

            String dataAtual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            avariaAtual.setDataAvaria(dataAtual);

            long idAvariaGerada = avaBRL.InsereAvaria(avariaAtual);

            if (idAvariaGerada == -1) {
                Toast.makeText(this, "Erro ao criar a Avaria Mestre no banco.", Toast.LENGTH_SHORT).show();
                return;
            }

            avariaAtual.setId((int) idAvariaGerada);
        }

        // ==========================================
        // A BIFURCAÇÃO (O PULO DO GATO)
        // ==========================================
        if (itemEmEdicao != null) {
            // FLUXO DE ATUALIZAÇÃO (UPDATE)
            itemEmEdicao.setCodProduto(Long.parseLong(produtoCod));
            itemEmEdicao.setQuantidade(quantidade);
            itemEmEdicao.setPreco(preco);
            itemEmEdicao.setUnidade(unidadeStr);

            boolean sucessoUpdate = itaBRL.Update(itemEmEdicao);

            if (sucessoUpdate) {
                // Atualiza a linha existente no RecyclerView
                listaItensAvaria.set(posicaoEmEdicao, itemEmEdicao);
                adapter.notifyItemChanged(posicaoEmEdicao);
                Toast.makeText(this, "Item atualizado com sucesso!", Toast.LENGTH_SHORT).show();

                // Reseta as variáveis de controle de edição
                itemEmEdicao = null;
                posicaoEmEdicao = -1;
                btnAdicionar.setText("Adicionar Item"); // Volta o botão ao texto original
                limparCamposParaProximoItem();
            } else {
                Toast.makeText(this, "Erro ao atualizar item no banco.", Toast.LENGTH_SHORT).show();
            }

        } else {
            // FLUXO DE INSERÇÃO (INSERT NOVO)
            ItemAvariaDTO novoItem = new ItemAvariaDTO();
            novoItem.setCodAvaria(avariaAtual.getId());
            novoItem.setCodEmpresa(avariaAtual.getCodEmpresa());
            novoItem.setCodProduto(Long.parseLong(produtoCod));
            novoItem.setQuantidade(quantidade);
            novoItem.setPreco(preco);
            novoItem.setUnidade(unidadeStr);

            long idItemGerado = itaBRL.InsereItemAvaria(novoItem);

            if (idItemGerado != -1) {
                novoItem.setId((int) idItemGerado);

                // Adiciona uma nova linha no final do RecyclerView
                listaItensAvaria.add(novoItem);
                adapter.notifyItemInserted(listaItensAvaria.size() - 1);

                Toast.makeText(this, "Item adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                limparCamposParaProximoItem();
            } else {
                Toast.makeText(this, "Erro ao gravar o item.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void limparCamposParaProximoItem() {
        txtCodProduto.setText("");
        txtDescricaoProduto.setText("");
        txtQuantidade.setText("");
        txtUnidade.setText("");
        txtPrecoUnitario.setText("");
        txtInfAdicional.setText("");
        txtCodProduto.requestFocus();
    }

    private void configurarRecyclerView() {
        recyclerViewAvarias.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AvariaAdapter(listaItensAvaria, new AvariaAdapter.OnItemClickListener() {
            @Override
            public void onEditarClick(int position, ItemAvariaDTO item) {
                prepararEdicaoItem(position, item);
            }

            @Override
            public void onExcluirClick(int position, ItemAvariaDTO item) {
                confirmarExclusaoItem(position, item);
            }
        });

        recyclerViewAvarias.setAdapter(adapter);
    }
}
