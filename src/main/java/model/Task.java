package model;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;


public class Task {

    @BsonProperty("_id")
    int id;
    @BsonProperty("task")
    private String task;
    @BsonProperty("dueDate")
    private String dueDate;
    @BsonProperty("category")
    private String category;
    @BsonProperty("assignUser")
    private int assignUser;
    @BsonProperty("createUser")
    private int createUser;

    public Task(@BsonProperty("_id") int id, @BsonProperty("task") String task, @BsonProperty("dueDate") String dueDate, @BsonProperty("category") String category) {

        this.id = id;
        this.task = task;
        this.dueDate = dueDate;
        this.category = category;
    }

    @BsonCreator
    public Task(@BsonProperty("_id") int id, @BsonProperty("task") String task, @BsonProperty("dueDate") String dueDate, @BsonProperty("category") String category, @BsonProperty("assignUser") int assignUser, @BsonProperty("createUser") int createUser) {

        this.id = id;
        this.task = task;
        this.dueDate = dueDate;
        this.category = category;
        this.assignUser = assignUser;
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", category='" + category + '\'' +
                ", assignUser=" + assignUser +
                ", createUser=" + createUser +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(int assignUser) {
        this.assignUser = assignUser;
    }

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }
}
