package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class TimeEntry {
    private long id=0l;
    private long projectId;
    private long userId;
    private java.time.LocalDate date;
    private int hours;



    public TimeEntry (long id, int projectId, int userId, java.time.LocalDate date, int hours){

        this.id = id;
        this.projectId = projectId;
        this.hours = hours;
        this.userId = userId;
        this.date = date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeEntry timeEntry = (TimeEntry) o;
        return this.hours == timeEntry.hours &&
                this.projectId == timeEntry.projectId &&
                this.userId == timeEntry.userId
                && Objects.equals(this.date, timeEntry.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, projectId, userId, date, hours);
    }

    public TimeEntry (int projectId, int userId, java.time.LocalDate date, int hours){

        this.projectId = projectId;
        this.hours = hours;
        this.userId = userId;
        this.date = date;

    }

    public TimeEntry (){

    }

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }



    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

}

