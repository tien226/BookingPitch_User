package com.mobile.bookingpitch_user.model;

public class DateMonth {
    private String date;

    public DateMonth() {
    }

    public DateMonth(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return date;
    }
}
