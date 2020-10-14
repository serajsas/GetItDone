package model;

import java.util.Calendar;
import java.util.GregorianCalendar;

//Represents a date with day, month , and year

public class Date {
    private int day;
    private int month;
    private int year;
    private Calendar calendar;

    //EFFECTS:Constructs a date with day, month and year
    public Date(int day, int month, int year) {
        calendar = new GregorianCalendar();
        this.day = day;
        this.month = month;
        this.year = year;
    }

    //EFFECTS:Constructs a date with empty fields
    public Date() {
    }


    //MODIFIES: this
    //EFFECTS: Returns current date
    public Date getCurrentDate() {
        calendar = Calendar.getInstance();
        return new Date(calendar.get(Calendar.DATE),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR));
    }
    //getters

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }


    //EFFECTS: Returns true if the date entered valid, otherwise returns false
    public boolean isDateValid() {
        if (getDay() > 31 || getDay() <= 0) {
            return false;
        } else if (getMonth() > 12 || getMonth() < 0) {
            return false;
        } else if (getYear() < getCurrentDate().getYear()) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public String toString() {
        return "Date{"
                + "day=" + day
                + ", month=" + month
                + ", year=" + year
                + '}';
    }
}
