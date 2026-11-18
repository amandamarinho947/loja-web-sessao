package br.edu.ifto.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import br.edu.ifto.web.dao.ProdutoRepository;
import br.edu.ifto.web.model.Carrinho;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Recurso: /produtos
 */
@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {
    private final ProdutoRepository repository = new ProdutoRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("produtos", repository.listarTodos());
        } catch (SQLException exception) {
            throw new ServletException("Erro ao consultar os produtos.", exception);
        }

        Carrinho carrinho = obterCarrinho(request);
        request.setAttribute("quantidadeTotalItens", carrinho.getQuantidadeTotal());

        request.getRequestDispatcher("/WEB-INF/views/produtos.jsp").forward(request, response);
    }

    private Carrinho obterCarrinho(HttpServletRequest request) {
        javax.servlet.http.HttpSession session = request.getSession(false);
        Object carrinho = session == null ? null : session.getAttribute("carrinho");
        return carrinho instanceof Carrinho ? (Carrinho) carrinho : new Carrinho();
    }
}
