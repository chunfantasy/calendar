package no.ntnu.pu.net;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import no.ntnu.pu.model.Appointment;
import no.ntnu.pu.model.Group;
import no.ntnu.pu.model.Participant;
import no.ntnu.pu.model.Person;
import no.ntnu.pu.model.Room;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONSerializer {
	
	private static final Appointment Appointment = null;
	
	@SuppressWarnings("unchecked")
	public static String toJSON(Appointment appointment){
		JSONObject aJSON = new JSONObject();
		aJSON.put("id", new Integer(appointment.getId()));
		aJSON.put("startTime", new String(appointment.getStartTimeString()));
		aJSON.put("endTime", new String(appointment.getEndTimeString()));
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

		System.out.println(g.getPersons().get(1).getEmail());
		Room r = new Room("P15");
		r.setId(1);

		Appointment a = new Appointment("");
		a.setTitle("gogogo");
		a.setId(1337);
		a.setStartTime(new Date());
		a.setEndTime(new Date());
		a.setMeetingRoom(r);
		a.setDescription("Hyttetur med Alexander Rybakk");
		ArrayList<Participant> participants = new ArrayList();
		participants.add(p2);
		participants.add(p3);
		participants.add(g);
		a.setParticipants(participants);
		
		try {
			 
			FileWriter file = new FileWriter("/home/hernil/temp/test.json");
			file.write(toJSON(a));
			file.flush();
			file.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
		System.out.print(toJSON(a));
	}
	public void fromJSON(){
		
	}
}


 
