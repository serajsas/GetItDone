package ui;

import model.*;

import java.util.Map;
import java.util.Scanner;

/*
 * To-do list application
 */

public class ConsoleInterface {

    private TodoList todoList;

    private Scanner input;

    // EFFECTS: runs the To-do Application
    public ConsoleInterface() {
        runInterface();
    }

    // MODIFIES: this
    // EFFECTS: process user input
    private void runInterface() {
        boolean keepGoing = true;
        String command;

        init();
        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase().trim();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("Have a nice day!!");
    }

    // MODIFIES: this
    // EFFECTS: initializes todoList
    private void init() {
        todoList = new TodoList();
        input = new Scanner(System.in);
    }

    //EFFECTS: Displays the menu of options to user
    private void displayMenu() {
        System.out.println("Select from:");
        System.out.println("\ta -> Add a Task");
        System.out.println("\tb -> Mark a task as complete");
        System.out.println("\tc -> Mark a task as in review");
        System.out.println("\td -> Delete an existing task");
        System.out.println("\te -> See a task description");
        System.out.println("\tf -> Access all tasks");
        System.out.println("\tg -> Access incomplete tasks");
        System.out.println("\th -> Access complete tasks");
        System.out.println("\ti -> Access in review tasks");
        System.out.println("\tj -> See your progress");
        System.out.println("\tq -> quit");
    }


    //EFFECTS: Process user command
    private void processCommand(String command) {
        if (command.matches("[a-e]")) {
            taskEditors(command);
        } else if (command.matches("[f-j]")) {
            taskListEditor(command);
        } else {
            System.out.println("Selection is invalid...");
        }
    }

    //EFFECTS: Process the commands related to tasks
    private void taskEditors(String command) {
        switch (command) {
            case "a":
                addTask();
                break;
            case "b":
                completeTask();
                break;
            case "c":
                reviewTask();
                break;
            case "d":
                deleteTask();
                break;
            case "e":
                seeTaskDescription();
                break;
        }

    }

    //EFFECTS: Process the commands related to the To-do List
    private void taskListEditor(String command) {
        switch (command) {
            case "g":
                accessIncompleteTasks();
                break;
            case "h":
                accessCompleteTasks();
                break;
            case "i":
                accessInReviewTasks();
                break;
            case "f":
                accessToDoTasks();
                break;
            case "j":
                checkProgress();
                break;
        }
    }

    //EFFECTS:Prints the list of all tasks
    private void accessToDoTasks() {
        for (Map.Entry<String, Task> taskEntry : todoList.getTaskList().entrySet()) {
            System.out.println(taskEntry.getValue().toString());
        }
    }

    //EFFECTS: Prints the task description upon selecting a specific task title
    private void seeTaskDescription() {
        System.out.println("Enter the name of the task to show its description");
        String name = input.nextLine();
        String description = todoList.seeTaskDescription(name.trim());
        if (description.equals("false")) {
            System.out.println("This task does not exist ");
        } else {
            System.out.println(description.trim());
        }

    }

    //MODIFIES: this
    //EFFECTS: Delete a task from the to-do list upon providing the task title
    private void deleteTask() {
        System.out.println("Enter the name of the task to delete");
        String name = input.nextLine();
        todoList.deleteTask(name.trim());
    }

    //EFFECTS: Prints out the progress of the user
    private void checkProgress() {
        todoList.setProgress();
        System.out.println(todoList.getProgress() + "% is completed");
    }

    //MODIFIES: this
    //EFFECTS: Allows the user to change a task status to inReview state
    private void reviewTask() {
        System.out.println("Enter the name of the task to change its status to in review");
        String name = input.nextLine();
        todoList.changeToInReview(name.trim());
    }

    //MODIFIES: this
    //EFFECTS: Allows the user to change a task status to complete state
    private void completeTask() {
        System.out.println("Enter the name of the task to change its status to complete");
        String name = input.nextLine();
        todoList.changeToComplete(name.trim());
    }


    //EFFECTS: Prints all the tasks that are in review
    private void accessInReviewTasks() {
        for (Map.Entry<String, Task> taskEntry : todoList.getTaskList().entrySet()) {
            if (taskEntry.getValue().getStatus().equals(Task.INREVIEW_STRING)) {
                System.out.println(taskEntry.getValue());
            }
        }
    }

    //EFFECTS: Prints all the tasks that are completed
    private void accessCompleteTasks() {
        for (Map.Entry<String, Task> taskEntry : todoList.getTaskList().entrySet()) {
            if (taskEntry.getValue().getStatus().equals(Task.COMPLETE_STRING)) {
                System.out.println(taskEntry.getValue().toString());
            }
        }
    }

    //EFFECTS: Prints all the tasks that are incomplete
    private void accessIncompleteTasks() {
        for (Map.Entry<String, Task> taskEntry : todoList.getTaskList().entrySet()) {
            if (taskEntry.getValue().getStatus().equals(Task.INCOMPLETE_STRING)) {
                System.out.println(taskEntry.getValue().toString());
            }
        }
    }


    //MODIFIES: this
    //EFFECTS: Allows the user to add incomplete task to the to-do list
    private void addTask() {
        Task task = taskGetter();
        boolean doesExist = todoList.addTask(task);
        while (!doesExist) {
            System.out.println("Your task is: " + task.toString());
            printChangingDateQuestion();
            String answer = input.nextLine();
            answer = answer.toLowerCase().trim();
            if (answer.equals("y")) {
                enterValidDate(task);
                break;
            } else if (!answer.equals("n")) {
                System.out.println("Choice is invalid ! Please try again");
            } else {
                Date date = new Date();
                task.setDueDate(date.getCurrentDate());
                System.out.println("Your due date has been updated to today's date :)");
                break;
            }
        }
        if (doesExist) {
            System.out.println("This task exists! Change the task title");
        }
    }

    //EFFECTS: Prompts the user for a task title and description and return it as incomplete task
    private Task taskGetter() {
        System.out.println("Enter name of the task to be added");
        String name = input.nextLine();
        System.out.println("Enter your task description");
        String description = input.nextLine();
        return new IncompleteTask(name.trim(), description.trim());

    }

    //EFFECTS: Asks the user whether they want to change the due date for a task
    private void printChangingDateQuestion() {
        System.out.println("Would you like to change the due date?");
        System.out.println("Please enter Y/N");
    }

    //REQUIRES: Integers are used for entering date values
    //MODIFIES: task
    //EFFECTS: Prompts the user to enter a valid date
    private void enterValidDate(Task task) {
        Date date;
        do {
            System.out.println("Enter the date in the format DD/MM/YY as integers");
            String dueDate = input.nextLine();
            String[] dateData = dueDate.split("/");
            date = new Date(Integer.parseInt(dateData[0]),
                    Integer.parseInt(dateData[1]),
                    Integer.parseInt(dateData[2]));
            boolean isValid = date.isDateValid();
            if (!isValid) {
                System.out.println("Please enter a valid due date as integers");
            }
        } while (!date.isDateValid());
        task.setDueDate(date);
        System.out.println("Your task is: " + task.toString());
    }


}
