package model;

/*
 * This class represents a task that has a title, description, date, status and due date
 */

import org.json.JSONObject;
import persistence.Writable;

import java.util.HashMap;

public class Task implements Writable {
    public static final String COMPLETE_STRING = "Complete";
    public static final String INCOMPLETE_STRING = "Incomplete";
    public static final String INREVIEW_STRING = "InReview";
    protected String taskTitle;
    protected String description;
    protected String status;
    private Date dueDate;

    //EFFECTS:Constructs  task with taskTitle and description
    public Task(String taskTitle, String description) {
        this.taskTitle = taskTitle;
        this.description = description;
        this.dueDate = null;
    }

    //EFFECTS:Constructs  task with all the fields
    public Task(String taskTitle, String description, String status, Date dueDate) {
        this.taskTitle = taskTitle;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getStatus() {
        return status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;
        return taskTitle.equals(task.taskTitle);
    }

    @Override
    public String toString() {
        return "Task{"
                + "taskTitle='" + taskTitle + '\''
                + ", description='" + description + '\''
                + ", status='" + status + '\''
                + ", dueDate=" + dueDate
                + '}';
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("taskTitle", taskTitle);
        json.put("description", description);
        json.put("status", status);
        json.put("dueDate", dueDate);
        return json;
    }
}
