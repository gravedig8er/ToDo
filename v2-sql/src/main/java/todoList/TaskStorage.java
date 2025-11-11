package todoList;
// CRUD для БД
/*
* Create - создать
* Read - получить все задачи
* Update - изменить статус
* Delete - удалить задачу*/

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class TaskStorage {
    private static final String DB_URL = "jdbc:sqlite:tasks.db";

    public TaskStorage() {
        try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement()) {
            String sql = """
                    CREATE TABLE IF NOT EXISTS tasks (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        description TEXT NOT NULL,
                        status TEXT NOT NULL,
                        time text NOT NULL
                    );
                    """;
            stmt.execute(sql); // выполнить
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    public ArrayList<Task> readAll() {
        ArrayList<Task> list = new ArrayList<>();

        String sql = "SELECT id, description, status, time FROM tasks ORDER BY id;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                EStatus status = EStatus.valueOf(rs.getString("status"));
                LocalDateTime time = LocalDateTime.parse(rs.getString("time"));

                list.add(new Task(id, description, status, time));
            }
        } catch(SQLException e) {
            System.out.println("Ошибка при чтении из БД: " + e.getMessage());
        }
        return list;
    }

    public void insert(Task task) {
        String sql = "INSERT INTO tasks(description, status, time) VALUES(?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, task.getDescription());
            pstmt.setString(2, task.getStatus().name());
            pstmt.setString(3, task.getTime().toString());
            pstmt.executeUpdate();

        } catch(SQLException e) {
            System.out.println("Ошибка при добавлении задачи: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tasks WHERE ID = ?;";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении: " + e.getMessage());
        }
    }

    public void updateStatus(int id, EStatus status) {
        String sql = "UPDATE tasks SET status = ? WHERE id = ?;";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status.name());
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении статуса: " + e.getMessage());
        }
    }
}
