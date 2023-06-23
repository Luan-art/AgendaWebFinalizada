					package controller;

import java.io.IOException;

import dao.UserDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;



@WebServlet(urlPatterns = {"/", "/Login"})

public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
    private UserDao userDao;

    public void init() {
        userDao = new UserDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    HttpSession session = request.getSession(false); // Obtém a sessão existente (se não existir, retorna null)
	    if (session != null && session.getAttribute("idUser") != null) {
	        // O usuário está logado, permita o acesso à página restrita
	        RequestDispatcher dispatcher = request.getRequestDispatcher("PaginaPrincipal");
	    	dispatcher.forward(request, response);
	    } else {
	        // O usuário não está logado, redirecione-o para a página de login

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Login.jsp");
    		dispatcher.forward(request, response);	
    		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String login = request.getParameter("login");
	    String senha = request.getParameter("senha");

	    String senhaCriptografada = PasswordEncryptor.encryptPassword(senha);

	    Usuario user = null;
	    Usuario user1 = null;

	    try {
	        user = userDao.getUser(login);
	        user1 = userDao.getPassword(senhaCriptografada);

	        if (user.getId() != 0  && user1.getId() != 0 && user1.getId() == user.getId()) {
	            HttpSession session = request.getSession();
	            session.setAttribute("idUser", user.getId()); // Guarda o id do usuário

	            RequestDispatcher dispatcher = request.getRequestDispatcher("PaginaPrincipal");
	            dispatcher.forward(request, response);
	        } else {
	            request.setAttribute("error", "Usuário ou senha incorretos");
                showErrorPage(request, response, "Senha ou usuario incorretos");
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Login.jsp");
	            dispatcher.forward(request, response);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
    private void showErrorPage(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Login.jsp");
        dispatcher.forward(request, response);
    }
	 
	}