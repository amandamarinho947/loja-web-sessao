package br.edu.ifto.web.servlet;
import br.edu.ifto.web.dao.UsuarioRepository;
import br.edu.ifto.web.model.Usuario;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UsuarioRepository repository = new UsuarioRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioLogado") instanceof Usuario) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Usuario usuario;
        try {
            usuario = repository.autenticar(email, senha);
        } catch (SQLException exception) {
            throw new ServletException("Erro ao consultar os usuários.", exception);
        }

        if (usuario == null) {
            request.setAttribute("erro", true);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("usuarioLogado", usuario);
        response.sendRedirect(request.getContextPath() + "/");
    }
}