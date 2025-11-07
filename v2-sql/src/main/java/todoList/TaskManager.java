package todoList;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.IntStream;

public class TaskManager {
    ArrayList<Task> list;
    TaskStorage ts;

    public TaskManager() {
        this.ts = new TaskStorage();
        this.list = ts.read();
    }
    public void showAllTasks() {
        if (list.isEmpty()) {
            System.out.println("Список пуст, добавьте задачи");
            return;
        }
        System.out.println("\tСписок задач:");
        // наверное нужно выводить не айди задачи, а итерирорвать с единицы
        // выглядит не очень при удалении какой-либо задачи
        IntStream.range(0, list.size())
                .forEach(i -> System.out.println("\t" + (i+1) + ". " + list.get(i)));
    }

    public void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Опишите задачу: ");
        String taskDescription = scanner.nextLine();

        Task task = new Task(list.size(), taskDescription, EStatus.values()[0]);
        list.add(task);
    }

    public void deleteTask() {
        showAllTasks();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите номер задачи для удаления: ");
        int toDelete = -1;
        while (true) {
            try {
                toDelete = scanner.nextInt() - 1;

                if (toDelete < 0 || toDelete >= list.size()) {
                    System.out.println("Такой задачи нет, попробуйте снова.");
                    continue;
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введено не число. Повторите попытку.");
                scanner.nextLine(); // очистка буфера
            }
        }
        list.remove(toDelete);
        System.out.println("Задача " + (toDelete+1) + " успешно удалена");
    }

    public void changeStatusTask() {
        showAllTasks();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите номер задачи: ");
        int toChange = -1;
        while (true) {
            try {
                toChange = scanner.nextInt() - 1;

                if (toChange < 0 || toChange >= list.size()) {
                    System.out.println("Такой задачи нет, попробуйте снова.");
                    continue;
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("Такой задачи нет, попробуйте снова");
                scanner.nextLine();
            }

        }

        System.out.println("На какой статус необходимо заменить?");
        System.out.println("\t1. TODO");
        System.out.println("\t2. IN_PROGRESS");
        System.out.println("\t3. DONE");
        int status = -1;


        while (true) {
            try {
                status = scanner.nextInt() - 1;

                if (status < 0 || status >= EStatus.values().length) {
                    System.out.println("Такого статуса нет, попробуйте снова");
                    continue;
                }

                break;
            } catch(InputMismatchException e) {
                System.out.println("Такого статуса нет, попробуйте снова");
                scanner.nextLine();
            }
        }
        list.get(toChange).changeStatus(status);
    }

    public void quit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Хотите сохранить задачи?");
        System.out.println("\t1. Да");
        System.out.println("\t2. Нет");

        int userMeaning = -1;
        while (true) {
            try {
                userMeaning = scanner.nextInt();

                break;
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод, повторите попытку");
                scanner.nextLine();
            }

        }
        if (userMeaning == 1) {
            ts.write(list);
        }
    }
}
