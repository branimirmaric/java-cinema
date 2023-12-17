/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

/**
 *
 * @author branimir.maric
 */
public class Users {

    private int id;
    private String username;
    private String password;
    private boolean memberRole; 

    public Users() {
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users(int id, String username, String password, boolean memberRole) {
        this(username, password);
        this.id = id;
        this.memberRole = memberRole;
    }

}
