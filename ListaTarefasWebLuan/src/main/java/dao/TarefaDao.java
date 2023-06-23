package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Tarefa;

public class TarefaDao {
    private int chaveSequencial = 0;

    public int registerTask(Tarefa tarefa) throws ClassNotFoundException {
        String SELECT_TAREFA = "SELECT max(idTarefa) FROM tarefas";

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tasklist?serverTimezone=UTC", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAREFA)) {

            System.out.println(preparedStatement);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    chaveSequencial = resultSet.getInt(1);
                    chaveSequencial++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String INSERT_TAREFAS_SQL = "INSERT INTO tarefas (idTarefa, titulo, descricao, data_criacao, data_conclusao, status, idUser) VALUES (?, ?, ?, ?, ?, ?, ?)";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tasklist?serverTimezone=UTC", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TAREFAS_SQL)) {

            preparedStatement.setInt(1, chaveSequencial);
            preparedStatement.setString(2, tarefa.getTitulo());
            preparedStatement.setString(3, tarefa.getDescricao());
            preparedStatement.setString(4, tarefa.getData_criacao().toString());
            preparedStatement.setString(5, tarefa.getData_conclusao().toString());
            preparedStatement.setBoolean(6, tarefa.isStatus());
            preparedStatement.setInt(7, tarefa.getIdUser());

            System.out.println(preparedStatement);

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Tarefa getById(int id) throws ClassNotFoundException {
        String SELECT_TAREFA = "SELECT idTarefa, titulo, descricao, data_criacao, data_conclusao, status, idUser FROM tarefas WHERE idTarefa = ?";

        Tarefa tarefa = null;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tasklist?serverTimezone=UTC", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAREFA)) {

            preparedStatement.setInt(1, id);

            System.out.println(preparedStatement);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    tarefa = new Tarefa();
                    tarefa.setId(resultSet.getInt("idTarefa"));
                    tarefa.setTitulo(resultSet.getString("titulo"));
                    tarefa.setDescricao(resultSet.getString("descricao"));

                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDateCriacao = LocalDate.parse(resultSet.getString("data_criacao"), dateFormatter);
                    tarefa.setData_criacao(localDateCriacao);

                    String dataConclusao = resultSet.getString("data_conclusao");
                    if (dataConclusao != null) {
                        LocalDate localDateConclusao = LocalDate.parse(dataConclusao, dateFormatter);
                        tarefa.setData_conclusao(localDateConclusao);
                    }

                    tarefa.setStatus(resultSet.getBoolean("status"));
                    tarefa.setIdUser(resultSet.getInt("idUser"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tarefa;
    }

    public List<Tarefa> buscarPorIdUser(int id) throws ClassNotFoundException {
        String SELECT_TAREFA = "SELECT idTarefa, titulo, descricao, data_criacao, data_conclusao, status, idUser FROM tarefas WHERE idUser = ?";
        List<Tarefa> tarefas = new ArrayList<>();

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tasklist?serverTimezone=UTC", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAREFA)) {

            preparedStatement.setInt(1, id);

            System.out.println(preparedStatement);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Tarefa tarefa = new Tarefa();
                    tarefa.setId(resultSet.getInt("idTarefa"));
                    tarefa.setTitulo(resultSet.getString("titulo"));
                    tarefa.setDescricao(resultSet.getString("descricao"));

                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDateCriacao = LocalDate.parse(resultSet.getString("data_criacao"), dateFormatter);
                    tarefa.setData_criacao(localDateCriacao);

                    String dataConclusao = resultSet.getString("data_conclusao");
                    if (dataConclusao != null) {
                        LocalDate localDateConclusao = LocalDate.parse(dataConclusao, dateFormatter);
                        tarefa.setData_conclusao(localDateConclusao);
                    }

                    tarefa.setStatus(resultSet.getBoolean("status"));
                    tarefa.setIdUser(resultSet.getInt("idUser"));

                    tarefas.add(tarefa);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tarefas;
    }

    public int deletarTarefa(int idTarefa) throws ClassNotFoundException {
        String REMOVE_TAREFA = "DELETE FROM tarefas WHERE idTarefa = ?";
        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tasklist?serverTimezone=UTC", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_TAREFA)) {

            preparedStatement.setInt(1, idTarefa);

            System.out.println(preparedStatement);

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int editarTarefa(Tarefa tarefa) throws ClassNotFoundException {
        String UPDATE_TAREFA = "UPDATE tarefas SET titulo=?, descricao=?, data_criacao=?, data_conclusao=?, status=?" +
                " WHERE idTarefa = ?;";

        Class.forName("com.mysql.jdbc.Driver");

        int result = 0;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tasklist?serverTimezone=UTC", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TAREFA)) {

            preparedStatement.setString(1, tarefa.getTitulo());
            preparedStatement.setString(2, tarefa.getDescricao());
            preparedStatement.setString(3, tarefa.getData_criacao().toString());
            preparedStatement.setString(4, tarefa.getData_conclusao().toString());
            preparedStatement.setBoolean(5, tarefa.isStatus());
            preparedStatement.setInt(6, tarefa.getId());

            System.out.println(preparedStatement);

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
