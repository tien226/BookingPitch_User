package com.mobile.bookingpitch_user.model;

public class SpanPitch {
    private String id;
    private String time;

    public SpanPitch() {
    }

    public SpanPitch(String id, String time) {
        this.id = id;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return time;
    }
}
