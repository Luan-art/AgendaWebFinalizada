package controller;

import java.io.IOException;
import java.sql.Date;

import model.Tarefa;

import dao.TarefaDao;
import jakarta.servlet.RequestDispatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CadastroDeTarefa")
public class CadTaskServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	TarefaDao tarefaDAO = new TarefaDao();
	Tarefa tarefa = new Tarefa();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); // Obtém a sessão existente (se não existir, retorna null)

		System.out.println(session.getAttribute("idUser"));
		
		tarefa.setIdUser((int) session.getAttribute("idUser"));
		
        if (session != null && session.getAttribute("idUser") != null) {
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/CadastroDeTarefa.jsp");
    		dispatcher.forward(request, response);
    		doPost(request, response);
        } else {
            // O usuário não está logado, redireciona-o para a página de login
            response.sendRedirect(request.getContextPath() + "/PaginaPrincipal");
        }
		
	
		
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String titulo = request.getParameter("titulo");
	    String descricao = request.getParameter("descricao");
	    String data_criacao = request.getParameter("data_criacao");
	    String data_conclusao = request.getParameter("data_conclusao");
	    String status = request.getParameter("status");

	    tarefa.setTitulo(titulo);
	    tarefa.setDescricao(descricao);

	    LocalDate dataCriacao = null;
	    LocalDate dataConclusao = null;

	    // Verifica se o formato está correto!!!
	    try {
	        dataCriacao = LocalDate.parse(data_criacao);
	        dataConclusao = LocalDate.parse(data_conclusao);

	        if (dataConclusao.isBefore(dataCriacao)) {
	            request.setAttribute("error", "Cadastro error");
	            showErrorPage(request, response, "Data de conclusão tem que ser posterior a de criação");
	        } else {
	            tarefa.setData_criacao(dataCriacao);
	            tarefa.setData_conclusao(dataConclusao);

	            if (status == null) {
	                tarefa.setStatus(false);
	            } else {
	                tarefa.setStatus(true);
	            }

	            try {
	                tarefaDAO.registerTask(tarefa);
	            } catch (ClassNotFoundException e) {
	                e.printStackTrace();
	            }

	            HttpSession session = request.getSession();
	            session.setAttribute("idUser", tarefa.getIdUser());
	            session.setAttribute("data_criacao", tarefa.getData_criacao().toString());
	            session.setAttribute("data_conclusao", tarefa.getData_conclusao().toString());

	            RequestDispatcher dispatcher = request.getRequestDispatcher("/PaginaPrincipal");
	            dispatcher.forward(request, response);
	        }
	    } catch (DateTimeParseException e) {
	        throw new ServletException(e);
	    }
	}

	
	  private void showErrorPage(HttpServletRequest request, HttpServletResponse response, String errorMessage)
	            throws ServletException, IOException {
	        request.setAttribute("error", errorMessage);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/CadastroDeTarefa.jsp");
	        dispatcher.forward(request, response);
	  }
}
