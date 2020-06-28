package com.atividades.minhapedida.model.vo;

import com.atividades.minhapedida.util.Constantes;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "produtos")
public class ProdutoVO {

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, dataType = DataType.INTEGER_OBJ)
    private Integer id;
    @DatabaseField(columnName = "nomeProduto", canBeNull = false, dataType = DataType.STRING, width = 50)
    private String nome;
    @DatabaseField(columnName = "valorProduto", canBeNull = false, dataType = DataType.DOUBLE_OBJ, width = 9999)
    private Double valor;
    @DatabaseField(columnName = "statusProduto", canBeNull = false, dataType = DataType.BOOLEAN_OBJ)
    private Boolean status;

    public ProdutoVO(Integer id, String nome, Double valor, Boolean status) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.status = status;
    }

    public ProdutoVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setValor(String valor) {
        try {
            this.valor = Double.parseDouble(valor);
            if (this.valor.isNaN() || this.valor == null) {
                throw new NullPointerException("erro ao converter o valor da tela Gerenciar Produto");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    private String builderString() {
        String status;

        if (this.status) {
            status = "Ativo";
        } else {
            status = "Inativo";
        }

        switch (Constantes.TIPO_TOSTRING) {
            case 0:
                return nome + " - " + valor + "$";

            case 1:
                return nome + " - " + valor + "$ - " + status;

        }
        return null;
    }

    @Override
    public String toString() {
        return builderString();
    }
}
