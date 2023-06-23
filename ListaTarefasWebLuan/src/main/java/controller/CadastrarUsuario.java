package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import dao.UserDao;
import model.Usuario;

@WebServlet("/CadastrarUsuario")
public class CadastrarUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDao userDao;

    public void init() {
        userDao = new UserDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/CadastrarUser.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String email = request.getParameter("email");
        
        String senhacCripto = PasswordEncryptor.encryptPassword(senha);

        if (!validateInput(nome, login, senha, email)) {
            showErrorPage(request, response, "Todos os campos devem ser preenchidos");
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setLogin(login);
        usuario.setSenha(senhacCripto);
        usuario.setEmail(email);

        try {
            if (userDao.getUser(login).getId() != 0) {
                request.setAttribute("error", "Usuário já registrado");
                showErrorPage(request, response, "Usuário já registrado");
                return;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
	        userDao.registerUser(usuario);
            request.setAttribute("success", "Usuário cadastrado com sucesso");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Login.jsp");
            dispatcher.forward(request, response);
        } catch (ClassNotFoundException e) {
            showErrorPage(request, response, "Login já registrado");
        }
    }

    private boolean validateInput(String nome, String login, String senha, String email) {
        return !nome.isEmpty() && !login.isEmpty() && !senha.isEmpty() && !email.isEmpty();
        // Adicione validações adicionais aqui, se necessário
    }

    private void showErrorPage(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/CadastrarUser.jsp");
        dispatcher.forward(request, response);
    }
}
