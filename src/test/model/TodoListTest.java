package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//This class is testing the todolist class

public class TodoListTest {
    private TodoList todoList;

    @BeforeEach
    public void setUp() {
        todoList = new TodoList();
    }

    @Test
    public void getTaskListTestEmpty() {
        assertTrue(todoList.getTaskList().isEmpty());
    }

    @Test
    public void getTaskListTestNotEmpty() {
        Task task = new IncompleteTask("1", "1");
        Task task1 = new InReviewTask("2", "2");
        Task task2 = new CompleteTask("3", "3");
        assertFalse(todoList.addTask(task));
        assertFalse(todoList.addTask(task1));
        assertFalse(todoList.addTask(task2));
        assertFalse(todoList.getTaskList().isEmpty());
        assertEquals(todoList.getTaskList().get("1"), task);
        assertEquals(todoList.getTaskList().get("2"), task1);
        assertEquals(todoList.getTaskList().get("3"), task2);
    }

    @Test
    public void addTaskTestDoesNotExist() {
        Task task = new IncompleteTask("1", "1");
        Task task1 = new InReviewTask("2", "2");
        Task task2 = new CompleteTask("3", "3");
        assertFalse(todoList.addTask(task));
        assertFalse(todoList.addTask(task1));
        assertFalse(todoList.addTask(task2));
        assertEquals(todoList.getNumberOfInCompleteTasks(), 1);
        assertEquals(todoList.getNumberOfInReviewTasks(), 1);
        assertEquals(todoList.getNumberOfCompleteTasks(), 1);
        assertEquals(todoList.getSize(), 3);

    }

    @Test
    public void addTaskTestDoestExist() {
        Task task = new IncompleteTask("1", "1");
        Task task1 = new IncompleteTask("1", "1");
        Task task2 = new IncompleteTask("1", "122");
        assertFalse(todoList.addTask(task));
        assertTrue(todoList.addTask(task1));
        assertTrue(todoList.addTask(task2));
        assertEquals(todoList.getNumberOfInCompleteTasks(), 1);
        assertEquals(todoList.getNumberOfInReviewTasks(), 0);
        assertEquals(todoList.getNumberOfCompleteTasks(), 0);
        assertEquals(todoList.getSize(), 1);

    }

    @Test
    public void getProgressTestZeroComplete() {
        Task task = new IncompleteTask("1", "1");
        Task task1 = new IncompleteTask("2", "2");
        assertEquals(todoList.getProgress(), 0);
        assertFalse(todoList.addTask(task));
        assertFalse(todoList.addTask(task1));
        assertEquals(todoList.getNumberOfInCompleteTasks(), 2);
        assertEquals(todoList.getNumberOfCompleteTasks(), 0);
        assertEquals(todoList.getProgress(), 0);


    }

    @Test
    public void getProgressTestHalfComplete() {
        Task task = new IncompleteTask("1", "1");
        Task task1 = new IncompleteTask("2", "2");
        todoList.calculateProgress();
        assertEquals(todoList.getProgress(), 0);
        assertFalse(todoList.addTask(task));
        assertFalse(todoList.addTask(task1));
        todoList.changeToComplete("1");
        assertEquals(todoList.getNumberOfInCompleteTasks(), 1);
        assertEquals(todoList.getNumberOfCompleteTasks(), 1);
        todoList.calculateProgress();
        assertEquals(todoList.getProgress(), 50);
    }

    @Test
    public void getProgressTestFullyComplete() {
        Task task = new IncompleteTask("1", "1");
        Task task1 = new CompleteTask("2", "2");
        Task task2 = new InReviewTask("3", "3");
        assertFalse(todoList.addTask(task));
        assertFalse(todoList.addTask(task1));
        assertFalse(todoList.addTask(task2));
        todoList.changeToComplete("1");
        todoList.changeToComplete("3");
        todoList.calculateProgress();
        assertEquals(todoList.getProgress(), 100);
    }

    @Test
    public void deleteTaskTestDoesNotExist() {
        Task task = new IncompleteTask("1", "1");
        Task task1 = new IncompleteTask("2", "2");
        assertFalse(todoList.addTask(task));
        assertFalse(todoList.addTask(task1));
        assertFalse(todoList.deleteTask("5"));
        assertEquals(todoList.getNumberOfInCompleteTasks(), 2);
        assertEquals(todoList.getSize(), 2);
    }

    @Test
    public void deleteTaskTestDoesExist() {
        Task task = new IncompleteTask("1", "1");
        Task task1 = new IncompleteTask("2", "2");
        assertFalse(todoList.addTask(task));
        assertFalse(todoList.addTask(task1));
        assertTrue(todoList.deleteTask("1"));
        assertEquals(todoList.getNumberOfInCompleteTasks(), 1);
        assertEquals(todoList.getSize(), 1);
        assertTrue(todoList.deleteTask("2"));
        assertEquals(todoList.getNumberOfInCompleteTasks(), 0);
        assertEquals(todoList.getSize(), 0);
    }

    @Test
    public void seeTaskDescriptionTestDoesNotExist() {
        Task task = new IncompleteTask("Solve", "500");
        Task task1 = new IncompleteTask("2", "2");
        todoList.addTask(task);
        todoList.addTask(task1);
        assertEquals(todoList.seeTaskDescription("4"), "false");
        assertEquals(todoList.seeTaskDescription("Solve"), "500");
    }

    @Test
    public void changeToCompleteTest() {
        Task task = new IncompleteTask("Solve", "500");
        todoList.addTask(task);
        assertFalse(todoList.changeToComplete("1"));
        assertEquals(todoList.getNumberOfCompleteTasks(), 0);
        assertTrue(todoList.changeToComplete("Solve"));
        assertEquals(todoList.getNumberOfCompleteTasks(), 1);
    }

    @Test
    public void changeToInReviewTest() {
        Task task = new IncompleteTask("Solve", "500");
        todoList.addTask(task);
        assertFalse(todoList.changeToInReview("1"));
        assertEquals(todoList.getNumberOfInReviewTasks(), 0);
        assertTrue(todoList.changeToInReview("Solve"));
        assertEquals(todoList.getNumberOfInReviewTasks(), 1);
    }

    @Test
    void toJsonTest(){
        Task incompleteTask = new IncompleteTask("MATH", "Finish the webwork");
        Task completeTask = new CompleteTask("CPSC 210", "Finish phase 1");
        Task inReview = new InReviewTask("CHEM", "Finish the hw");
        incompleteTask.setDueDate(new Date(25, 10, 2020));
        completeTask.setDueDate(new Date(22, 10, 2020));
        inReview.setDueDate(new Date(22, 11, 2020));
        todoList.addTask(incompleteTask);
        todoList.addTask(completeTask);
        todoList.addTask(inReview);
        JSONObject jsonObject = todoList.toJson();
        assertEquals(jsonObject.get("progress"),33);
    }

    @Test
    void toStringTest(){
        Task incompleteTask = new IncompleteTask("MATH", "Finish the webwork");
        todoList.addTask(incompleteTask);
        assertEquals(todoList.toString(),"TodoList{taskList={MATH=Task{taskTitle='MATH', " +
                "description='Finish the webwork', status='Incomplete', dueDate=null}}, progress=0}");

    }

}
