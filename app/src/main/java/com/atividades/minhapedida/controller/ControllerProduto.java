package com.atividades.minhapedida.controller;

import android.app.AlertDialog;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.atividades.minhapedida.R;
import com.atividades.minhapedida.model.banco.BaseDAO;
import com.atividades.minhapedida.model.bo.BO;
import com.atividades.minhapedida.model.dao.ProdutoDAO;
import com.atividades.minhapedida.model.vo.ProdutoVO;
import com.atividades.minhapedida.util.Constantes;
import com.atividades.minhapedida.view.ProdutoActivity;
import com.j256.ormlite.dao.Dao;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ControllerProduto {
    private ProdutoActivity activity;

    private ProdutoVO p;
    private String msg;

    private List<ProdutoVO> listProduto;
    private ArrayAdapter<ProdutoVO> adapter;

    private BaseDAO<ProdutoVO> daoProduto;

    public ControllerProduto(ProdutoActivity activity) {
        this.activity = activity;
        daoProduto = new ProdutoDAO(this.activity, ProdutoVO.class);
        this.configListView();
        this.refreshData(2);
    }

    private void configListView() {
        listProduto = daoProduto.consultar(Constantes.DB_PRODUTO_STATUS);
        adapter = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1,
                listProduto
        );
        adapter.notifyDataSetChanged();
        activity.getListView().setAdapter(adapter);

        this.addClickCurto();
        this.addClickLongo();
    } // OK

    private void addClickCurto() {
        activity.getListView().setOnItemClickListener(
                (parent, view, position, id) -> {
                    this.p = adapter.getItem(position);
                    if (this.p != null) {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                        alerta.setTitle(R.string.FIELD_produto);
                        alerta.setMessage(this.p.toString());
                        alerta.setIcon(android.R.drawable.ic_menu_edit);
                        alerta.setNegativeButton(R.string.MESSAGE_fechar, (dialog, which) -> this.p = null);
                        alerta.setPositiveButton(R.string.MESSAGE_editar, (dialog, which) -> this.popularForm(this.p));
                        alerta.show();
                    } else {
                        Toast.makeText(activity, R.string.ERROR_popularForm, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    } // OK

    private void addClickLongo() {
        activity.getListView().setOnItemLongClickListener(
                (parent, view, position, id) -> {
                    this.p = adapter.getItem(position);
                    if (this.p != null) {
                        this.excluirAction();
                        return true;
                    }
                    return false;
                }
        );
    } // OK

    public void cadastrarAction() {
        if (this.p == null) {
            if (this.validarCampos(this.getResultadoForm())) {
                this.cadastrar();
            }
        } else {
            this.editarAction(this.getResultadoForm());
        }
        if (this.validarCampos(this.getResultadoForm())) {
            this.limparForm();
        }

        this.refreshData(2);
        this.p = null;
        System.gc();

    } // OK

    private void cadastrar() {
        this.p = getResultadoForm();
        ProdutoVO produtoCadastrado = daoProduto.cadastrar(this.p);
        if (produtoCadastrado != null) {

            msg = activity.getString(R.string.SUCCESS_cadastro, p.toString());
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            Log.i("Cadastrando", "Cadastrando: " + p.toString());

            adapter.add(produtoCadastrado);
            this.refreshData(0);

        } else {
            msg = activity.getString(R.string.ERROR_cadastrar, p.toString());
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            Log.e("DB_INSERT_ERRO", "Cadastro: " + this.p.toString());
        }
    } // OK

    private void editarAction(@NotNull ProdutoVO newProduto) {
        this.p.setNome(newProduto.getNome());
        this.p.setValor(newProduto.getValor());
        this.p.setStatus(newProduto.getStatus());

        Dao.CreateOrUpdateStatus daoStatus = daoProduto.alterar(this.p);
        int i = daoStatus.getNumLinesChanged();

        if (i > 0) {
            if (daoStatus.isCreated()) {
                msg = activity.getString(R.string.SUCCESS_cadastro, p.toString());
                Toast.makeText(activity, msg + "(" + i + ")", Toast.LENGTH_SHORT).show();
                Log.i("Cadastrado", "Cadastrado: " + p.toString());
            } else if (daoStatus.isUpdated()) {
                msg = activity.getString(R.string.SUCCESS_alterado, p.toString());
                Toast.makeText(activity, msg + "(" + i + ")", Toast.LENGTH_SHORT).show();
                Log.i("Alterando", "Alterando: " + p.toString());
            }
            if (!p.getStatus()) {
                this.adapter.remove(this.p);
                this.refreshData(0);
            }
        } else {
            msg = activity.getString(R.string.ERROR_alterar, p.toString());
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            Log.e("DB_INFO", "Erro ao Editar:" + this.p.toString());
        }
    } // OK

    private void excluirAction() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle(R.string.MESSAGE_builderTitle);
        String alertaMessage = activity.getString(R.string.MESSAGE_ExcluirProduto, p.toString());
        alerta.setMessage(alertaMessage);
        alerta.setIcon(android.R.drawable.ic_menu_delete);
        alerta.setNegativeButton(R.string.MESSAGE_fechar, (dialog, which) -> this.p = null);
        //Deletar
        alerta.setPositiveButton(R.string.MESSAGE_sim, (dialog, which) -> this.excluir());
        alerta.show();
    } // OK

    private void excluir() {
        Integer i = daoProduto.excluir(this.p);

        if (i != null && i > 0) {
            msg = activity.getString(R.string.SUCCESS_excluido, p.toString());
            Toast.makeText(activity, msg + "(" + i + ")", Toast.LENGTH_SHORT).show();
            Log.i("DB_INFO", "Sucesso ao Alterar: " + this.p.toString());

            this.adapter.remove(this.p);
            this.refreshData(0);
        } else {
            msg = activity.getString(R.string.ERROR_excluir, p.toString());
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            Log.e("DB_INFO", "Erro ao Excluir: " + this.p.toString());
        }
    }

    private void popularForm(ProdutoVO p) {
        if (p != null) {
            try {
                activity.getEditNome().setText(p.getNome());
                activity.getEditValor().setText(String.valueOf(p.getValor()));
                activity.getCheckBox().setChecked(p.getStatus());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.getCause());
            }
        } else {
            Toast.makeText(activity, R.string.ERROR_popularForm, Toast.LENGTH_SHORT).show();
        }
    } // OK

    @NotNull
    private ProdutoVO getResultadoForm() {
        ProdutoVO produtoVO = new ProdutoVO();
        produtoVO.setNome(activity.getEditNome().getText().toString());
        produtoVO.setValor(activity.getEditValor().getText().toString());
        produtoVO.setStatus(activity.getCheckBox().isChecked());
        return produtoVO;
    } // OK

    private boolean validarCampos(ProdutoVO p) {
        if (!BO.validarNome(p)) {
            msg = activity.getString(R.string.ERROR_digitado);
            activity.getEditNome().setError(msg);
            activity.getEditNome().requestFocus();
            return false;
        }
        if (!BO.validarValor(p)) {
            msg = activity.getString(R.string.ERROR_digitado);
            activity.getEditValor().setError(msg);
            activity.getEditValor().requestFocus();
            return false;
        }
        return true;
    }

    private void limparForm() {
        activity.getEditValor().setText("");
        activity.getEditNome().setText("");
        activity.getCheckBox().setSelected(false);
        this.clearFocus();
    } // OK

    private void clearFocus() {
        activity.getEditNome().clearFocus();
        activity.getEditValor().clearFocus();
        activity.getCheckBox().clearFocus();
        activity.getListView().clearFocus();
    } // OK

    public void voltarAction() {
        Toast.makeText(this.activity, R.string.MESSAGE_voltando, Toast.LENGTH_SHORT).show();
        this.refreshData(2);
        this.activity.finish();
    } // OK

    public void refreshData(int tipo) {
        if (tipo == 0) {
            adapter.notifyDataSetChanged();
        }
        if (tipo == 1) {
            listProduto = daoProduto.consultar(Constantes.DB_PRODUTO_STATUS);
        }
        if (tipo == 2) {
            adapter.notifyDataSetChanged();
            listProduto = daoProduto.consultar(Constantes.DB_PRODUTO_STATUS);
        }
    }




}