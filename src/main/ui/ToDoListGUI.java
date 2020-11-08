package ui;

import model.Date;
import model.IncompleteTask;
import model.Task;
import model.TodoList;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/*This class represents the graphical user interface for the todolist*/
public class ToDoListGUI extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JButton addTask;
    private JButton load;
    private JButton save;
    private JButton showIncompleteTasks;
    private JButton showCompleteTasks;
    private JButton completeTask;
    private JButton clear;
    private JButton getProgress;
    private JTextField taskName;
    private JTextField taskDescription;
    private JFormattedTextField dueDate;
    private JTextArea textArea;
    private TodoList todoList;

    //EFFECTS: Constructor of the ToDOListGUI
    public ToDoListGUI() {
        super("To-Do");
        setPreferredSize(new Dimension(ToDoListGUI.WIDTH, ToDoListGUI.HEIGHT));
        initializeTools();
        createInteractions();
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        revalidate();
    }

    //This method is modified from FROM https://www.codejava.net/download-attachment?fid=189.
    //EFFECTS: Plays the music given its path
    public void play(String audioFilePath) {
        File audioFile = new File(audioFilePath);

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            audioClip.start();
        } catch (Exception ex) {
            //DO NOTHING
        }

    }

    //EFFECTS: Activates the functionality of the buttons
    private void createInteractions() {
        saveTasks();
        loadTasks();
        showIncompleteTasks();
        showCompleteTasks();
        completeTask();
        clearTextArea();
        addTaskListener();
        getProgress();
    }

    //MODIFIES: this
    //EFFECTS: Allows the user to clear text area
    private void clearTextArea() {
        clear.addActionListener(e -> {
            textArea.setText(null);
        });
    }

    //MODIFIES: this
    //EFFECTS: Allows the user to change a task status to complete state
    private void completeTask() {
        completeTask.addActionListener(e -> {
            String taskTitle = taskName.getText();
            if (todoList.getTaskList().containsKey(taskTitle)) {
                todoList.changeToComplete(taskTitle);
            } else {
                textArea.append("This task does not exist" + "\n");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Loads Todolist from file
    private void loadTasks() {
        load.addActionListener(e -> {
            try {
                todoList = todoList.loadTaskList();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    // EFFECTS: Saves the Todolist to file
    private void saveTasks() {
        save.addActionListener(e -> {
            try {
                todoList.saveTaskList();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
    }

    //EFFECTS: Prints all the complete tasks to the text field
    private void showCompleteTasks() {
        showCompleteTasks.addActionListener(e -> {
            boolean isFound = false;
            for (Map.Entry<String, Task> taskEntry : todoList.getTaskList().entrySet()) {
                if (taskEntry.getValue().getStatus().equals(Task.COMPLETE_STRING)) {
                    textArea.append(taskEntry.getValue().toString() + "\n");
                    isFound = true;
                }
            }
            if (!isFound) {
                textArea.append("You don't have any completed tasks :(" + "\n");
            }
        });
    }

    //EFFECTS: Prints all the incomplete tasks to the text field
    private void showIncompleteTasks() {
        showIncompleteTasks.addActionListener(e -> {
            boolean isFound = false;
            for (Map.Entry<String, Task> taskEntry : todoList.getTaskList().entrySet()) {
                if (taskEntry.getValue().getStatus().equals(Task.INCOMPLETE_STRING)) {
                    textArea.append(taskEntry.getValue().toString() + "\n");
                    isFound = true;
                }
            }
            if (!isFound) {
                textArea.append("You don't have any tasks :(" + "\n");
            }
        });
    }

    //EFFECTS: Prints the progress to the screen and plays applause sound if progress is 100%
    private void getProgress() {
        getProgress.addActionListener(e -> {
            todoList.calculateProgress();
            textArea.append("Your progress is: " + todoList.getProgress() + "\n");
            if (todoList.getProgress() == 100) {

                play("./data/app-29.wav");

            }
        });
    }

    //MODIFIES: this
    //EFFECTS: Allows the user to add incomplete task to the to-do list
    private void addTaskListener() {
        addTask.addActionListener(e -> {
            String date = dueDate.getText();
            String taskTitle = taskName.getText();
            String description = taskDescription.getText();
            Date dueDate = new Date();
            try {
                dueDate = dueDate.getDateFromGUI(date);
                if (!dueDate.isDateValid()) {
                    textArea.append("Make sure date DD/MM/YYYY is valid" + "\n");
                } else if (todoList.getTaskList().containsKey(taskTitle)) {
                    textArea.append("This task name already exists" + "\n");
                } else {
                    clearTextArea();
                    Task task = new IncompleteTask(taskTitle, description);
                    task.setDueDate(dueDate);
                    todoList.addTask(task);
                    textArea.append(task.toString() + "\n");
                }
            } catch (Exception e1) {
                textArea.append("Make sure date is in this format DD/MM/YYYY " + "\n");
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: Creates a new panel and adds the buttons to the panel
    //         this method is called by ToDoListGUI constructor
    private void initializeTools() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(1, 1));
        toolArea.setSize(new Dimension(1, 1));
        add(toolArea, BorderLayout.SOUTH);

        addTask = new JButton("Add Task");
        toolArea.add(addTask);

        completeTask = new JButton("Mark As Complete");
        toolArea.add(completeTask);

        showIncompleteTasks = new JButton("Show Incompleted Tasks");
        toolArea.add(showIncompleteTasks);

        showCompleteTasks = new JButton("Show Completed Tasks");
        toolArea.add(showCompleteTasks);

        save = new JButton("Save");
        toolArea.add(save);

        load = new JButton("Load");
        toolArea.add(load);

        clear = new JButton("Clear Area");
        toolArea.add(clear);

        getProgress = new JButton("Get Progress");
        toolArea.add(getProgress);

        initializeFields();
    }

    // MODIFIES: this
    // EFFECTS:  Creates a new todolist object and instantiates taskName, taskDescription and dueDate
    //           this method is called by ToDoListGUI constructor
    private void initializeFields() {
        todoList = new TodoList();
        JPanel fieldsPanel = new JPanel();

        taskName = new JTextField("Task Title", 15);
        taskDescription = new JTextField("Task Description", 30);
        dueDate = new JFormattedTextField("Due Date (DD/MM/YYYY)");
        dueDate.setColumns(15);

        fieldsPanel.add(taskName);
        fieldsPanel.add(taskDescription);
        fieldsPanel.add(dueDate);

        fieldsPanel.setLayout(new FlowLayout());
        fieldsPanel.setSize(300, 300);
        add(fieldsPanel, BorderLayout.NORTH);
        clearFieldsOnMousePress();
        initializeTextViewer();
    }

    //MODIFIES: this
    //EFFECTS: Creates a text area and scroll pane and adds it to the frame
    private void initializeTextViewer() {
        JPanel textViewer = new JPanel();
        textArea = new JTextArea();
        textArea.setEditable(false);
        textViewer.add(textArea);
        textViewer.setLayout(null);
        add(textViewer);
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
    }

    //MODIFIES: this
    //EFFECTS: Clears the text fields on a mouse click
    private void clearFieldsOnMousePress() {
        taskName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                taskName.setText(null);
            }
        });
        dueDate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dueDate.setText(null);
            }
        });
        taskDescription.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                taskDescription.setText(null);
            }
        });
    }


}
