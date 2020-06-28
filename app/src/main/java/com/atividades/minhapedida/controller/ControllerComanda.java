package com.atividades.minhapedida.controller;

import android.app.AlertDialog;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.atividades.minhapedida.R;
import com.atividades.minhapedida.model.banco.BaseDAO;
import com.atividades.minhapedida.model.dao.PedidoDAO;
import com.atividades.minhapedida.model.dao.ProdutoDAO;
import com.atividades.minhapedida.model.vo.PedidoVO;
import com.atividades.minhapedida.model.vo.ProdutoVO;
import com.atividades.minhapedida.util.Constantes;
import com.atividades.minhapedida.view.ComandaActivity;

import java.util.List;

public class ControllerComanda {
    private ComandaActivity activity;

    private String msg;

    private PedidoVO pedidoVO;

    private List<PedidoVO> listPedido;
    private ArrayAdapter<PedidoVO> adapter;

    private BaseDAO<ProdutoVO> daoProduto;
    private BaseDAO<PedidoVO> daoPedido;

    public ControllerComanda(ComandaActivity activity) {
        this.activity = activity;
        daoProduto = new ProdutoDAO(this.activity, ProdutoVO.class);
        daoPedido = new PedidoDAO(this.activity, PedidoVO.class);
        this.addCadastro();
        this.configListView();
        this.configLabelTotal();
    } // OK

    private void addCadastro() {

        ProdutoVO produtoCadastrado;
        produtoCadastrado = daoProduto.cadastrar(new ProdutoVO(1, "Refrigerante", 3.00, true));
        daoPedido.cadastrar(new PedidoVO(1, 2, 6.0, produtoCadastrado));
        System.out.println("Foi 1");

        produtoCadastrado = daoProduto.cadastrar(new ProdutoVO(2, "Cerveja", 5.00, true));
        daoPedido.cadastrar(new PedidoVO(2, 2, 10.0, produtoCadastrado));
        System.out.println("Foi 2");

        produtoCadastrado = daoProduto.cadastrar(new ProdutoVO(3, "Batata Frita", 10.00, true));
        daoPedido.cadastrar(new PedidoVO(3, 2, 20.0, produtoCadastrado));
        System.out.println("Foi 3");

        produtoCadastrado = daoProduto.cadastrar(new ProdutoVO(4, "Agua", 2.50, true));
        daoPedido.cadastrar(new PedidoVO(4, 2, 5.0, produtoCadastrado));
        System.out.println("Foi 4");

        produtoCadastrado = daoProduto.cadastrar(new ProdutoVO(5, "Pastel de Flango", 3.50, true));
        daoPedido.cadastrar(new PedidoVO(5, 2, 7.0, produtoCadastrado));
        System.out.println("Foi 5");

        produtoCadastrado = daoProduto.cadastrar(new ProdutoVO(6, "Petiscos", 6.00, true));
        daoPedido.cadastrar(new PedidoVO(6, 2, 12.0, produtoCadastrado));
        System.out.println("Foi 6");

    } // OK

    private void configListView() {
        listPedido = daoPedido.listarTodos();
        adapter = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1,
                listPedido
        );

        activity.getLvComanda().setAdapter(adapter);
        adapter.notifyDataSetChanged();

