package no.ntnu.pu.control;

import no.ntnu.pu.model.Room;
import no.ntnu.pu.storage.RoomStorage;
import no.ntnu.pu.storage.ServerStorage;

public class RoomControl {

    public static RoomStorage serverStorage = new RoomStorage();

	public void isAvailable(){
		
	}

	public void getSuitableRooms(){

	}

    public static Room getRoomById(int id) {
        return serverStorage.getRoomById(id);
    }
}
