/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Techcare
 */
public class Account {

    private int UID;
    private String name;
    private String email;
    private String phone;
    private String avatar;
    private String googleId;
    private int roleId;
    private String password;

    public Account(int UID, String name, String email, String phone, String avatar, String googleId, int roleId, String password) {
        this.UID = UID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.googleId = googleId;
        this.roleId = roleId;
        this.password = password;
    }

    public int getUID() {
        return UID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getGoogleId() {
        return googleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" + "UID=" + UID + ", name=" + name + ", email=" + email + ", phone=" + phone + ", avatar=" + avatar + ", googleId=" + googleId + ", roleId=" + roleId + ", password=" + password + '}';
    }
    
}
