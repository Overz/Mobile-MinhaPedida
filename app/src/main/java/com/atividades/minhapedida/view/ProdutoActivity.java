package com.atividades.minhapedida.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.atividades.minhapedida.R;
import com.atividades.minhapedida.controller.ControllerProduto;
import com.atividades.minhapedida.util.Constantes;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class ProdutoActivity extends AppCompatActivity {
    private ControllerProduto controllerProduto;

    private Button btnVoltar, btnEnviar;
    private EditText editNome, editValor;
    private CheckBox checkBox;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        this.initialize();
        Constantes.TIPO_TOSTRING = 1;
        controllerProduto = new ControllerProduto(this);
        controllerProduto.refreshData(2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Constantes.TIPO_TOSTRING = 1;
        controllerProduto.refreshData(2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Constantes.TIPO_TOSTRING = 1;
        controllerProduto.refreshData(2);
    }

    private void initialize() {
        this.editNome = findViewById(R.id.editNomeProduto);
        this.editValor = findViewById(R.id.editValor);
        this.btnVoltar = findViewById(R.id.btnVoltar);
        this.btnEnviar = findViewById(R.id.btnEnviar);
        this.checkBox = findViewById(R.id.cbAtivo);
        this.listView = findViewById(R.id.lvGerenciamento);
        this.onClickListener();
    }

    private void onClickListener() {
        this.btnEnviar.setOnClickListener(v -> controllerProduto.cadastrarAction());
        this.btnVoltar.setOnClickListener(v -> controllerProduto.voltarAction());
    }

    public Button getBtnVoltar() {
        return btnVoltar;
    }

    public Button getBtnEnviar() {
        return btnEnviar;
    }

    public EditText getEditNome() {
        return editNome;
    }

    public EditText getEditValor() {
        return editValor;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public ListView getListView() {
        return listView;
    }

}
