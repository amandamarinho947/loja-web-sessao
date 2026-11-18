package br.edu.ifto.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.edu.ifto.web.model.Carrinho;

import java.io.IOException;

/**
 * Recurso: /carrinho
 */
@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        javax.servlet.http.HttpSession session = request.getSession(false);
        Object valorCarrinho = session == null ? null : session.getAttribute("carrinho");
        Carrinho carrinho = valorCarrinho instanceof Carrinho
                ? (Carrinho) valorCarrinho
                : new Carrinho();

        request.setAttribute("carrinho", carrinho);
        request.setAttribute("itensCarrinho", carrinho.getItens());
        request.setAttribute("quantidadeTotalItens", carrinho.getQuantidadeTotal());
        request.setAttribute("valorTotal", carrinho.getValorTotal());
        request.getRequestDispatcher("/WEB-INF/views/carrinho.jsp").forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        javax.servlet.http.HttpSession session = request.getSession(false);
        if (session != null) {
            synchronized (session) {
                session.removeAttribute("carrinho");
            }
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
