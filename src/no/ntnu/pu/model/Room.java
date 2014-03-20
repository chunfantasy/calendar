package no.ntnu.pu.model;

public class Room {

	private int id, capacity;
	private String roomname;

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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

    @Override
    public String toString() {
        return getRoomname();
    }
}
