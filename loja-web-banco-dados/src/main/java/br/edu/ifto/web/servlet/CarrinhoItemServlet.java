package br.edu.ifto.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.edu.ifto.web.dao.ProdutoRepository;
import br.edu.ifto.web.model.Carrinho;
import br.edu.ifto.web.model.Produto;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;

/**
 * Recurso: /carrinho/itens
 */
@WebServlet("/carrinho/itens")
public class CarrinhoItemServlet extends HttpServlet {
    private final ProdutoRepository repository = new ProdutoRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer id = lerId(request.getParameter("id"));
        if (id == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "O id do produto é inválido.");
            return;
        }

        Produto produto;
        try {
            produto = repository.buscarPorId(id);
        } catch (SQLException exception) {
            throw new ServletException("Erro ao consultar o produto.", exception);
        }
        if (produto == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Produto não encontrado.");
            return;
        }

        javax.servlet.http.HttpSession session = request.getSession();
        synchronized (session) {
            Object valorCarrinho = session.getAttribute("carrinho");
            Carrinho carrinho;
            if (valorCarrinho instanceof Carrinho) {
                carrinho = (Carrinho) valorCarrinho;
            } else {
                carrinho = new Carrinho();
                session.setAttribute("carrinho", carrinho);
            }
            carrinho.adicionar(produto);
        }

        response.sendRedirect(request.getContextPath() + "/carrinho");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer id = lerId(request.getParameter("id"));
        if (id == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "O id do produto é inválido.");
            return;
        }
        Produto produto;
        try {
            produto = repository.buscarPorId(id);
        } catch (SQLException exception) {
            throw new ServletException("Erro ao consultar o produto.", exception);
        }
        if (produto == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Produto não encontrado.");
            return;
        }

        javax.servlet.http.HttpSession session = request.getSession(false);
        if (session != null) {
            synchronized (session) {
                Object valorCarrinho = session.getAttribute("carrinho");
                if (valorCarrinho instanceof Carrinho) {
                    ((Carrinho) valorCarrinho).remover(id);
                }
            }
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    private Integer lerId(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(valor);
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}