package persistence;

import model.Date;
import model.Task;
import model.TodoList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Used the code provided on Github to implement Json Reader
// Represents a reader that reads JSON representation of todolist
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads todolist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TodoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTodoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses todolist from JSON object and returns it
    private TodoList parseTodoList(JSONObject jsonObject) {
        TodoList todoList = new TodoList();
        addTasks(todoList, jsonObject);
        todoList.setProgress(jsonObject.getInt("progress"));
        return todoList;
    }

    // MODIFIES: todolist
    // EFFECTS: parses tasks from JSON object and adds them to console interface
    private void addTasks(TodoList todoList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("taskList");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(todoList, nextTask);
        }
    }

    // MODIFIES: todolist
    // EFFECTS: parses task from JSON object and adds it to todolist
    private void addTask(TodoList todoList, JSONObject jsonObject) {
        String taskTitle = jsonObject.getString("taskTitle");
        String description = jsonObject.getString("description");
        String status = jsonObject.getString("status");
        String dueDate = jsonObject.getString("dueDate");


        Date date = new Date();
        date = date.getDateFromJson(dueDate);
        Task task = new Task(taskTitle, description, status, date);
        todoList.addTask(task);
    }

}
