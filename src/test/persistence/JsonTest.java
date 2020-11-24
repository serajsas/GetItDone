package persistence;

import model.Date;
import model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTask(String taskTitle, String description, String status, Date dueDate, Task task) {
        assertEquals(taskTitle, task.getTaskTitle());
        assertEquals(description, task.getDescription());
        assertEquals(status, task.getStatus());
        assertEquals(dueDate, task.getDueDate());
    }
}
