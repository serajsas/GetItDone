package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        JsonWriter jsonWriter = new JsonWriter("data/savedTasks0\0");
        try {
            jsonWriter.open();
            fail("FileNotFoundException was expected");
        } catch (FileNotFoundException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyTodoList() {
        TodoList todoList = new TodoList();
        JsonWriter jsonWriter = new JsonWriter("data/testWriterEmptyTodoList");
        JsonReader jsonReader = new JsonReader("data/testWriterEmptyTodoList");
        try {
            jsonWriter.open();
            jsonWriter.write(todoList);
            jsonWriter.close();

            todoList = jsonReader.read();
            assertEquals(0, todoList.getTaskList().size());
            assertEquals(0, todoList.getProgress());
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not have been thrown");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }

    }

    @Test
    void testWriterNotEmptyTodoList() {
        TodoList todoList = new TodoList();
        Task incompleteTask = new IncompleteTask("MATH", "Finish the webwork");
        Task completeTask = new CompleteTask("CPSC 210", "Finish phase 1");
        Task inReview = new InReviewTask("CHEM", "Finish the hw");
        incompleteTask.setDueDate(new Date(25, 10, 2020));
        completeTask.setDueDate(new Date(22, 10, 2020));
        inReview.setDueDate(new Date(22, 11, 2020));
        todoList.addTask(incompleteTask);
        todoList.addTask(completeTask);
        todoList.addTask(inReview);
        JsonWriter jsonWriter = new JsonWriter("data/testWriterNotEmptyTodoList");
        JsonReader jsonReader = new JsonReader("data/testWriterNotEmptyTodoList");
        try {
            jsonWriter.open();
            jsonWriter.write(todoList);
            jsonWriter.close();

            TodoList todoListTest;

            todoListTest = jsonReader.read();
            assertEquals(3, todoListTest.getTaskList().size());
            assertEquals(33, todoListTest.getProgress());
            checkTask("MATH", "Finish the webwork", "Incomplete"
                    , new Date(25, 10, 2020), incompleteTask);
            checkTask("CPSC 210", "Finish phase 1", "Complete"
                    , new Date(22, 10, 2020), completeTask);
            checkTask("CHEM", "Finish the hw", "InReview"
                    , new Date(22, 11, 2020), inReview);
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not have been thrown");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }

    }
}
