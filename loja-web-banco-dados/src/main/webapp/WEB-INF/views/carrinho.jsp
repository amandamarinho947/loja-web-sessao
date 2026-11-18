<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrinho - Loja Web</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<header class="topo">
    <div class="container topo-conteudo">
        <h1>Loja Web</h1>
        <nav class="menu">
            <a href="${pageContext.request.contextPath}/index.jsp">Início</a>
            <a href="${pageContext.request.contextPath}/produtos">Produtos</a>
            <a class="ativo" href="${pageContext.request.contextPath}/carrinho">Carrinho
                <span class="contador">${quantidadeTotalItens}</span>
            </a>
        </nav>
    </div>
</header>

<main class="container secao">
    <div class="titulo-pagina">
        <div>
            <h2>Meu carrinho</h2>
            <p>Seus produtos permanecem aqui durante esta sessão.</p>
        </div>
    </div>

    <div class="carrinho-layout">
        <section class="carrinho-produtos">
            <c:choose>
                <c:when test="${empty itensCarrinho}">
                    <p class="aviso">Seu carrinho está vazio. <a href="${pageContext.request.contextPath}/produtos">Ver produtos</a>.</p>
                </c:when>
                <c:otherwise>
                    <c:forEach var="item" items="${itensCarrinho}">
                        <article class="item-carrinho">
                            <div class="item-icone">${item.produto.icone}</div>
                            <div class="item-info">
                                <h3>${item.produto.nome}</h3>
                                <span>
                                    <fmt:formatNumber value="${item.produto.preco}" type="currency"
                                                      currencySymbol="R$ " minFractionDigits="2" maxFractionDigits="2"/>
                                </span>
                            </div>
                            <div class="quantidade"><span>Quantidade</span><strong>${item.quantidade}</strong></div>
                            <div class="subtotal">
                                <span>Subtotal</span>
                                <strong>
                                    <fmt:formatNumber value="${item.subtotal}" type="currency"
                                                      currencySymbol="R$ " minFractionDigits="2" maxFractionDigits="2"/>
                                </strong>
                            </div>
                            <button class="btn-remover" type="button" data-id="${item.produto.id}">Remover</button>
                        </article>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </section>

        <aside class="resumo">
            <h3>Resumo do pedido</h3>
            <div class="resumo-linha"><span>Itens</span><strong>${quantidadeTotalItens}</strong></div>
            <div class="resumo-linha total">
                <span>Total</span>
                <strong>
                    <fmt:formatNumber value="${valorTotal}" type="currency"
                                      currencySymbol="R$ " minFractionDigits="2" maxFractionDigits="2"/>
                </strong>
            </div>
            <a class="btn btn-secundario btn-bloco" href="${pageContext.request.contextPath}/produtos">Continuar comprando</a>
            <button class="btn btn-perigo btn-bloco" id="limpar-carrinho" type="button">Limpar carrinho</button>
        </aside>
    </div>
</main>

<footer><div class="container">Programação para Web I</div></footer>
<script>
    const contextPath = '${pageContext.request.contextPath}';

    async function enviarDelete(url) {
        const resposta = await fetch(url, { method: 'DELETE' });
        if (!resposta.ok) {
            throw new Error('Não foi possível atualizar o carrinho.');
        }
        window.location.reload();
    }

    document.querySelectorAll('.btn-remover').forEach((botao) => {
        botao.addEventListener('click', async () => {
            try {
                const id = encodeURIComponent(botao.dataset.id);
                await enviarDelete(contextPath + '/carrinho/itens?id=' + id);
            } catch (erro) {
                window.alert(erro.message);
            }
        });
    });

    document.querySelector('#limpar-carrinho').addEventListener('click', async () => {
        try {
            await enviarDelete(contextPath + '/carrinho');
        } catch (erro) {
            window.alert(erro.message);
        }
    });
</script>
</body>
</html>
