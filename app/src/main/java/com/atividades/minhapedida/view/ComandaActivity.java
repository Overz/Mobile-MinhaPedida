package com.atividades.minhapedida.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.atividades.minhapedida.R;
import com.atividades.minhapedida.controller.ControllerComanda;
import com.atividades.minhapedida.util.Constantes;

public class ComandaActivity extends AppCompatActivity {
    private ControllerComanda controllerComanda;

    private Button btnAddProduto, btnLimpar;
    private TextView tvValor;
    private ListView lvComanda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);
        this.initialize();
        Constantes.TIPO_TOSTRING = 0;
        controllerComanda = new ControllerComanda(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Constantes.TIPO_TOSTRING = 0;
        controllerComanda.refreshData(2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Constantes.TIPO_TOSTRING = 0;
        controllerComanda.refreshData(2);
    }

    private void initialize() {
        this.btnAddProduto = findViewById(R.id.btnAddProduto);
        this.btnLimpar = findViewById(R.id.btnLimpar);
        this.tvValor = findViewById(R.id.tvValor);
        this.lvComanda = findViewById(R.id.lvComanda);
        this.onClickListener();
    }

    private void onClickListener() {
        this.btnAddProduto.setOnClickListener(v -> {
            Intent it = new Intent(this, PedidoActivity.class);
            startActivity(it);
        });
        this.btnLimpar.setOnClickListener(v -> controllerComanda.limparAction());
    }

    public Button getBtnAddProduto() {
        return btnAddProduto;
    }

    public Button getBtnLimpar() {
        return btnLimpar;
    }

    public TextView getTvValor() {
        return tvValor;
    }

    public ListView getLvComanda() {
        return lvComanda;
    }
}
