package no.ntnu.pu.control;

import no.ntnu.pu.model.Room;
import no.ntnu.pu.storage.RoomStorage;

import java.util.ArrayList;

public class RoomControl {

    public static RoomStorage storage = new RoomStorage();

	public void isAvailable(){
		
	}

	public static ArrayList<Room> getSuitableRooms(int size){
        ArrayList<Room> allRooms = getAll();
        ArrayList<Room> suitableRooms = new ArrayList<Room>();
        for(Room room : allRooms){
            if(room.getSize() >= size) {
                suitableRooms.add(room);
            }
        }
        return suitableRooms;
	}

    public static Room getRoomById(int id) {
        return storage.getRoomById(id);
    }

    public static ArrayList<Room> getAll(){
        return storage.getAll();
    }
}
