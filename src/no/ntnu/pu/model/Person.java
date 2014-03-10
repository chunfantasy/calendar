package no.ntnu.pu.model;

public class Person implements Participant{
	private String name, email, title;
	private String[] phoneNumbers;

    public Person(String Name){
        name = Name;
    }

    public void addPhoneNumber(String phoneNumber) {
        this.phoneNumbers.add(phoneNumber);
    }

    public Boolean removePhoneNumber(String phoneNumber){
        if(phoneNumbers.contain(phoneNumber)){
            phoneNumbers.remove(phoneNumber);
            return True;
        }
        return False;
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

    public String[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public String getTitle() {
        return title;
    }
}