        this.addClickCurto();
        this.addClickLongo();
    } // OK

    private void configLabelTotal() {
        if (listPedido.size() > 0) {
            double total = 0.0;
            for (int i = 0; i < listPedido.size(); i++) {
                total += listPedido.get(i).getSubTotal();
            }

            if (total == 0) {
                activity.getTvValor().setVisibility(TextView.GONE);
            }
            if (total > 0) {
                activity.getTvValor().setVisibility(TextView.VISIBLE);
            }

            String lblValor = String.valueOf(total);
            activity.getTvValor().setText(activity.getString(R.string.FIELD_lblTotal, lblValor));
        }
    } // ok

    private void addClickCurto() {
        activity.getLvComanda().setOnItemClickListener(
                (parent, view, position, id) -> {
                    this.pedidoVO = adapter.getItem(position);
                    if (this.pedidoVO != null) {
                        this.alertBuilderClickCurtoAction();
                    } else {
                        msg = activity.getString(R.string.ERROR_cadastrar, pedidoVO.toString());
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    } // OK

    private void addClickLongo() {
        activity.getLvComanda().setOnItemLongClickListener(
                (parent, view, position, id) -> {
                    this.pedidoVO = adapter.getItem(position);
                    if (this.pedidoVO != null) {
                        this.alertBuilderClickLongoAction();
                        return true;
                    }
                    return false;
                }
        );

    } // OK

    /**
     * Constroi o AlertBuilder para Opção -1 ou +1 e Notifica sua alteração
     */
    private void alertBuilderClickCurtoAction() {
        Constantes.TIPO_TOSTRING = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(R.string.FIELD_produto);
        builder.setMessage(this.pedidoVO.toString());
        builder.setIcon(android.R.drawable.ic_menu_edit);
        builder.setNegativeButton(R.string.MESSAGE_menosUM, (dialog, which) -> {
            Constantes.TIPO_ALTERACAO = 0;
            cadastrar_excluir(builder);
            this.refreshData(2);
        });
        builder.setPositiveButton(R.string.MESSAGE_maisUM, (dialog, which) -> {
            Constantes.TIPO_ALTERACAO = 1;
            cadastrar_excluir(builder);
            this.refreshData(2);
        });
        builder.setNeutralButton(R.string.MESSAGE_fechar, (dialog, which) -> {
            Constantes.TIPO_TOSTRING = 0;
            this.pedidoVO = null;
        });
        builder.create();
        builder.show();
    } // ok

    /**
     * Método para verificar o TIPO de ALTERAÇÂO;
     * Constantes.TIPO_ALTERACAO = 0 : Remoção
     * Constantes.TIPO_ALTERACAO = 1 : Adição
     *
     * @param builder: AlertBuilder
     */
    private void cadastrar_excluir(AlertDialog.Builder builder) {
        int i;
        AlertDialog alerta = builder.create();
        if (Constantes.TIPO_ALTERACAO == 0) {
            i = daoPedido.alterarColumnValues(this.pedidoVO);
            pedidoVO = daoPedido.consultarPorID(pedidoVO.getId());

            if (i > 0) {
                this.toastMessage(Constantes.SUCCESS);
            } else {
                this.toastMessage(Constantes.FAILED);
            }
        }

        if (Constantes.TIPO_ALTERACAO == 1) {
            i = daoPedido.alterarColumnValues(this.pedidoVO);
            pedidoVO = daoPedido.consultarPorID(pedidoVO.getId());

            if (i > 0) {
                this.toastMessage(Constantes.SUCCESS);
            } else {
                this.toastMessage(Constantes.FAILED);
            }
        }

        // TODO Realizar Funcionalidade para Fechar o AlertBuilder
        // TODO Quando a Quantidade chegar a 0
        if (pedidoVO.getQuantidade() > 1) {

            alerta.show();
            this.refreshData(2);


        } else if (pedidoVO.getQuantidade() == 1){
            this.alertBuilderClickLongoAction();

            msg = activity.getString(R.string.SUCCESS_excluido, pedidoVO.toString());
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            System.out.println("Pedido Vazio");
            this.refreshData(2);
        }
        if (pedidoVO.getQuantidade() < 1) {
            PedidoVO pedidoNegativo = pedidoVO;
            daoPedido.excluir(pedidoNegativo);
            alerta.cancel();
            msg = activity.getString(R.string.ERROR_alterar, pedidoVO.toString());
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        }
    } // OK

    /**
     * Cria o Toast de acordo com o tipo de alteração no ClickCurto
     *
     * @param tipo: String
     */
    private void toastMessage(Integer tipo) {

        // Tipo de Alteração no Banco: REMOÇÃO
        if (Constantes.TIPO_ALTERACAO == 0) {

            // Conseguiu Alterar
            if (tipo.equals(Constantes.SUCCESS)) {
                msg = activity.getString(R.string.SUCCESS_alterado, pedidoVO.toString());
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                System.out.println("Removeu -1");
                adapter.notifyDataSetChanged();


                // Erro ao Tentar Alterar
            } else if (tipo.equals(Constantes.FAILED)) {
                msg = activity.getString(R.string.ERROR_alterar, pedidoVO.toString());
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                System.out.println();
                adapter.notifyDataSetChanged();
            }

            // Tipo Alteração no Banco: ADIÇÃO
        } else if (Constantes.TIPO_ALTERACAO == 1) {

            // Conseguiu Alterar
            if (tipo.equals(Constantes.SUCCESS)) {
                msg = activity.getString(R.string.SUCCESS_alterado, pedidoVO.toString());
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                System.out.println("Adicionou +1");
                adapter.notifyDataSetChanged();

                // Erro ao Tentar Alterar
            } else if (tipo.equals(Constantes.FAILED)) {
                msg = activity.getString(R.string.ERROR_alterar, pedidoVO.toString());
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                System.out.println("Erro ao tentar Adicionar +1");
                adapter.notifyDataSetChanged();
            }
        }

    } // OK

    /**
     * Excluir da Tela o Objecto como um todo do Banco de Dados
     */
    private void alertBuilderClickLongoAction() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle(R.string.MESSAGE_builderTitle);
        String alertaMessage = activity.getString(R.string.MESSAGE_ExcluirProduto, pedidoVO.toString());
        alerta.setMessage(alertaMessage);
        alerta.setIcon(android.R.drawable.ic_menu_delete);
        alerta.setNegativeButton(R.string.MESSAGE_fechar, (dialog, which) -> this.pedidoVO = null);
        alerta.setPositiveButton(R.string.MESSAGE_sim, (dialog, which) -> this.excluirAction());
        alerta.show();
    } // OK

    /**
     * Click Longo Excluir o Objeto
     */
    private void excluirAction() {
        Integer i = daoPedido.excluir(this.pedidoVO);

        if (i != null && i > 0) {
            msg = activity.getString(R.string.SUCCESS_excluido, pedidoVO.toString());
            Toast.makeText(activity, msg + "(" + i + ")", Toast.LENGTH_SHORT).show();
            Log.i("DB_INFO", "Sucesso ao Alterar: " + this.pedidoVO.toString());

//            this.adapter.remove(this.pedidoVO);
            this.refreshData(2);
        } else {
            msg = activity.getString(R.string.ERROR_excluir, pedidoVO.toString());
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            Log.e("DB_INFO", "Erro ao Excluir: " + this.pedidoVO.toString());
        }
    } // OK

    public void limparAction() {
        PedidoVO p;
        for (int i = 0; i < listPedido.size(); i++) {
            p = listPedido.get(i);
            daoPedido.excluirPorID(p.getId());
        }

        listPedido = daoPedido.listarTodos();
        adapter.notifyDataSetChanged();

        if (listPedido.size() == 0) {
            Toast.makeText(activity, "Lista Excluida", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "Erro ao Excluir a Lista", Toast.LENGTH_SHORT).show();
        }
    } // OK

    public void refreshData(int tipo) {
        if (tipo == 0) {
            adapter.notifyDataSetChanged();
        }
        if (tipo == 1) {
            listPedido = daoPedido.listarTodos();
        }
        if (tipo == 2) {
            listPedido.clear();
            adapter.clear();
            listPedido = daoPedido.listarTodos();
            adapter.addAll(listPedido);
            adapter.notifyDataSetChanged();
            this.configLabelTotal();
        }
    } // ok

}
