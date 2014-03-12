package no.ntnu.pu.model;

public class Group implements Participant{

	private int id;
    private String name,email;

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Group(String name){
        this.name = name;
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
}
