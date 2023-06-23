<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Tarefa" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalhes Tarefa</title>
<style>
            body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ccc;
        }

        /* Estilos para links */
        a {
            color: #333;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        /* Estilos específicos para os botões de ação */
        .action-buttons {
            margin-top: 10px;
            text-align: center;
        }

        .action-buttons a {
            display: inline-block;
            margin-right: 10px;
            padding: 8px 12px;
            background-color: #333;
            color: #fff;
            border-radius: 4px;
            transition: background-color 0.3s;
        }

        .action-buttons a:hover {
            background-color: #555;
        }

        .search-container {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
            text-align: center;
        }

     	.search-container input[type="text"] {
        	margin-left: 10px;
        	margin-right: 20px;
        	width: calc(100% - 10px);
   		}

        .hidden {
            display: none;
        }
</style>
<script>
    function filterTable() {
        var input = document.getElementById("searchInput");
        var filter = input.value.toLowerCase();
        var rows = document.getElementsByClassName("task-row");

        for (var i = 0; i < rows.length; i++) {
            var titleColumn = rows[i].getElementsByClassName("title-column")[0];
            var descriptionColumn = rows[i].getElementsByClassName("description-column")[0];

            if (titleColumn || descriptionColumn) {
                var titleText = titleColumn.textContent || titleColumn.innerText;
                var descriptionText = descriptionColumn.textContent || descriptionColumn.innerText;
                var matchTitle = titleText.toLowerCase().indexOf(filter) > -1;
                var matchDescription = descriptionText.toLowerCase().indexOf(filter) > -1;

                if (matchTitle || matchDescription) {
                    rows[i].classList.remove("hidden");
                } else {
                    rows[i].classList.add("hidden");
                }
            }
        }
    }
</script>
</head>
<body>
<div class="container">
    <h1>Tarefas:</h1>
</div>

<div class="search-container">
    <h2>Pesquise sua tarefa:</h2>
    <input type="text" name="pesquisa" id="searchInput" onkeyup="filterTable()" />
</div>

<%
List<Tarefa> lista = (List<Tarefa>) session.getAttribute("tarefas");

if (lista != null) {
    // Ordenar a lista em ordem alfabética pelo título da tarefa
    Collections.sort(lista, Comparator.comparing(Tarefa::getTitulo));

    for (Tarefa tarefa : lista) {
%>
<div class="task-row">
    <table>
        <tr>
            <td>Título</td>
            <td class="title-column"><%= tarefa.getTitulo() %></td>
        </tr>
        <tr>
            <td>Descrição</td>
            <td class="description-column"><%= tarefa.getDescricao() %></td>
        </tr>
        <tr>
            <td>Data de Criação</td>
            <td><%= tarefa.getData_criacao() %></td>
        </tr>
        <tr>
            <td>Data de Conclusão</td>
            <td><%= tarefa.getData_conclusao() %></td>
        </tr>
        <tr>
            <td>Status</td>
            <% if (tarefa.isStatus()) { %>
                <td>Concluído</td>
            <% } else { %>
                <td>Em andamento</td>
            <% } %>
        </tr>
    </table>
    <div class="action-buttons">
        <a href="/ListaTarefasWebLuan/EdicaoTarefa?idTarefa=<%= tarefa.getId() %>">Editar</a>
        <a href="/ListaTarefasWebLuan/DeletarTarefa?idTarefa=<%= tarefa.getId() %>&idUser=${idUser}">Deletar</a>
    </div>
</div>
<br>
<% }
} else { %>
<div>
    Sem tarefas cadastradas!
</div>
<% } %>

<div class="action-buttons">
    <a href="/ListaTarefasWebLuan/CadastroDeTarefa?idUser=${idUser}" style="font-size: 20px; text-decoration: none">Adicionar Tarefa</a>
</div>

<div class="action-buttons">
    <a href="/ListaTarefasWebLuan/Logout" style="font-size: 20px; text-decoration: none">Deslogar!</a>
</div>
</body>
</html>
