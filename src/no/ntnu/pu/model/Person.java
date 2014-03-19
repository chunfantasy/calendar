package no.ntnu.pu.model;

import java.util.ArrayList;
import java.util.List;

public class Person implements Participant {
	private int id;
	private String name, email, title, password;
	private List<String> phoneNumbers;
    private Calendar calendar;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Person(String Name) {
		name = Name;
        calendar = new Calendar();
		phoneNumbers = new ArrayList<String>();
	}

	public void addPhoneNumber(String phoneNumber) {
		this.phoneNumbers.add(phoneNumber);
	}

	public Boolean removePhoneNumber(String phoneNumber) {
		if (phoneNumbers.contains(phoneNumber)) {
			phoneNumbers.remove(phoneNumber);
			return true;
		}
		return false;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public String getTitle() {
		return title;
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}