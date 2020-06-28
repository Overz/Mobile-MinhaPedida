package com.atividades.minhapedida.model.vo;

import com.atividades.minhapedida.util.Constantes;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@DatabaseTable(tableName = "pedidos")
public class PedidoVO {

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, dataType = DataType.INTEGER_OBJ)
    private Integer id;
    @DatabaseField(columnName = "quantidadePedido", canBeNull = false, dataType = DataType.INTEGER_OBJ)
    private Integer quantidade;
    @DatabaseField(columnName = "subtotalPedido", canBeNull = false, dataType = DataType.DOUBLE_OBJ)
    private Double subTotal;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private ProdutoVO produtoVO;

    public PedidoVO(Integer id, Integer quantidade, Double subTotal, ProdutoVO produtoVO) {
        this.id = id;
        this.quantidade = quantidade;
        this.subTotal = subTotal;
        this.produtoVO = produtoVO;
    }

    public PedidoVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public ProdutoVO getProdutoVO() {
        return produtoVO;
    }

    public void setProdutoVO(ProdutoVO produtoVO) {
        this.produtoVO = produtoVO;
    }

    public void setProdutoVO(Object produtoVO) {
        try {
            this.produtoVO = (ProdutoVO) produtoVO;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    private String builderString() {
        switch (Constantes.TIPO_TOSTRING) {
            case 0:
                return produtoVO.toString() + " - Nº " + quantidade + " - " + subTotal + "$";
            case 1:
                return produtoVO.toString();
        }
        return null;
    }

    @Override @NotNull
    public String toString() {
        return builderString();
    }
}
