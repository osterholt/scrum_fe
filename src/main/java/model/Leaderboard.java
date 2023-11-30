package model;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Evelyn Ellis & Cam Osterholt
 * @version v2.0
 * Date: 10/23/2023
 */

//TODO: Determine when we need to sort list. 

public class Leaderboard {
     private ArrayList<LeaderboardMember> leaderboard;

     private class LeaderboardMember {
          private User user;
          private int score;

          public LeaderboardMember(User user, int score) {
               setUser(user);
               setScore(score);
          }
          private void setUser(User user) {
               if(user != null)
               this.user = user;
          }
          public User getUser() {
               return this.user;
          }
          public int setScore(int score) {
               if(score > -1)
                    this.score = score;
               return this.score;
          }
          public int getScore() {
               return this.score;
          }
     }

     /**
      * Initalizes a new leaderboard.
      */
     public Leaderboard() {
          leaderboard = new ArrayList<LeaderboardMember>();
     }

     /**
      * Bubble sorts instance's Leaderboard.
      * @return 
      */
     private boolean sortList(){
          LeaderboardMember temp;
          boolean swapped;
          for(int i = 0; i < leaderboard.size() - 1; i++) {
               swapped = false;
               for(int j = 0; j < leaderboard.size() - i - 1; j++) {
                    if(leaderboard.get(j).getScore() < leaderboard.get(j + 1).getScore()) {
                         temp = leaderboard.get(j);
                         leaderboard.set(j, leaderboard.get(j + 1));
                         leaderboard.set(j + 1, temp);
                         swapped = true;
                    }
               }
               if(!swapped) return true; // Checks for final pass if sorted correctly.
          }
          return false;
     }

     public int getRank(UUID id) {
          return getIndex(id) + 1;
     }
     public int getRank(User user){
          return getIndex(user.getId()) + 1;
     }
     public int getRank() {
          return getIndex(AppFacade.getInstance().getActiveUser().getId()) + 1;
     }

     /**
      * Increments User score for one completed task.
     * @param user User to update.
     * @return User's current score.
     * @author Cam Osterholt
     */
     public int incrementScore(User user) {
          return updateScore(user, 1);
     }
     /**
      * Updates User score for an inputted number.
      * @param user User to update.
      * @return User's current score.
      * @author Cam Osterholt
      */
     public int updateScore(User user, int increment){
          int index = getIndex(user.getId());
          //User not found
          if(index < 0) {
               this.leaderboard.add(new LeaderboardMember(user, increment));
               sortList();
               return increment;
          } 
          return updateScore(user.getId(), increment);
     }
     /**
      * Updates User score for an inputted number.
      * @param user User to update.
      * @return User's current score.
      * @author Cam Osterholt
      */
     public int updateScore(UUID id, int increment) {
          int index = getIndex(id);
          //User not found, cannot initalize.
          if(index < 0) {
               return index;
          } 
          LeaderboardMember lm = this.leaderboard.get(index);
          return lm.setScore(lm.getScore() + increment);
     }

     /**
      * Gets the top ranked users to a last value.
      * @param lastScore Last ranked user to include in array
      * @return Array with [UUID, score]
      */
     public String[][] getTopRank(int lastScore){
          sortList();
          String[][] arr = new String[lastScore][2];
          for(int i = 0; i < lastScore; i++) {
               arr[i][0] = this.leaderboard.get(i).getUser().getId().toString();
               arr[i][1] = String.valueOf(this.leaderboard.get(i).getScore());
          }
          return arr;
     }

     /**
      * Formats the leaderboard to,
      * FirstName LastName    Score: {Score}
      * @return String formatted for all 
      */
     public String toString() {
          sortList();
          String str = "";
          for(LeaderboardMember lm : this.leaderboard) {
               str += lm.getUser().getFirstName() + " " + lm.getUser().getLastName() + "\tScore: " + lm.getScore() + "\n";
          }
          return str;
     }

     /**
      * Prints the leaderboard in order to console.
      */
     public void print() {
          System.out.print(toString());
     }

     private int getIndex(UUID id) {
          sortList();
          for(int i = 0; i < leaderboard.size(); i++) {
               if(leaderboard.get(i).getUser().getId().equals(id))
                    return i;
          }
          return -1;
     }
    
}
