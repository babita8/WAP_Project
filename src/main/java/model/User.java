package model;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class User {
    @BsonProperty("_id")
    int id;
    @BsonProperty("userName")
    String userName;
    @BsonProperty("passWord")
    String passWord;
    @BsonProperty("email")
    String email;
    @BsonProperty("phone")
    String phone;
    @BsonProperty("groupId")
    int groupId;
    @BsonProperty("group")
    String group;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public User(@BsonProperty("userName") String userName, @BsonProperty("passWord") String passWord, @BsonProperty("email") String email, @BsonProperty("phone") String phone, @BsonProperty("groupId") int groupId, @BsonProperty("group") String group) {
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.phone = phone;
        this.groupId = groupId;
        this.group = group;
    }

    @BsonCreator
    public User(@BsonProperty("_id") int id, @BsonProperty("userName") String userName, @BsonProperty("passWord") String passWord, @BsonProperty("email") String email, @BsonProperty("phone") String phone, @BsonProperty("groupId") int groupId, @BsonProperty("group") String group) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.phone = phone;
        this.groupId = groupId;
        this.group = group;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", groupId=" + groupId +
                ", group='" + group + '\'' +
                '}';
    }
}
