package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.TarefaDao;

@WebServlet("/DeletarTarefa")
public class DeletTaskServelet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(); // Obtém a sessão existente (se não existir, retorna null)

        if (session != null && session.getAttribute("idUser") != null) {
            // O usuário está logado, permite o acesso à página principal
            doPost(request, response);
        } else {
            // O usuário não está logado, redireciona-o para a página de login
            response.sendRedirect(request.getContextPath() + "/PaginaPrincipal");
        }
        
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        int id_tarefa = Integer.parseInt(request.getParameter("idTarefa"));
        int id_usuario = (int) session.getAttribute("idUser");

        System.out.println(id_tarefa);

        TarefaDao tarefaDao = new TarefaDao();

        try {
            tarefaDao.deletarTarefa(id_tarefa);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/PaginaPrincipal");
        dispatcher.forward(request, response);
    }
}
