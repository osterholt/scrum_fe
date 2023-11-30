package model;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DB extends DataConstants{

    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();

        try {
            FileReader reader = new FileReader(USER_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray userJSON = (JSONArray)new JSONParser().parse(reader);

            for(int i=0; i < userJSON.size(); i++) {
			    JSONObject personJSON = (JSONObject)userJSON.get(i);
                UUID id = UUID.fromString((String)personJSON.get(USER_ID));
                String firstName = (String)personJSON.get(USER_FIRST_NAME);
                String lastName = (String)personJSON.get(USER_LAST_NAME);
                String email = (String)personJSON.get(USER_EMAIL);
                String role = (String)personJSON.get(USER_ROLE);
                String password = (String)personJSON.get(USER_PASSWORD);
                users.add(new User(id,firstName, lastName, email, password, role));
            }

            return users;
        } catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    public static void main(String[] args) {
        ArrayList<User> users = DB.getUsers();
        for(int i = 0; i<users.size();i++) {
            System.out.println(users.get(i).getFirstName()+
            " "+users.get(i).getLastName()+
            " "+users.get(i).getEmail()+
            " "+users.get(i).getRole()+
            " "+users.get(i).getPassword());
        }
    }
}
