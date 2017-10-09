package api.youtube.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Created by daolinh on 9/29/17.
 */
@Entity
public class Member {

    @Id
    private long id;
    @Index
    private String username;
    @Unindex
    private String fullName;
    @Index
    private String email;
    @Unindex
    private String password;
    @Index
    private long birthDay;
    @Index
    private int gender;
    @Unindex
    private String avatar;
    @Index
    private long createdTimeMLS;
    @Index
    private long updatedTimeMLS;
    @Index
    private int status;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(long birthDay) {
        this.birthDay = birthDay;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCreatedTimeMLS() {
        return createdTimeMLS;
    }

    public void setCreatedTimeMLS(long createdTimeMLS) {
        this.createdTimeMLS = createdTimeMLS;
    }

    public long getUpdatedTimeMLS() {
        return updatedTimeMLS;
    }

    public void setUpdatedTimeMLS(long updatedTimeMLS) {
        this.updatedTimeMLS = updatedTimeMLS;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isValid(){
        boolean isValid = true;
        if(this.username == null || this.username.length() < 7 || this.username.contains(" ")){
            isValid = false;
        }
        if(this.email == null || this.email.length() < 7 || this.email.contains(" ")){
            isValid = false;
        }
        if(this.password == null || this.password.length() < 7){
            isValid = false;
        }
        return isValid;
    }
}
