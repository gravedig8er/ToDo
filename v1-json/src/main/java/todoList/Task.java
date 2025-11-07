package todoList;
// ОПИСАНИЕ ОДНОЙ ЗАДАЧИ

import java.time.LocalDateTime;

public class Task {
    private int id;
    private String description;
    LocalDateTime time;
    private EStatus status;

    public Task(int id, String description, EStatus status) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.time = LocalDateTime.now();
    }

    public void changeStatus(int index) {
        this.status = EStatus.values()[index];
    }
    @Override
    public String toString() {
        return  description + "\tStatus: " + status + "\tDate: " + time.toString();
    }
}
