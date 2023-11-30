package model;
import java.time.LocalDateTime;
/**
 * @author Evelyn Ellis
 * @version v1.0
 * Date: 10/12/2023
 */
public class History {

    private LocalDateTime date;
    private User user;
    private String change;
    /* 
     * 
     * date, user, change --> task has ARRAY LIST OF INSTANCES
     */

    public History(LocalDateTime date, User user, String change){
        this.date = date;
        this.user = user;
        this.change = change;

    }
    public LocalDateTime getDate(){
        return date;
    }

    public User getUser(){
        return user;
    }
    public String getChange(){
        return change;
    }
    
    public String toString(){
        return date.toString() + " " + user.getFirstName() + " " + user.getLastName() + " " + change;
    }

    // test
    /*public static void main(String[] args) {
        User myUser = new User("Evie", "Ellis", "evie.ellis11@gmail.com", "B3llyR@$h");
        Date myDate = new Date(1000000000);
        History myHistory = new History(myDate, myUser, "popped and pushed their shit");
        System.out.println(myHistory.toString());
    
    }*/
    
}

