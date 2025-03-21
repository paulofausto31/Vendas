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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import persistencia.adapters.RVProdutoAdapter;
import persistencia.brl.FornecedorBRL;
import persistencia.brl.GrupoBRL;
import persistencia.brl.PrecoBRL;
import persistencia.brl.ProdutoBRL;
import persistencia.dto.FornecedorDTO;
import persistencia.dto.GrupoDTO;
import persistencia.dto.PrecoDTO;
import persistencia.dto.ProdutoDTO;
import venda.util.Global;

public class RVProdutoLista extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RVProdutoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ProdutoBRL brl;
    PrecoBRL preBRL;
    GrupoBRL grpBRL;
    FornecedorBRL forBRL;

    public class Grupo {
        private String codigo;
        private String descricao;

        public Grupo(){}

        public Grupo(String codigo, String descricao) {
            this.codigo = codigo;
            this.descricao = descricao;
        }

        public String getCodigo() {
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

    public class Fornecedor {
        private Integer codigo;
        private String descricao;

        public Fornecedor(){}

        public Fornecedor(Integer codigo, String descricao) {
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
        setContentView(R.layout.recyclerview_produto);

        recyclerView = findViewById(R.id.recycler_produtos);

        // Use um LayoutManager para o RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Dados de exemplo
        brl = new ProdutoBRL(getBaseContext());
        preBRL = new PrecoBRL(getBaseContext());

        //Intent it = getIntent();
        //Boolean param = it.getBooleanExtra("paramProduto", true);
/*
        if (Global.lstProdutos != null){
            List<ProdutoDTO> _list = Global.lstProdutos;

            adapter = new RVProdutoAdapter(getBaseContext(), _list, new RVProdutoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    // Ação para quando um item é clicado
                    ProdutoDTO item = _list.get(position);
                    AcaoDoClick(item);
                }
            });
            recyclerView.setAdapter(adapter);
        }*/
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ProdutoBRL brl = new ProdutoBRL(getBaseContext());
        if (Global.lstProdutos == null){
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
            List<ProdutoDTO> _list = Global.lstProdutos;

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

    private void ComboGrupo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione o filtro");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_cliente_grupo, null);
        builder.setView(dialogView);

        final Spinner spinnerFiltergrupo = dialogView.findViewById(R.id.cbxGrupo);

        grpBRL = new GrupoBRL(getBaseContext());
        List<GrupoDTO> grupos = grpBRL.getAll();

        List<Grupo> lstGrupo = new ArrayList<Grupo>();
        lstGrupo.add(new Grupo("-1", "Selecione uma linha ..."));
        for (GrupoDTO grupoDTO : grupos) {
            Grupo grupoAdd = new Grupo(grupoDTO.getCodGrupo(), grupoDTO.getDescricao());
            lstGrupo.add(grupoAdd);
        }

        ArrayAdapter<Grupo> adapterGrupos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstGrupo);
        adapterGrupos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltergrupo.setAdapter(adapterGrupos);

        spinnerFiltergrupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle spinner selection
                Grupo grupoSelecionado = (Grupo) parent.getItemAtPosition(position);
                String codigoSelecionado = grupoSelecionado.getCodigo();

                // Do something with the selected item
                if (position > 0){
                    List<ProdutoDTO> lstProdutos = brl.getByGrupo(codigoSelecionado);
                    Global.lstProdutos = lstProdutos;
                    adapter = new RVProdutoAdapter(getBaseContext(), lstProdutos, new RVProdutoAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            // Ação para quando um item é clicado
                            ProdutoDTO item = lstProdutos.get(position);
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
            String selectedItem = spinnerFiltergrupo.getSelectedItem().toString();
            // Do something with the selected item
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void ComboFornecedor(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione o filtro");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_cliente_fornecedor, null);
        builder.setView(dialogView);

        final Spinner spinnerFilterFornecedor = dialogView.findViewById(R.id.cbxFornecedor);

        forBRL = new FornecedorBRL(getBaseContext());
        List<FornecedorDTO> fornecedores = forBRL.getAll();

        List<Fornecedor> lstFornecedor = new ArrayList<Fornecedor>();
        lstFornecedor.add(new Fornecedor(-1, "Selecione um fornecedor ..."));
        for (FornecedorDTO fornecedorDTO : fornecedores) {
            Fornecedor fornecedorAdd = new Fornecedor(fornecedorDTO.getCodFornecedor(), fornecedorDTO.getDescricao());
            lstFornecedor.add(fornecedorAdd);
        }

        ArrayAdapter<Fornecedor> adapterFornecedor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstFornecedor);
        adapterFornecedor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilterFornecedor.setAdapter(adapterFornecedor);

        spinnerFilterFornecedor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle spinner selection
                Fornecedor fornecedorSelecionado = (Fornecedor) parent.getItemAtPosition(position);
                Integer codigoSelecionado = fornecedorSelecionado.getCodigo();

                // Do something with the selected item
                if (position > 0){
                    List<ProdutoDTO> lstProdutos = brl.getByFornecedor(codigoSelecionado);
                    Global.lstProdutos = lstProdutos;
                    adapter = new RVProdutoAdapter(getBaseContext(), lstProdutos, new RVProdutoAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            // Ação para quando um item é clicado
                            ProdutoDTO item = lstProdutos.get(position);
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
            String selectedItem = spinnerFilterFornecedor.getSelectedItem().toString();
            // Do something with the selected item
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_linha:
                ComboGrupo();
                return true;
            case R.id.menu_fornecedor:
                ComboFornecedor();
                return true;
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
                            //Global.prodPesquisa = value;
                            ProdutoBRL brl = new ProdutoBRL(getBaseContext());
                            List<ProdutoDTO> _list = brl.getByDescricao(value);
                            Global.lstProdutos = _list;

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
                //Global.prodPesquisa = null;
                Global.lstProdutos = null;
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
