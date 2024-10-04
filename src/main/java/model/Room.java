
package model;
public class Room {
    private int roomId;
    private String name;
    private int numberOfSeat;
    private Cinema cinema;

    // Default constructor
    public Room() {
    }

    // Parameterized constructor
    public Room(int roomId, String name, int numberOfSeat, Cinema cinema) {
        this.roomId = roomId;
        this.name = name;
        this.numberOfSeat = numberOfSeat;
        this.cinema = cinema;
    }

    public Room(String name, int numberOfSeat, Cinema cinema) {
        this.name = name;
        this.numberOfSeat = numberOfSeat;
        this.cinema = cinema;
    }

    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfSeat() {
        return numberOfSeat;
    }

    public void setNumberOfSeat(int numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    @Override
    public String toString() {
        return "Room{" +
                "cinema=" + cinema +
                ", roomId=" + roomId +
                ", name='" + name + '\'' +
                ", numberOfSeat=" + numberOfSeat +
                '}';
    }
}
