
package model;

public class Seat {
    private int seatId;
    private String seatNum;
    private int col;
    private int row;
    private String type;
    private int roomId;

    // Default constructor
    public Seat() {
    }

    // Parameterized constructor
    public Seat(int seatId, String seatNum, int col, int row, String type, int roomId) {
        this.seatId = seatId;
        this.seatNum = seatNum;
        this.col = col;
        this.row = row;
        this.type = type;
        this.roomId = roomId;
    }

    // Getters and Setters
    public int getSeatId() {
        return seatId;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Seat{" + "seatId=" + seatId + ", seatNum=" + seatNum + ", col=" + col + ", row=" + row + ", type=" + type + ", roomId=" + roomId + '}';
    }
}
