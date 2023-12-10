package model;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;

import java.util.regex.Matcher;
/**
 * @author Cam Osterholt & Evelyn Ellis
 * @version v2.0
 * Date: 10/17/2023
 */

public class LoginManager {
    private ArrayList<User> userList;
    private static LoginManager loginManager;

    public static LoginManager getInstance() {
        if(loginManager == null) {
            loginManager = new LoginManager();
            loginManager.loadData();
        }
        return loginManager;
    }

    private LoginManager() {
        userList = new ArrayList<User>();
    }

    private void loadData() {
        userList = DataWriter.getUsers();
        if (userList == null)
          userList = new ArrayList<User>();
      }

    public boolean checkEmail(String email) {
        if(email == null){
            return false;
        }
        // check if email is already in user list
        if(userList!=null){
            for (User user : userList) {
                if(user.getEmail().equals(email.toLowerCase())){
                    return false;
                }    
            }
        }
        // check if email is valid
        //String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9-_]+.[a-zA-Z0-9+-._]+$";
        String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Checks password that it is 8 or more characters.
     * @param password String to check
     * @return boolean if correct size
     */
    public boolean checkPassword(String password) {
        if(password == null)
            return false;
        return password.length() > 7;
    }

    /**
     * Adds a user to the master list.
     * @param user user to add
     * @return boolean if user was added.
     */
    public boolean addUser(User user) {
        if(user != null)
            return userList.add(user);
        return false;
    }

    public User getUser(UUID id) {
        for(User user : userList) {
            if(id.equals(user.getId()))
                return user;
        }
        //No User Found
        return null;
    }
    
    public ArrayList<User> getUsers() {
        return userList;
    }

    /*
     * Has DataWriter save the users to json.
     */
    public boolean saveUsers() {
        return DataWriter.saveUsers();
    }
    public boolean saveTasks() {
        return DataWriter.saveTasks();
    }
    public boolean saveCompanies() {
        return DataWriter.saveCompanies();
    }
    public User getUser(String email, String password) {
        for(User user : userList) {
            if(user.getEmail().equals(email) && user.isPassword(password))
                return user;
        }
        return null;
    }

    public User getUser(String email) {
        for(User user : userList) {
            if(user.getEmail().equals(email))
                return user;
        }
        return null;
    }

    public void printUsers() {
        System.out.println("");
        if(userList == null){
            return;
        }
        for(User user : userList) 
            System.out.println(user);
        System.out.println("");
    }
}

