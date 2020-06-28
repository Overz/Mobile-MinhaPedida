package com.atividades.minhapedida.model.banco.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.atividades.minhapedida.model.vo.PedidoVO;
import com.atividades.minhapedida.model.vo.ProdutoVO;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class MyORMLiteHelper extends OrmLiteSqliteOpenHelper {
    //Configuração do banco de dados
    private static final String DATABASE_NAME = "avaliacao.db";
    private static final int DATABASE_VERSION = 1;

    MyORMLiteHelper(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, ProdutoVO.class);
            TableUtils.createTableIfNotExists(connectionSource, PedidoVO.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + "\n"
                    + e.getCause() + "\n"
                    + e.getNextException() + "\n"
                    + e.getClass().getSimpleName()
            );
            System.out.println("MyORMLiteHelper - Erro em Classe");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, ProdutoVO.class, true);
            TableUtils.dropTable(connectionSource, ProdutoVO.class, true);
            this.onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + "\n"
                    + e.getCause() + "\n"
                    + e.getNextException() + "\n"
                    + e.getClass().getSimpleName()
            );
            System.out.println("MyORMLiteHelper - Erro em Classe");
        }
    }

}