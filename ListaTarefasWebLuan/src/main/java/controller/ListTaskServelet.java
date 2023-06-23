package controller;

import java.io.IOException;
import java.util.List;

import dao.TarefaDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Tarefa;

@WebServlet("/PaginaPrincipal")
public class ListTaskServelet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String PRINCIPAL_JSP = "/WEB-INF/view/Principal.jsp";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    	HttpSession session = request.getSession(); // Obtém a sessão existente (se não existir, retorna null)

        if (session != null && session.getAttribute("idUser") != null) {
            // O usuário está logado, permite o acesso à página principal
            doPost(request, response);
        } else {
            // O usuário não está logado, redireciona-o para a página de login
            response.sendRedirect(request.getContextPath() + "/Login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	HttpSession session = request.getSession();
    	
    	int userId = (int) session.getAttribute("idUser");
    	
        TarefaDao tarefaDao = new TarefaDao();
        List<Tarefa> tarefas = null;

        try {
            tarefas = tarefaDao.buscarPorIdUser(userId);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Ocorreu um erro ao buscar as tarefas do usuário.");
        }
        
        session.setAttribute("tarefas", tarefas);

        RequestDispatcher dispatcher = request.getRequestDispatcher(PRINCIPAL_JSP);
        dispatcher.forward(request, response);
    }


}
