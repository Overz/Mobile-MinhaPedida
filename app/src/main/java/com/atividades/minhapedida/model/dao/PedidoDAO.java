package com.atividades.minhapedida.model.dao;

import android.content.Context;

import com.atividades.minhapedida.model.banco.BaseDAO;
import com.atividades.minhapedida.model.banco.helpers.DaoHelper;
import com.atividades.minhapedida.model.vo.PedidoVO;
import com.atividades.minhapedida.util.Constantes;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;

public class PedidoDAO extends DaoHelper<PedidoVO> implements BaseDAO<PedidoVO> {
    public PedidoDAO(Context c, Class className) {
        super(c, className);
    }

    @Override
    public List<PedidoVO> listarTodos() {
        try {
            return getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + "\n"
                    + e.getCause() + "\n"
                    + e.getNextException() + "\n"
                    + e.getClass().getSimpleName()
            );
        }
        return null;
    }

    @Override
    public Long consultar(@NotNull String... values) {
        try {
            if (values.length > 0) {
                String lastValue = values[values.length - 1];
                if (lastValue.equals(Constantes.DB_PEDIDO_SUBTOTAL)) {
                    return getDao().queryRawValue("select SUM(" + Constantes.DB_PEDIDO_SUBTOTAL + ") from PEDIDOS");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n"
                    + e.getCause() + "\n"
                    + e.getNextException() + "\n"
                    + e.getClass().getSimpleName()
            );
        }
        return null;
    }

    @Override
    public PedidoVO consultarPorID(@NotNull Integer id) {
        try {
            return getDao().queryForId(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n"
                    + e.getCause() + "\n"
                    + e.getNextException() + "\n"
                    + e.getClass().getSimpleName()
            );
        }
        return null;
    }

    @Override
    public PedidoVO cadastrar(@NotNull PedidoVO object) {
        try {
            return getDao().createIfNotExists(object);
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n"
                    + e.getCause() + "\n"
                    + e.getNextException() + "\n"
                    + e.getClass().getSimpleName()
            );
        }
        return null;
    }

    @Override
    public Dao.CreateOrUpdateStatus alterar(@NotNull PedidoVO object) {
        try {
            return getDao().createOrUpdate(object);
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n"
                    + e.getCause() + "\n"
                    + e.getNextException() + "\n"
                    + e.getClass().getSimpleName()
            );
        }
        return null;
    }

    @Override
    public Integer alterarColumnValues(@NotNull PedidoVO object) {
        try {

            UpdateBuilder<PedidoVO, Integer> updateBuilder = getDao().updateBuilder();
            Where<PedidoVO, Integer> where = updateBuilder.where();
            where.eq(Constantes.DB_ID, object.getId());
            updateBuilder.setWhere(where);
            double valor;

            if (Constantes.TIPO_ALTERACAO == 0) {

                int quantidadeRemocao = (object.getQuantidade() - 1);
                valor = (object.getSubTotal() - object.getProdutoVO().getValor());

                updateBuilder.updateColumnValue(Constantes.DB_PEDIDO_QNTD, quantidadeRemocao);
                updateBuilder.updateColumnValue(Constantes.DB_PEDIDO_SUBTOTAL, valor);

                return updateBuilder.update();


            } else if (Constantes.TIPO_ALTERACAO == 1) {

                int quantidadeAdicao = (object.getQuantidade() + 1);
                valor = (object.getSubTotal() + object.getProdutoVO().getValor());

                updateBuilder.updateColumnValue(Constantes.DB_PEDIDO_QNTD, quantidadeAdicao);
                updateBuilder.updateColumnValue(Constantes.DB_PEDIDO_SUBTOTAL, valor);

                return updateBuilder.update();

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n"
                    + e.getCause() + "\n"
                    + e.getNextException() + "\n"
                    + e.getClass().getSimpleName()
            );
        }
        return null;
    }

    @Override
    public Integer excluir(@NotNull PedidoVO object) {
        try {
            return getDao().delete(object);
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n"
                    + e.getCause() + "\n"
                    + e.getNextException() + "\n"
                    + e.getClass().getSimpleName()
            );
        }
        return null;
    }

    @Override
    public Integer excluirPorID(@NotNull Integer id) {
        try {
            return getDao().deleteById(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n"
                    + e.getCause() + "\n"
                    + e.getNextException() + "\n"
                    + e.getClass().getSimpleName()
            );
        }
        return null;
    }

}
