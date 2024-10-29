
package model;
public class Room {
    private int roomId;
    private String name;
    private int numberOfSeat;
    private Cinema cinema;
    private int numberOfRows;
    private int numberOfColumns;

    // Default constructor
    public Room() {
    }

    // Parameterized constructor


    public Room(Cinema cinema, int roomId, int numberOfSeat, int numberOfRows, int numberOfColumns, String name) {
        this.cinema = cinema;
        this.roomId = roomId;
        this.numberOfSeat = numberOfSeat;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.name = name;
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

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    @Override
    public String toString() {
        return "Room{" +
                "cinema=" + cinema +
                ", roomId=" + roomId +
                ", name='" + name + '\'' +
                ", numberOfSeat=" + numberOfSeat +
                ", numberOfRows=" + numberOfRows +
                ", numberOfColumns=" + numberOfColumns +
                '}';
    }
}
