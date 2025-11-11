package todoList;


import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        TaskManager tm = new TaskManager();

        boolean quitFlag = false;
        while (!quitFlag) {
            // добавить время добавления задачи
            System.out.println("=== Task Manager ===");
            System.out.println("1. Показать все задачи");
            System.out.println("2. Добавить задачу");
            System.out.println("3. Удалить задачу");
            System.out.println("4. Изменить статус");
            System.out.println("0. Выход");

            int userIN = -1;
            try {userIN = scanner.nextInt();}
            catch (InputMismatchException e) {
                System.out.println("Введено не число, повторите снова");
                scanner.nextLine(); // чистим буфер
                continue;
            }
            switch (userIN) {
                case 0:
                    System.out.println("Завершение работы");
                    quitFlag = true;
                    break;
                case 1:
                    tm.showAllTasks();
                    break;
                case 2:
                    tm.addTask();
                    System.out.println("\n".repeat(45));
                    break;
                case 3:
                    tm.deleteTask();
                    System.out.println("\n".repeat(45));
                    break;
                case 4:
                    tm.changeStatusTask();
                    System.out.println("\n".repeat(45));
                    break;
            }

        }
    }
}