package model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

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

    //EFFECTS: Takes in a string and changes it into a date and returns it
    public Date getDateFromJson(String dueDate) {

        String[] dateData = dueDate.split(",");
        String day = dateData[0];
        String[] dayData = day.split("=");

        String month = dateData[1];
        String[] monthData = month.split("=");

        String year = dateData[2];
        String[] yearData = year.split("=");

        return new Date(Integer.parseInt(dayData[1]), Integer.parseInt(monthData[1]),
                Integer.parseInt(yearData[1].substring(0, 4)));
    }

    //EFFECTS: Takes in a string and changes it into a date and returns it
    public Date getDateFromGUI(String dueDate) {
        String[] dateData = dueDate.split("/");
        String day = dateData[0];

        String month = dateData[1];

        String year = dateData[2];

        return new Date(Integer.parseInt(day), Integer.parseInt(month),
                Integer.parseInt(year.substring(0,4)));
    }

    @Override
    public String toString() {
        return "Date{"
                + "day=" + day
                + ", month=" + month
                + ", year=" + year
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Date date = (Date) o;
        return day == date.day && month == date.month && year == date.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }
}
