package br.edu.ifto.web.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Carrinho {
    private final Map<Integer, ItemCarrinho> itens = new LinkedHashMap<>();

    public synchronized void adicionar(Produto produto) {
        ItemCarrinho item = itens.get(produto.getId());
        if (item == null) {
            itens.put(produto.getId(), new ItemCarrinho(produto, 1));
        } else {
            item.incrementarQuantidade();
        }
    }

    public synchronized void remover(int produtoId) {
        itens.remove(produtoId);
    }

    public synchronized Collection<ItemCarrinho> getItens() {
        return new ArrayList<>(itens.values());
    }

    public synchronized int getQuantidadeTotal() {
        return itens.values().stream().mapToInt(ItemCarrinho::getQuantidade).sum();
    }

    public synchronized BigDecimal getValorTotal() {
        return itens.values().stream()
                .map(ItemCarrinho::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public synchronized boolean isVazio() {
        return itens.isEmpty();
    }
}
