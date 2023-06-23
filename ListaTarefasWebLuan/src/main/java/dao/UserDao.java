package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Usuario;

public class UserDao {

    private int contadorSequencial = 0;

    public int registerUser(Usuario user) throws ClassNotFoundException {
        String SELECT_USUARIO = "SELECT max(id) FROM usuarios;";
        String INSERT_USERS_SQL = "INSERT INTO usuarios (id, nome, login, senha, email) VALUES (?,?,?,?,?);";

        try (Connection connection = getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(SELECT_USUARIO);
             ResultSet resultSet = selectStatement.executeQuery()) {

            if (resultSet.next()) {
                contadorSequencial = resultSet.getInt(1);
                contadorSequencial++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        int result = 0;

        try (Connection connection = getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_USERS_SQL)) {

            insertStatement.setInt(1, contadorSequencial);
            insertStatement.setString(2, user.getNome());
            insertStatement.setString(3, user.getLogin());
            insertStatement.setString(4, user.getSenha());
            insertStatement.setString(5, user.getEmail());

            result = insertStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Usuario getUser(String username) throws ClassNotFoundException {
        String SELECT_LOGIN = "SELECT id, nome, login, senha, email FROM usuarios WHERE login=?";

        Usuario user = new Usuario();

        try (Connection connection = getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(SELECT_LOGIN)) {

            selectStatement.setString(1, username);

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt("id"));
                    user.setNome(resultSet.getString("nome"));
                    user.setLogin(resultSet.getString("login"));
                    user.setSenha(resultSet.getString("senha"));
                    user.setEmail(resultSet.getString("email"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public Usuario getPassword(String password) throws ClassNotFoundException {
        String SELECT_PASSWORD = "SELECT id, nome, login, senha, email FROM usuarios WHERE senha=?";

        Usuario user = new Usuario();

        try (Connection connection = getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(SELECT_PASSWORD)) {

            selectStatement.setString(1, password);

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt("id"));
                    user.setNome(resultSet.getString("nome"));
                    user.setLogin(resultSet.getString("login"));
                    user.setSenha(resultSet.getString("senha"));
                    user.setEmail(resultSet.getString("email"));
                }
            }

        } catch (SQLException e) {
            user = null;
            e.printStackTrace();
        }

        return user;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/tasklist", "root", "");
    }
}
