package model;
/**
 * @author Cam Osterholt
 * @version v1.0
 * Date: 10/18/2023
 */

public class UI {
    private static final String[] USER_CAM = {"Cam", "Osterholt", "banana@osterholt.us", "p33y0urp@nt$"};
    public static void main(String args[]) {
        UI ui  = new UI();
        ui.run();
    }
    /*Your name is Atticus Madden.
    - You currently work for Code Mission Possible - A company who works on creating software solutions for clean energy.
    - You are the SCRUM Manager for 3 different projects (Electric Missiles, Soap Free Washers, and Air Computers)
    - Open Electric Missiles
    - Add a new task "Initialize super algorithm to detonate at warp speed". Assign the task to Jeff Goldblum.
    - Add a comment to the task "Avoid civilians Jeff!"
    - Move the existing task of "Curve the metal to make a cylindrical shape" to the 'Doing' column. 
    - This task has the existing comments of "Not cylindrical enough" - by Jeff, 
    - and "What's a cylinder" by Atticus Finch.  
    - Reply to Jeff's comment and say "How about you do it jeff", and re-assign the task from yourself to Jeff.
    - Add a new column called "Abandoned"
    - Move an existing task "Make impossible burger possible" which doesn't really relate to the project purpose to "Abandoned"
    - Now print the scrum board to a txt file.... make it pretty.*/
    public void run() {
        

        

        Task curveMetal = new Task("Curve the metal to make a cylindrical shape");

      
        //TODO:
        //ADD USER with data above
        //Signup
        //Add to json file - test
        //Make sure we can save
    }
}
