<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Tarefa" %>
<%@ page import="javax.servlet.http.HttpSession" %> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Tarefa</title>
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
        textarea,
        input[type="date"] {
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
    <script>
   	function validarDatas() {
        var dataCriacao = new Date(document.getElementById("data_criacao").value);
        var dataConclusao = new Date(document.getElementById("data_conclusao").value);

        if (dataConclusao <= dataCriacao) {
            document.getElementById("error-message").innerText = "Data de conclusão inválida. Deve ser posterior à data de criação.";
            return false;
        }

        return true;
    }
    </script>
</head>
<body>
    <div class="container">
        <h1>Edição de Tarefa</h1>

        <form action="<%= request.getContextPath() %>/EdicaoTarefa" method="post" onsubmit="return validarDatas()">
            <label for="titulo">Título:</label>
            <input type="text" id="titulo" name="titulo" required value="${tarefa.titulo}"><br>

            <label for="descricao">Descrição:</label>
            <input type ="text" id="descricao" name="descricao" required value="${tarefa.descricao}" ><br>

            <label for="data_criacao">Data de Criação:</label>
            <input type="date" id="data_criacao" name="data_criacao" required value="${tarefa.data_criacao}"><br>

            <label for="data_conclusao">Data de Conclusão:</label>
            <input type="date" id="data_conclusao" name="data_conclusao" required value="${tarefa.data_conclusao}"><br>

            <label for="status">Status:</label>
            <%
                Tarefa tarefa = (Tarefa) request.getAttribute("tarefa");
                if (tarefa.isStatus()) {
            %>
            <input type="checkbox" name="status" checked>Concluído
            <%
                } else {
            %>
            <input type="checkbox" name="status">Pendente
            <%
                }
            %>
            <br>

            <input type="hidden" name="idTarefa" value="${tarefa.id}">
            <input type="hidden" name="idUser" value="${tarefa.idUser}">
            <input type="submit" value="Salvar">
            
            <div id="error-message" class="error-message">
                <% String errorMessage = (String) request.getAttribute("error"); %>
                <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
                <p><%= errorMessage %></p>
                <% } %>
            </div>
            
            <a href="/ListaTarefasWebLuan/PaginaPrincipal" class="register-link">Voltar!</a>    
        </form>
    </div>
</body>
</html>
