/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Techcare
 */
public class Cinema {
    private int cinemaId;
    private String name;
    private String logo;
    private String address;
    private String description;
    private String image;
    private int managerId;

    public Cinema() {
    }

    public Cinema(int cinemaId, String name, String logo, String address, String description, String image, int managerId) {
        this.cinemaId = cinemaId;
        this.name = name;
        this.logo = logo;
        this.address = address;
        this.description = description;
        this.image = image;
        this.managerId = managerId;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "Cinema{" + "cinemaId=" + cinemaId + ", name=" + name + ", logo=" + logo + ", address=" + address + ", description=" + description + ", image=" + image + ", managerId=" + managerId + '}';
    }
    
}
