package model;

//Represents a task with its status being "Complete"

public class CompleteTask extends Task {

    //EFFECTS:Constructs a complete task
    public CompleteTask(String taskTitle, String description) {
        super(taskTitle, description);
        this.status = COMPLETE_STRING;
    }

}
