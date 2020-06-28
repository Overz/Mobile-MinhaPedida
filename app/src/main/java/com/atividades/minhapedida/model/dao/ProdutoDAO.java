package com.atividades.minhapedida.model.dao;

import android.content.Context;

import com.atividades.minhapedida.model.banco.BaseDAO;
import com.atividades.minhapedida.model.banco.helpers.DaoHelper;
import com.atividades.minhapedida.model.vo.ProdutoVO;
import com.atividades.minhapedida.util.Constantes;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;

public class ProdutoDAO extends DaoHelper<ProdutoVO> implements BaseDAO<ProdutoVO> {

    public ProdutoDAO(Context c, Class className) {
        super(c, className);
    }

    @Override
    public List<ProdutoVO> listarTodos() {
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
    public List<ProdutoVO> consultar(@NotNull String... values) {
        try {
            if (values.length > 0) {
                String lastValue = values[values.length - 1];
                if (lastValue.equals(Constantes.DB_PRODUTO_STATUS)) {
                    return getDao().queryBuilder()
                            .orderBy(Constantes.DB_PRODUTO_NOME, true)
                            .where().eq(lastValue, true).query();
                } else{
                    return getDao().queryBuilder().selectColumns(values).query();
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

    public ProdutoVO testeConsultaPorNome(String... values) {
        try {

            QueryBuilder<ProdutoVO, Integer> queryBuilder = getDao().queryBuilder();
            UpdateBuilder<ProdutoVO, Integer> updateBuilder = getDao().updateBuilder();
            Where<ProdutoVO, Integer> whereQuery = queryBuilder.where();

            whereQuery.like(Constantes.DB_PRODUTO_NOME, values);
            List<ProdutoVO> list = queryBuilder.query();


            Where<ProdutoVO, Integer> whereUpdate = updateBuilder.where();












            getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ProdutoVO consultarPorID(@NotNull Integer id) {
        try {
            return getDao().queryForId(id);
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
    public ProdutoVO cadastrar(@NotNull ProdutoVO object) {
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
    public Dao.CreateOrUpdateStatus alterar(@NotNull ProdutoVO object) {
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
    public Integer alterarColumnValues(ProdutoVO object) {
        return null;
    }

    @Override
    public Integer excluir(@NotNull ProdutoVO object) {
        try {
            UpdateBuilder<ProdutoVO, Integer> updateBuilder = getDao().updateBuilder();
            updateBuilder.where().eq(Constantes.DB_ID, object.getId());
            updateBuilder.updateColumnValue(Constantes.DB_PRODUTO_STATUS, false);
            return updateBuilder.update();
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
