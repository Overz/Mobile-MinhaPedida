package com.atividades.minhapedida.controller;

import android.app.AlertDialog;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.atividades.minhapedida.R;
import com.atividades.minhapedida.model.banco.BaseDAO;
import com.atividades.minhapedida.model.bo.BO;
import com.atividades.minhapedida.model.dao.PedidoDAO;
import com.atividades.minhapedida.model.dao.ProdutoDAO;
import com.atividades.minhapedida.model.vo.PedidoVO;
import com.atividades.minhapedida.model.vo.ProdutoVO;
import com.atividades.minhapedida.util.Constantes;
import com.atividades.minhapedida.view.PedidoActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ControllerPedido {
    private PedidoActivity activity;

    private PedidoVO p;

    private List<ProdutoVO> listProduto;
    private ArrayAdapter<ProdutoVO> adapterProduto;

    private BaseDAO<PedidoVO> daoPedido;
    private BaseDAO<ProdutoVO> daoProduto;

    public ControllerPedido(PedidoActivity activity) {
        this.activity = activity;
        daoPedido = new PedidoDAO(this.activity, PedidoVO.class);
        daoProduto = new ProdutoDAO(this.activity, ProdutoVO.class);
        this.configSpinner();
        this.configNumberPicker();
    } // OK

    private void configSpinner() {
        listProduto = daoProduto.consultar(Constantes.DB_PRODUTO_STATUS);
        adapterProduto = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_spinner_dropdown_item,
                listProduto
        );
        adapterProduto.notifyDataSetChanged();
        activity.getSpinner().setAdapter(adapterProduto);
    } // OK

    private void configNumberPicker() {
        adapterProduto.notifyDataSetChanged();

        activity.getNumberPicker().setMinValue(1);
        activity.getNumberPicker().setMaxValue(99);
        activity.getNumberPicker().setValue(5);
        activity.getNumberPicker().setTextSize(50);
        activity.getNumberPicker().setBackgroundColor(activity.getColor(R.color.colorPrimary));

        activity.getNumberPicker().setOnScrollListener((view, scrollState) -> {
            if (activity.getNumberPicker().getValue() <= 35) {
                activity.getNumberPicker().setBackgroundColor(activity.getColor(R.color.colorPrimary));
            }
            if (activity.getNumberPicker().getValue() > 35) {
                activity.getNumberPicker().setBackgroundColor(activity.getColor(R.color.bg_warning));
            }
            if (activity.getNumberPicker().getValue() >= 65) {
                activity.getNumberPicker().setBackgroundColor(activity.getColor(R.color.red));
            }
        });

        adapterProduto.notifyDataSetChanged();
    } // OK

    public void cadastrarAction() {
        if (this.p == null) {
            if (validarCampos(getResultadoForm())) {
                this.cadastrar();
            }
            adapterProduto.notifyDataSetChanged();
        }

        listProduto = daoProduto.consultar(Constantes.DB_PRODUTO_STATUS);
        adapterProduto.notifyDataSetChanged();

        this.clearFocus();
        this.p = null;
        System.gc();
    } // OK

    private void cadastrar() {
        this.p = getResultadoForm();
        PedidoVO pedidoCadastrado = daoPedido.cadastrar(this.p);
        String msg;
        if (pedidoCadastrado != null) {
            msg = activity.getString(R.string.SUCCESS_cadastro, p.toString());
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            Log.i("Cadastrando", "Cadastrando: " + p.toString());

            listProduto = daoProduto.consultar(Constantes.DB_PRODUTO_STATUS);
            adapterProduto.notifyDataSetChanged();

        } else {
            msg = activity.getString(R.string.ERROR_cadastrar, p.toString());
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            Log.e("DB_INSERT_ERRO", "Cadastro: " + this.p.toString());
        }
    } // OK

    @NotNull
    private PedidoVO getResultadoForm() {
        PedidoVO p = new PedidoVO();
        p.setProdutoVO(activity.getSpinner().getSelectedItem());
        p.setQuantidade(activity.getNumberPicker().getValue());
        Double subTotal = (p.getProdutoVO().getValor() * p.getQuantidade());
        p.setSubTotal(subTotal);
        return p;
    } // OK

    private boolean validarCampos(PedidoVO p) {
        if (!BO.validarQuantidadePedido(p)) {
            this.alertDialog();
            this.clearFocus();
            return false;
        }
        if (!BO.validarSpinner(p.getProdutoVO())) {
            this.alertDialog();
            this.clearFocus();
            return false;
        }
        return true;
    } // OK

    private void alertDialog() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle(R.string.MESSAGE_atencao);
        String alertaMessage = activity.getString(R.string.MESSAGE_alertBuilder);
        alerta.setMessage(alertaMessage);
        alerta.setIcon(android.R.drawable.ic_dialog_alert);
        alerta.setNegativeButton(R.string.MESSAGE_fechar, (dialog, which) -> {
        });
        alerta.show();
    } // OK

    private void clearFocus() {
        activity.getSpinner().clearFocus();
        activity.getNumberPicker().clearFocus();
    } // OK

    public void voltarAction() {
        Toast.makeText(this.activity, R.string.MESSAGE_voltando, Toast.LENGTH_SHORT).show();
        this.activity.finish();
    } // OK

    public void refreshData(int tipo) {
        if (tipo == 0) {
            adapterProduto.notifyDataSetChanged();
        }
        if (tipo == 1) {
            listProduto = daoProduto.consultar(Constantes.DB_PRODUTO_STATUS);
        }
        if (tipo == 2) {
            listProduto.clear();
            adapterProduto.clear();
            listProduto = daoProduto.consultar(Constantes.DB_PRODUTO_STATUS);
            adapterProduto.addAll(listProduto);
            adapterProduto.notifyDataSetChanged();
        }
    } // OK

}
