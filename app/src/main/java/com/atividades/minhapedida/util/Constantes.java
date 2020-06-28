package com.atividades.minhapedida.util;

/**
 * Constantes para uso utilitario
 */
public class Constantes {

//    DATABASE FIELDS
    public static final String DB_ID = "id";
    public static final String DB_PRODUTO_NOME = "nomeProduto";
    public static final String DB_PRODUTO_VALOR = "valorProduto";
    public static final String DB_PRODUTO_STATUS = "statusProduto";
    public static final String DB_PEDIDO_QNTD = "quantidadePedido";
    public static final String DB_PEDIDO_SUBTOTAL = "subtotalPedido";
    public static final String DB_FK_PEDIDO_PRODUTO = "produtoVO_id";

    /**
     * 0 = Remocao
     * 1 = Adicao
     */
    public static int TIPO_ALTERACAO = 0;

    /**
     * 0 = ToString Geral
     * 1 = ToString Personalizado
     */
    public static int TIPO_TOSTRING = 0;

    public static final Integer SUCCESS = 1;
    public static final Integer FAILED = 0;

}
