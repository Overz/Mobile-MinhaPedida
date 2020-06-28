package com.atividades.minhapedida.model.banco.helpers;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class DaoHelper<T> {

    private static MyORMLiteHelper mInstance = null;
    private Class className;

    public DaoHelper(Context c, Class className) {
        this.className = className;
        if (mInstance == null) mInstance = new MyORMLiteHelper(c.getApplicationContext());
    }

    protected Dao<T, Integer> getDao() {
        try {
            return mInstance.getDao(className);
        } catch (SQLException e) {
            System.out.println(e.getClass().getSimpleName() + "\n"
                    + e.getMessage() + "\n"
                    + e.getCause() + "\n"
                    + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getClass().getSimpleName() + "\n"
                    + e.getMessage() + "\n"
                    + e.getCause() + "\n"
                    + e.getLocalizedMessage());
        }
        return null;
    }
}
