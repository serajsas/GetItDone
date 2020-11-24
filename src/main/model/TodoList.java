package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ui.ConsoleInterface.JSON_STORE;

/*
 * This class represents a To-do List having a task list and progress achieved by the user
 */

public class TodoList implements Writable {
    private static final String FALSE = "false";
    private final HashMap<String, Task> taskList;

    private int progress;

    //EFFECTS: Initializes a TodoList with new HashMap for list of tasks
    public TodoList() {
        taskList = new HashMap<>();
    }

    //MODIFIES: this
    //EFFECTS: Add the task to the list of tasks and returns true if the task title does not already exist,
    // otherwise returns false
    public boolean addTask(Task task) {
        boolean doesExist = false;
        if (!taskList.containsKey(task.getTaskTitle())) {
            taskList.put(task.getTaskTitle(), task);

        } else {
            doesExist = true;
        }
        return doesExist;
    }


    //MODIFIES: this
    //EFFECTS: Changes the task to InReviewTask and returns true if it exists, otherwise returns false
    public boolean changeToInReview(String name) {
        if (taskList.containsKey(name)) {
            InReviewTask inReviewTask = new InReviewTask(taskList.get(name).taskTitle,
                    taskList.get(name).description);
            inReviewTask.setDueDate(taskList.get(name).getDueDate());
            taskList.put(inReviewTask.taskTitle, inReviewTask);
            return true;
        } else {
            return false;
        }
    }

    //MODIFIES: this
    //EFFECTS: Changes the task to CompleteTask and returns true if it exists, otherwise returns false
    public boolean changeToComplete(String name) {
        if (taskList.containsKey(name)) {
            CompleteTask completeTask = new CompleteTask(taskList.get(name).taskTitle,
                    taskList.get(name).description);
            completeTask.setDueDate(taskList.get(name).getDueDate());
            taskList.put(completeTask.taskTitle, completeTask);
            return true;
        } else {
            return false;
        }

    }

    //MODIFIES: this
    //EFFECTS: Remove the task from list of tasks
    public boolean deleteTask(String taskTitle) {
        if (!taskList.containsKey(taskTitle)) {
            return false;
        } else {
            taskList.remove(taskTitle);
            return true;
        }
    }

    //EFFECTS: Returns the task description if it exists otherwise, returns false string
    public String seeTaskDescription(String taskTitle) {
        if (taskList.containsKey(taskTitle)) {
            return taskList.get(taskTitle).description;
        } else {
            return FALSE;
        }
    }


    //setters

    //MODIFIES:this
    //EFFECTS: Calculates the progress of the user based on the number of tasks remaining to complete
    public void calculateProgress() {
        int total = getNumberOfInCompleteTasks()
                + getNumberOfCompleteTasks()
                + getNumberOfInReviewTasks();
        if (total == 0) {
            setProgress(0);
        } else {
            setProgress(100 * getNumberOfCompleteTasks() / total);

        }
    }


    //getters

    //EFFECTS: Returns task list
    public HashMap<String, Task> getTaskList() {
        return taskList;
    }

    //EFFECTS: Returns the number of instances inReview tasks occur
    public int getNumberOfInReviewTasks() {
        int counter = 0;
        for (Map.Entry<String, Task> stringTaskEntry : taskList.entrySet()) {
            if (stringTaskEntry.getValue().getStatus().equals("InReview")) {
                counter++;
            }
        }
        return counter;
    }

    //EFFECTS: Returns the number of instances Complete tasks occur
    public int getNumberOfCompleteTasks() {
        int counter = 0;
        for (Map.Entry<String, Task> stringTaskEntry : taskList.entrySet()) {
            if (stringTaskEntry.getValue().getStatus().equals("Complete")) {
                counter++;
            }
        }
        return counter;
    }

    //EFFECTS: Returns the number of instances InComplete tasks occur
    public int getNumberOfInCompleteTasks() {
        int counter = 0;
        for (Map.Entry<String, Task> stringTaskEntry : taskList.entrySet()) {
            if (stringTaskEntry.getValue().getStatus().equals("Incomplete")) {
                counter++;
            }
        }
        return counter;
    }


    //EFFECTS: Returns progress
    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    //EFFECTS: Returns the size of the task list
    public int getSize() {
        return taskList.size();
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("taskList", tasksToJson());
        calculateProgress();
        json.put("progress", progress);
        return json;
    }

    //EFFECTS: returns tasks in this todolist to JSON array
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, Task> taskEntry : taskList.entrySet()) {
            jsonArray.put(taskEntry.getValue().toJson());
        }
        return jsonArray;
    }


    @Override
    public String toString() {
        return "TodoList{" + "taskList=" + taskList + ", progress=" + progress + '}';
    }

    // MODIFIES: this
    // EFFECTS: loads Todolist from file
    public TodoList loadTaskList() throws IOException {
        JsonReader jsonReader = new JsonReader(JSON_STORE);
        return jsonReader.read();
    }

    // EFFECTS: saves the Todolist to file
    public void saveTaskList() throws FileNotFoundException {
        JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
        jsonWriter.open();
        jsonWriter.write(this);
        jsonWriter.close();
    }

}
