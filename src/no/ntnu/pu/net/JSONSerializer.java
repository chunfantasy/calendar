package no.ntnu.pu.net;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Participant;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.model.Room;
import no.ntnu.pu.storage.ServerStorage;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONSerializer {
	private static String path;
	
	@SuppressWarnings("unchecked")
	public static String appointmentToJSON(Appointment appointment){
		JSONObject aJSON = new JSONObject();
		aJSON.put("id", new Integer(appointment.getId()));
		aJSON.put("title", new String(appointment.getTitle()));
		aJSON.put("startTime", new Long(appointment.getStartTimeLong()));
		aJSON.put("endTime", new Long(appointment.getEndTimeLong()));
		if (appointment.getMeetingRoom() instanceof Room) {
			aJSON.put("meetingRoom", new Integer(appointment.getMeetingRoom().getId()));			
		}
		else {
			aJSON.put("address", new String(appointment.getAddress()));			
		}
		aJSON.put("description", new String(appointment.getDescription()));
		
		JSONArray participantList = new JSONArray();
		for (Participant p : appointment.getParticipants()) {
			JSONObject participant = new JSONObject();
			participant.put("id", new Integer(p.getId()));
			if (p instanceof Person) {
				participant.put("type", "Person");
			}
			if (p instanceof Group) {
				participant.put("type", "Group");
			};
			participantList.add(participant);
		};
		aJSON.put("participants", participantList);
		return aJSON.toJSONString();
		
	}
	
	public static void main(String[] args) {
		Person p1 = new Person("a");
		p1.setEmail("email1");
		p1.setTitle("title1");

		Person p2 = new Person("b");
		p2.setEmail("email2");
		p2.setTitle("title2");
		
		Person p3 = new Person("c");
		p3.setEmail("email3");
		p3.setTitle("title3");
		
		Group g = new Group("super group 12");
		g.addPerson(p1);
		g.addPerson(p3);
		
		Room r = new Room("P15");
		r.setId(1);

		Appointment a = new Appointment("");
		a.setTitle("gogogo");
		a.setId(1337);
		a.setStartTime(new Date(710975563000L));
		a.setEndTime(new Date(710975573001L));
		a.setMeetingRoom(r);
		a.setDescription("Hyttetur med Alexander Rybakk");
		ArrayList<Participant> participants = new ArrayList();
		participants.add(p2);
		participants.add(p3);
		participants.add(g);
		a.setParticipants(participants);
		
		try {
			 
			FileWriter file = new FileWriter("/home/hernil/temp/test.json");
			file.write(appointmentToJSON(a));
			file.flush();
			file.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
		appointmentFromJSON();
		System.out.print(appointmentToJSON(a));
	}
	
	
	public static void appointmentFromJSON() {
		JSONParser parser = new JSONParser();
		
		 
		try {
	 
			Object obj = parser.parse(new FileReader("/home/hernil/temp/test.json"));
	 
			JSONObject jsonObject = (JSONObject) obj;
			Appointment a = new Appointment((String) jsonObject.get("title"));
			a.setDescription((String) jsonObject.get("description"));
			a.setStartTimeByLong((Long) jsonObject.get("startTime"));
			a.setEndTimeByLong((Long) jsonObject.get("endTime"));
			if (jsonObject.get("address") != "Null") {
				a.setAddress((String) jsonObject.get("address"));
			}
			else {
				a.setMeetingRoom(ServerStorage.getRoomById((int) (jsonObject.get("meetingRoom"))));
			}
			
			System.out.println(a.getStartTime());
			System.out.println(a.getEndTime());
	 
//			Integer age = (Integer) jsonObject.get("id");
//			System.out.println(age);
//	 
//			// loop array
//			JSONArray msg = (JSONArray) jsonObject.get("messages");
//			Iterator<String> iterator = msg.iterator();
//			while (iterator.hasNext()) {
//				System.out.println(iterator.next());
//			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
	}
}


 
