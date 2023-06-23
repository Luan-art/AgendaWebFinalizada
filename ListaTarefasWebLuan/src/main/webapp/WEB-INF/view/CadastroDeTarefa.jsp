<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Tarefas</title>
    <style>
        /* Estilos gerais */
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
      		margin: 100px;
        	padding: 100px;
        }

        .container {
        	max-width: 800px; 
        	width: 90%;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #333;
        }

        label {
            display: block;
            margin-bottom: 10px;
            color: #333;
        }

        input[type="text"],
        input[type="date"],
        textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="checkbox"] {
            margin-top: 10px;
            margin-bottom: 10px;
        }

        input[type="submit"] {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #333;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #555;
        }
        .register-link {
        	display: block;
        	font-size: 40px;
        	text-decoration: none;
        	text-align: center;
        	margin-top: 20px;
        	color: #333;
    	}

    	.register-link:hover {
        	color: #555;
    	}
    	
    	.error-message {
            color: red;
            margin-top: 10px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Registro de Nova Tarefa</h1>
    <form action="<%= request.getContextPath() %>/CadastroDeTarefa" method="post">
        <table style="width: 80%">
            <tr>
                <td>Título</td>
                <td><input type="text" name="titulo" required/></td>
            </tr>
            <tr>
                <td>Descrição</td>
                <td><input type="text" name="descricao" required/></td>
            </tr>
            <tr>
                <td>Data de Criação</td>
                <td><input type="date" name="data_criacao" required/></td>
            </tr>
            <tr>
                <td>Data de Conclusão</td>
                <td><input type="date" name="data_conclusao" required/></td>
            </tr>
            <tr>
                <td>Status</td>
                <td><input type="checkbox" name="status" />Concluído</td>
            </tr>
        </table>
        <br>
        <input type="submit" value="Cadastrar a Tarefa" />
        
         <% String errorMessage = (String) request.getAttribute("error"); %>
    	 <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
        	<div class="error-message">
            	<p><%= errorMessage %></p>
        	</div>
    	<% } %>
    
       	<a href="/ListaTarefasWebLuan/PaginaPrincipal" class="register-link">Voltar!</a>    
       	
    </form>
</div>
</body>
</html>
