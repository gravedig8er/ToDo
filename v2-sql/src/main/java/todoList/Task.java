package todoList;
// ОПИСАНИЕ ОДНОЙ ЗАДАЧИ

import java.time.LocalDateTime;

public class Task {
    private int id;
    private String description;
    private LocalDateTime time;
    private EStatus status;

    public Task(int id, String description, EStatus status, LocalDateTime time) { // ДЛЯ ЗАГРУЗКИ ИЗ БД
        this.id = id;
        this.description = description;
        this.status = status;
        this.time = time;
    }

    public Task(String description) { // ДЛЯ СОЗДАНИЯ ЗАДАЧИ И ДОБАВЛЕНИЯ В БД, ID генерит самостоятельно БД
        this.description = description;
        this.status = EStatus.TODO;
        this.time = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public EStatus getStatus() {
        return status;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void changeStatus(int index) {
        this.status = EStatus.values()[index];
    }

    @Override
    public String toString() {
        return  description + "\tStatus: " + status + "\tDate: " + time.toString();
    }
}
