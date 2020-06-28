package com.atividades.minhapedida.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.atividades.minhapedida.R;
import com.atividades.minhapedida.controller.ControllerPedido;
import com.atividades.minhapedida.util.Constantes;

public class PedidoActivity extends AppCompatActivity {

    private ControllerPedido controllerPedido;

    private Spinner spinner;
    private NumberPicker numberPicker;
    private Button btnEnviar, btnVoltar, btnGerenciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        this.initialize();
        Constantes.TIPO_TOSTRING = 0;
        controllerPedido = new ControllerPedido(this);

        controllerPedido.refreshData(2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Constantes.TIPO_TOSTRING = 0;
        controllerPedido.refreshData(2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Constantes.TIPO_TOSTRING = 0;
        controllerPedido.refreshData(2);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initialize() {
        this.btnEnviar = findViewById(R.id.btnEnviar);
        this.btnGerenciar = findViewById(R.id.btnGerenciamento);
        this.btnVoltar = findViewById(R.id.btnVoltar);
        this.spinner = findViewById(R.id.spinnerProduto);
        this.numberPicker = findViewById(R.id.numberPicker);
        this.onClickListener();
    }

    private void onClickListener() {
        this.btnGerenciar.setOnClickListener(v -> {
            Intent it = new Intent(this, ProdutoActivity.class);
            startActivity(it);
        });
        this.btnEnviar.setOnClickListener(v -> controllerPedido.cadastrarAction());
        this.btnVoltar.setOnClickListener(v -> controllerPedido.voltarAction());
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public NumberPicker getNumberPicker() {
        return numberPicker;
    }

    public Button getBtnEnviar() {
        return btnEnviar;
    }

    public Button getBtnVoltar() {
        return btnVoltar;
    }

    public Button getBtnGerenciar() {
        return btnGerenciar;
    }
}
