package no.ntnu.pu.model;

import java.util.ArrayList;

public class Group implements Participant {

	private int id;
	private String name, email;
	private ArrayList<Person> persons;

	public Group(String name) {
		this.name = name;
		this.persons = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Person> getPersons() {
		return persons;
	}

	public void setPersons(ArrayList<Person> persons) {
		this.persons = persons;
	}

	public ArrayList<Person> addPerson(Person p) {
		this.persons.add(p);
		return this.persons;
	}

	public ArrayList<Person> removePerson(Person p) {
		this.persons.remove(p);
		return this.persons;
	}
}
