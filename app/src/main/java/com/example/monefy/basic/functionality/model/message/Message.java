package com.example.monefy.basic.functionality.model.message;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private String id;
    private String message;
    private boolean isReviewed;
    private Date date;

    public Message(String message, boolean isReviewed, Date date) {
        this.message = message;
        this.isReviewed = isReviewed;
        this.date = date;
    }

    public Message() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
