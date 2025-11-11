package todoList;
// минус реализации - считываем каждый раз и создаем новые объекты list. Могли бы сразу в нем изменять данные
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.IntStream;

public class TaskManager {
    private TaskStorage storage; // через него обращаемся к бд
    private ArrayList<Task> list;

    public TaskManager() {
        this.storage = new TaskStorage();
        this.list = storage.readAll();
    }

    public void showAllTasks() {
        if (list.isEmpty()) {
            System.out.println("Список пуст!");
            return;
        }
        System.out.println("\nТекущие задачи:");
        IntStream.range(0, list.size()).forEach(i-> System.out.println("\t" + (i+1) + ". " + list.get(i)));
    }

    public void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите описание задачи:");
        String desc = scanner.nextLine();

        Task newTask = new Task(desc);
        storage.insert(newTask);
        list = storage.readAll();
    }

    public void deleteTask() {
        showAllTasks();
        if (list.isEmpty()) return;

        Scanner scanner = new Scanner(System.in);
        try {
            int index = scanner.nextInt() - 1;
            if (index < 0 || index >= list.size()) {
                System.out.println("Неверный номер");
                return;
            }

            int id = list.get(index).getId(); // берем айди по задаче, а не по тому, как он вывел их
            storage.delete(id);
            list = storage.readAll();
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода");
        }
    }

    public void changeStatusTask() {
        showAllTasks();
        if (list.isEmpty()) return;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер задачи");
        try {
            int index = scanner.nextInt() - 1;

            if (index < 0 || index >= list.size()) {
                System.out.println("Неверный номер");
                return;
            }

            System.out.println("Выберите новый статус");
            System.out.println("\t1. TODO\n\t2. IN_PROGRESS\n\t3. DONE");
            int st = scanner.nextInt();

            if (st < 1 || st > 3) {
                System.out.println("Такого статуса не существует");
                return;
            }

            EStatus status = EStatus.values()[st - 1];
            int id = list.get(index).getId();
            storage.updateStatus(id, status);
            list = storage.readAll();
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода");
        }

    }
}
