package no.ntnu.pu.model;

public class Room {

	private int id;
	private String roomname;
    private int size;

	public Room(String roomname) {
		this.roomname = roomname;
	}

	public String getRoomname() {
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
