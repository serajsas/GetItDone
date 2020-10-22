package persistence;

import model.Date;
import model.Task;
import model.TodoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader jsonReader = new JsonReader("/data/noSuchFile.json");

        try {
            TodoList todoList = jsonReader.read();
            fail("IO exception is expected");
        } catch (IOException e) {
            //
        }
    }

    @Test
    void testReadEmptyTodoList() {
        JsonReader jsonReader = new JsonReader("data/testWriterEmptyTodoList");

        try {
            TodoList todoList = jsonReader.read();
            assertEquals(todoList.getSize(), 0);
            assertEquals(todoList.getProgress(), 0);
        } catch (IOException e) {
            fail("IOException is not supposed to be thrown");
        }
    }

    @Test
    void testReadNotEmptyTodoList() {
        JsonReader jsonReader = new JsonReader("data/testWriterNotEmptyTodoList");

        try {
            TodoList todoList = jsonReader.read();
            assertEquals(todoList.getSize(), 3);
            assertEquals(todoList.getProgress(), 33);
            HashMap<String, Task> taskList = todoList.getTaskList();
            assertEquals(taskList.get("CHEM").getStatus(),"InReview");
            assertEquals(taskList.get("MATH").getDescription(),"Finish the webwork");
            assertEquals(taskList.get("CPSC 210").getDueDate(),new Date(22,10,2020));
        } catch (IOException e) {
            fail("IOException is not supposed to be thrown");
        }
    }
}
