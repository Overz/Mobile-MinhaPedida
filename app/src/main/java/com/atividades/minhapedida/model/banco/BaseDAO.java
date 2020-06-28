package com.atividades.minhapedida.model.banco;

import com.j256.ormlite.dao.Dao;

import java.util.List;

public interface BaseDAO<T> {

    List<T> listarTodos();

    <T> T consultar(String... values);

    T consultarPorID(Integer id);

    T cadastrar(T object);

    Dao.CreateOrUpdateStatus alterar(T object);

    Integer alterarColumnValues(T object);

    Integer excluir(T object);

    Integer excluirPorID(Integer id);

}
