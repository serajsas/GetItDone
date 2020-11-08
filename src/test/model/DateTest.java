package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//This class is testing the date class

public class DateTest {
    private Date date;

    //EFFECTS: Initializes a valid date
    @BeforeEach
    public void setUp() {
        date = new Date(9, 10, 2020);
    }

    @Test
    public void dateConstructorTest() {
        assertEquals(date.getDay(), 9);
        assertEquals(date.getMonth(), 10);
        assertEquals(date.getYear(), 2020);
    }

    @Test
    public void getDateFromGUITest() {
        String dateString = "09/10/2020";
        String dateString1 = "9/10/2020";
        String dateString2 = "5/10/2020";
        Date date1 = new Date();
        date1 = date1.getDateFromGUI(dateString);
        Date date2 = new Date();
        date2 = date2.getDateFromGUI(dateString1);
        Date date3 = new Date();
        date3 = date3.getDateFromGUI(dateString2);
        assertTrue(date.equals(date1));
        assertTrue(date.equals(date2));
        assertFalse(date.equals(date3));
    }


    @Test
    public void getCurrentDateTest() {
        Date date1 = new Date();
        date1 = date1.getCurrentDate();
        assertEquals(date1.getDay(), date.getCurrentDate().getDay());
        assertEquals(date1.getMonth(), date.getCurrentDate().getMonth());
        assertEquals(date1.getYear(), date.getCurrentDate().getYear());
    }

    @Test
    public void isDateValidTest() {
        Date date1 = new Date(31,10,2022);
        Date date2 = new Date(15,12,2030);
        Date date3 = new Date(15,9,2020);
        Date date4 = new Date(32,9,2020);
        Date date5 = new Date(15,13,2020);
        Date date6 = new Date(15,9,2019);
        Date date7 = new Date(15,-2,2019);
        Date date8 = new Date(0,5,2019);
        Date date9 = new Date(-3,2,2019);

        assertTrue(date.isDateValid());
        assertTrue(date1.isDateValid());
        assertTrue(date2.isDateValid());
        assertTrue(date3.isDateValid());

        assertFalse(date4.isDateValid());
        assertFalse(date5.isDateValid());
        assertFalse(date6.isDateValid());
        assertFalse(date7.isDateValid());
        assertFalse(date8.isDateValid());
        assertFalse(date9.isDateValid());


    }


    @Test
    public void toStringTest() {
        assertEquals(date.toString(), "Date{day=9, month=10, year=2020}");
    }

    @Test
    void equalsTest(){
        Date date1 = new Date(9,10,2020);
        Date date2 = null;
        Date date3 = new Date(9,10,2030);
        Date date4 = new Date(9,11,2020);
        Date date5 = new Date(10,11,2020);

        assertTrue(date.equals(date));
        assertTrue(date.equals(date1));

        assertFalse(date.equals(new Task("1","1")));
        assertFalse(date.equals(date2));
        assertFalse(date.equals(date3));
        assertFalse(date.equals(date4));
        assertFalse(date.equals(date5));
        assertEquals(date1.hashCode(),date.hashCode());
    }
}
