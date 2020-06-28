package com.atividades.minhapedida.model.bo;

import com.atividades.minhapedida.model.vo.PedidoVO;
import com.atividades.minhapedida.model.vo.ProdutoVO;

import org.jetbrains.annotations.NotNull;

public class BO {

    public static boolean validarNome(@NotNull ProdutoVO p) {
        return p.getNome() != null && !p.getNome().trim().isEmpty() && p.getNome().length() < 50;
    }

    public static boolean validarValor(@NotNull ProdutoVO p) {
        return p.getValor() != null && !p.getValor().isNaN()
                && p.getValor() <= 9999.99 && p.getValor() >= 0.01;
    }

    public static boolean validarQuantidadePedido(@NotNull PedidoVO p) {
        return p.getQuantidade() != null && p.getQuantidade() >= 1 && p.getQuantidade() <= 99;
    }

    public static boolean validarSpinner(ProdutoVO p) {
        return p != null;
    }
}

