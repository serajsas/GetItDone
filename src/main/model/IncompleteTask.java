package model;

//Represents a task with its status being "Incomplete"

public class IncompleteTask extends Task {

    //EFFECTS:Constructs an incomplete task
    public IncompleteTask(String taskTitle, String description) {
        super(taskTitle, description);
        this.status = INCOMPLETE_STRING;
    }

}
