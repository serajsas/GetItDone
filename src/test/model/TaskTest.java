package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//This class is testing the task class

class TaskTest {
    private Task task;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        task = new IncompleteTask("1", "1");
        task1 = new CompleteTask("2","2");
        task2 = new InReviewTask("3","3");
    }

    @Test
    public void taskConstructorTest(){
        Date date = new Date();
        date = date.getCurrentDate();

        assertEquals(task.getTaskTitle(),"1");
        assertEquals(task1.getTaskTitle(),"2");
        assertEquals(task2.getTaskTitle(),"3");

        assertEquals(task.getStatus(),"Incomplete");
        assertEquals(task1.getStatus(),"Complete");
        assertEquals(task2.getStatus(),"InReview");

        task.setDueDate(date);
        task1.setDueDate(date);
        task2.setDueDate(date);
        assertEquals(task.getDueDate(),date);
        assertEquals(task1.getDueDate(),date);
        assertEquals(task2.getDueDate(),date);
    }

    @Test
    public void equalsTest(){
        Date date = new Date();
        assertFalse(task.equals(task1));
        assertFalse(task.equals(task2));

        Task task3 = new IncompleteTask("4","5");
        assertFalse(task.equals(task3));


        Task task4 = new IncompleteTask("1","4");
        assertTrue(task.equals(task4));

        assertFalse(task.equals(date));
    }

    @Test
    public void toStringTest(){
        assertEquals(task.toString(),
                "Task{taskTitle='1', description='1', status='Incomplete', dueDate=null}");
    }
}