package model;

/*
 * This class represents a task that has a title, description, date, status and due date
 */

public class Task {
    public static final String COMPLETE_STRING = "Complete";
    public static final String INCOMPLETE_STRING = "Incomplete";
    public static final String INREVIEW_STRING = "InReview";
    protected String taskTitle;
    protected String description;
    protected String status;
    private Date dueDate;

    //EFFECTS:Constructs  task
    public Task(String taskTitle, String description) {
        this.taskTitle = taskTitle;
        this.description = description;
        this.dueDate = null;
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
}
