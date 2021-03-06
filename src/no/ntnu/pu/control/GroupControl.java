package no.ntnu.pu.control;

import no.ntnu.pu.model.Group;
import no.ntnu.pu.storage.GroupStorage;

import java.util.ArrayList;

public class GroupControl{

    public static GroupStorage serverStorage = new GroupStorage() ;

    public static ArrayList<Group> getAll(){
        return serverStorage.getAll();
    }

    public static Group getGroupById(int id){
        return serverStorage.getGroupById(id);
    }

    public static Group getGroupByEmail(String email){
        return serverStorage.getGroupByEmail(email);
    }

    public static ArrayList<Group> getGroupByName(String name){
        return serverStorage.getGroupByName(name);
    }

    public static Group insertGroup(Group g){
        return serverStorage.insertGroup(g);
    }

    public boolean deleteGroupById(int id){
        return serverStorage.deleteGroupById(id);
    }

    public boolean deleteGroupByEmail(String email){
        return serverStorage.deleteGroupByEmail(email);
    }
}