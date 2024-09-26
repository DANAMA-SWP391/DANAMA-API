
package model;
public class Room {
    private int roomId;
    private String name;
    private int numberOfSeat;
    private int cinemaId;

    // Default constructor
    public Room() {
    }

    // Parameterized constructor
    public Room(int roomId, String name, int numberOfSeat, int cinemaId) {
        this.roomId = roomId;
        this.name = name;
        this.numberOfSeat = numberOfSeat;
        this.cinemaId = cinemaId;
    }

    // Getters and Setters
    public int getRoomId() {
        return roomId;
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

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    @Override
    public String toString() {
        return "Room{" + "roomId=" + roomId + ", name=" + name + ", numberOfSeat=" + numberOfSeat + ", cinemaId=" + cinemaId + '}';
    }
}
