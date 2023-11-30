package model;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Evelyn Ellis
 * @version v1.0
 * Date: 10/12/2023
 */
public class Company {
    private UUID id;
    private String name;
    private ArrayList<Board> boards;
    private ArrayList<User> users;
    private ArrayList<User> admins;

    public Company(String name) {
        init();
        setUUID(null);
        setName(name);
    }

    public Company(String name, UUID id) {
        init();
        setUUID(id);
        setName(name);
    }

    public Company(String aName, User self, ArrayList<User> users, UUID id){
        init();
        setUUID(id);
        setName(aName);
        this.users = users;
        addAdmin(self);
    }

    private void setName(String name){
        this.name = name;
    }

    private void init(){
        this.boards = new ArrayList<Board>();
        this.users = new ArrayList<User>();
        this.admins = new ArrayList<User>();
        users.add(AppFacade.getInstance().getActiveUser());
    }

    public String getName() {
        return name;
    }
    public ArrayList<Board> getBoards(){
        return boards;
    }

    public Board getBoard(String title) {
        for(Board board : boards) {
            if(board.getTitle().toLowerCase().equals(title.toLowerCase())) 
                return board;
        }
        return null;
    }

    public ArrayList<User> getUsers(){
        return users;
    }
    public ArrayList<User> getAdmins(){
        return admins;
    }
    public UUID getUUID(){
        return id;
    }

    private void setUUID(UUID id) {
        if(id == null)
            id = UUID.randomUUID();
        this.id = id;
    }

    public UUID getID() {
        return id;
    }

    public boolean addUser(User user){
        if(user!=null){
            users.add(user);
            return true;
        }
        return false;
    }
    
    public boolean removeUser(User user){
        if(user==null){
            return false;
        }
        for(int i = 0; i<users.size(); i++){
            if(users.get(i).equals(user)){
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean addBoard(Board board){
        if(board!=null){
            boards.add(board);
            return true;
        }
        return false;
    }

    public boolean removeBoard(Board board){
        for(int i = 0; i<boards.size(); i++){
            if(boards.get(i).equals(board)){
                boards.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean addAdmin(User user){
         if(user!=null){
            admins.add(user);
            return true;
        }
        return false;
    }

    public boolean removeAdmin(User user){
        for(int i = 0; i<admins.size(); i++){
            if(admins.get(i).equals(user)){
                admins.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean isAdmin(User user){
        for(int i = 0; i<admins.size(); i++){
            if(admins.get(i).equals(user)){
                return true;
            }
        }
        return false;
    }

    public boolean equals(Company company) {
        return company != null
            && this.admins.equals(company.getAdmins())
            && this.users.equals(company.getUsers())
            && this.boards.equals(company.getBoards())
            && this.name.equals(company.getName())
            && this.id.equals(company.getUUID());
    }

    public String toString() {
        return "Company Name: " + name + 
        "\tID: " + id +
        "\nUsers: " + users + 
        "\nAdmins: " + admins +
        "\nBoards: " + boards;
    }

    /**
     * Checks that all data members are properly initialized
     * @return boolean
     * 
     * CHECK WHY WE NEED THISS
     */
    public boolean isValid() {
        return true;
    }
}
