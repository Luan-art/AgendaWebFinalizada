package controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;

import dao.TarefaDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jakarta.servlet.http.HttpSession;
import model.Tarefa;

@WebServlet("/EdicaoTarefa")

public class EdicaoTarefa extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	TarefaDao tarefaDao = new TarefaDao();
	Tarefa tarefa = new Tarefa();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession(); // Obtém a sessão existente (se não existir, retorna null)

	    if (session != null && session.getAttribute("idUser") != null) {
	        String paramId_tarefa = request.getParameter("idTarefa");
	        int id_tarefa = Integer.valueOf(paramId_tarefa);

	        try {
	            tarefa = tarefaDao.getById(id_tarefa);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }

	        System.out.println(tarefa.getTitulo());

	        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/EdicaoTarefa.jsp");

	        request.setAttribute("tarefa", tarefa);
	        rd.forward(request, response);
	        // O usuário está logado, permite o acesso à página principal
	        doPost(request, response);
	    } else {
	        // O usuário não está logado, redireciona-o para a página de login
	        response.sendRedirect(request.getContextPath() + "/PaginaPrincipal");
	    }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("Alterando Tarefa");

	    String paramId_tarefa = request.getParameter("idTarefa");
	    int idTarefa = Integer.valueOf(paramId_tarefa);

	    HttpSession session = request.getSession();
	    int id_usuario = (int) session.getAttribute("idUser");

	    String titulo = request.getParameter("titulo");
	    String descricao = request.getParameter("descricao");
	    String data_criacao = request.getParameter("data_criacao");
	    String data_conclusao = request.getParameter("data_conclusao");
	    String status = request.getParameter("status");

	    tarefa.setId(idTarefa);
	    tarefa.setTitulo(titulo);
	    tarefa.setDescricao(descricao);

	    if (status == null) {
	        tarefa.setStatus(false);
	    } else {
	        tarefa.setStatus(true);
	    }

	    LocalDate dataCriacao = null;
	    LocalDate dataConclusao = null;

	    // Verifica se o formato está correto!!!
	    try {
	        dataCriacao = LocalDate.parse(data_criacao);
	        dataConclusao = LocalDate.parse(data_conclusao);
	    } catch (DateTimeParseException e) {
	        throw new ServletException(e);
	    }

	    tarefa.setData_criacao(dataCriacao);
	    tarefa.setData_conclusao(dataConclusao);

	    try {
	        tarefaDao.editarTarefa(tarefa);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    request.setAttribute("idUser", id_usuario);

	    RequestDispatcher dispatcher = request.getRequestDispatcher("/PaginaPrincipal");
	    dispatcher.forward(request, response);
	}

	
	  private void showErrorPage(HttpServletRequest request, HttpServletResponse response, String errorMessage)
	            throws ServletException, IOException {
	        request.setAttribute("error", errorMessage);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/EdicaoTarefa.jsp");
	        dispatcher.forward(request, response);
	  }
	

}