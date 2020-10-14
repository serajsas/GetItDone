package model;

import java.util.HashMap;
import java.util.Map;

/*
 * This class represents a To-do List having a task list and progress achieved by the user
 */

public class TodoList {
    private static final String FALSE = "false";
    private HashMap<String, Task> taskList;

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
    public void deleteTask(String taskTitle) {
        taskList.remove(taskTitle);

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
    public void setProgress() {
        int total = getNumberOfInCompleteTasks()
                + getNumberOfCompleteTasks()
                + getNumberOfInReviewTasks();
        if (total == 0) {
            this.progress = 0;
        } else {
            this.progress = 100 * getNumberOfCompleteTasks() / total;

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
            if (stringTaskEntry.getValue() instanceof InReviewTask) {
                counter++;
            }
        }
        return counter;
    }

    //EFFECTS: Returns the number of instances Complete tasks occur
    public int getNumberOfCompleteTasks() {
        int counter = 0;
        for (Map.Entry<String, Task> stringTaskEntry : taskList.entrySet()) {
            if (stringTaskEntry.getValue() instanceof CompleteTask) {
                counter++;
            }
        }
        return counter;
    }

    //EFFECTS: Returns the number of instances InComplete tasks occur
    public int getNumberOfInCompleteTasks() {
        int counter = 0;
        for (Map.Entry<String, Task> stringTaskEntry : taskList.entrySet()) {
            if (stringTaskEntry.getValue() instanceof IncompleteTask) {
                counter++;
            }
        }
        return counter;
    }


    //EFFECTS: Returns progress
    public int getProgress() {
        return progress;
    }

    //EFFECTS: Returns the size of the task list
    public int getSize() {
        return taskList.size();
    }

}
