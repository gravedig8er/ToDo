package todoList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class TaskStorage {
    private String path = "D:\\university\\oop\\source\\tasks.json";

    public ArrayList<Task> read() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path));) {

            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            Type type = new TypeToken<ArrayList<Task>>(){}.getType();
            final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .setPrettyPrinting()
                    .create();
            ArrayList<Task> list = gson.fromJson(json.toString(), type);

            if (list == null) return new ArrayList<Task>();
            return list;
        }
        catch (IOException e) {
            System.out.println("Проблема с открытием файла: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void write(ArrayList<Task> list) {
        final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(list);

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(path));) {
            printWriter.print(json);

        }
        catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

}
