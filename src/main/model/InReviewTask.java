package model;

//Represents a task with its status being "InReview"

public class InReviewTask extends Task {

    //EFFECTS:Constructs in review task
    public InReviewTask(String taskTitle, String description) {
        super(taskTitle, description);
        this.status = INREVIEW_STRING;
    }

}
