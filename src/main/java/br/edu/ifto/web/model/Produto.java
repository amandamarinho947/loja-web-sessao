package br.edu.ifto.web.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Produto {
    private final int id;
    private final String nome;
    private final String descricao;
    private final BigDecimal preco;
    private final String icone;

    public Produto(int id, String nome, String descricao, BigDecimal preco, String icone) {
        if (id <= 0) {
            throw new IllegalArgumentException("O id do produto deve ser positivo.");
        }
        if (preco == null || preco.signum() < 0) {
            throw new IllegalArgumentException("O preço do produto não pode ser negativo.");
        }
        this.id = id;
        this.nome = Objects.requireNonNull(nome, "O nome do produto é obrigatório.");
        this.descricao = Objects.requireNonNull(descricao, "A descrição do produto é obrigatória.");
        this.preco = preco;
        this.icone = Objects.requireNonNull(icone, "O ícone do produto é obrigatório.");
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getIcone() {
        return icone;
    }
}
