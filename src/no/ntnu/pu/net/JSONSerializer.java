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
	public static void toJSON(Appointment appointment){
		JSONObject appointmentJSON = new JSONObject();
		appointmentJSON.put("id", new Integer(appointment.getId()));;
	 
		JSONArray list = new JSONArray();
		list.add("msg 1");
		list.add("msg 2");
		list.add("msg 3");
	 
		appointmentJSON.put("messages", list);
	 
		try {
	 
			FileWriter file = new FileWriter("/home/hernil/temp/test.json");
			file.write(appointmentJSON.toJSONString());
			file.flush();
			file.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
		System.out.print(appointmentJSON);
		
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
		ArrayList<Participant> participants = new ArrayList();
		participants.add(p2);
		participants.add(p3);
		participants.add(g);
		a.setParticipants(participants);
		
		toJSON(a);
	}
	public void fromJSON(){
		
	}
}


 
