# Loja Web — carrinho com sessão

Aplicação didática de comércio eletrônico para praticar HTTP, Servlets, `HttpSession`, JSP, EL/JSTL e endpoints orientados a recursos.

## Implementação

- Produtos e usuários persistidos em banco de dados H2.
- Banco persistente em arquivo em `target/loja-web-db`.
- Configuração opcional por variáveis `JDBC_URL`, `JDBC_USER` e `JDBC_PASSWORD`.
- Produto e usuário consultados por JDBC/SQL; não são armazenados em listas Java.
- O carrinho de cada usuário fica no atributo `carrinho` da sua própria `HttpSession`.
- As páginas de produtos e carrinho são renderizadas com EL/JSTL, sem Scriptlets.
- A inclusão usa POST e redireciona para o carrinho; remoção e limpeza usam DELETE.

## Endpoints esperados

- GET `/produtos`
- GET `/carrinho`
- POST `/carrinho/itens`
- DELETE `/carrinho/itens?id={id}`
- DELETE `/carrinho`

## Restrições

- O carrinho deve ser mantido em `HttpSession`.
- O atributo da sessão deve se chamar `carrinho`.
- Não usar Scriptlets nas JSPs.
- Não usar variável `static` para armazenar carrinhos.
- Identificadores específicos devem ser enviados por query string quando aplicável.

## Executar

Compile o projeto com Java 21 ou superior e Maven:

```sh
mvn package
```

Implante `target/loja-web-sessao.war` em um contêiner compatível com Servlet 4.0 e Java EE 8, como Tomcat 9. Acesse a aplicação pelo contexto `/loja-web-sessao/`.

## Testar sessões independentes

Adicione produtos em uma janela do navegador e atualize ou navegue entre as páginas: o carrinho deve permanecer. Uma janela anônima ou outro navegador possui outra sessão e, portanto, outro carrinho. Use os botões de remoção e limpeza na página do carrinho para testar as requisições DELETE.
