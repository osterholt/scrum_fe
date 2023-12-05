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
        if(user == null || users.contains(user))
            return false;
        return users.add(user);
    }
    
    public boolean removeUser(User user){
        if(user == null || users.size() <= 1)
            return false;
        return users.remove(user);
    }
    
    public boolean addBoard(Board board){
        if(board == null || boards.contains(board) || checkBoardName(board))
            return false;
        return boards.add(board);
    }

    private boolean checkBoardName(Board board) {
        for(int i = 0; i < boards.size(); i++) {
            if(boards.get(i).getTitle() == board.getTitle())
                return true;
        }
        return false;
    }
    
    public boolean removeBoard(Board board){
        if(boards.size() <= 1)
            return false;
        return boards.remove(board);
    }

    public boolean addAdmin(User user){
        if(user == null || admins.contains(user))
            return false;
        // if you're adding an admin, they're by nature a user in the system as well
        if(!users.contains(user))
            users.add(user);
        return admins.add(user);
    }
    
    public boolean removeAdmin(User user){
        // don't want to remove the only admin
        if(admins.size() <= 1)
            return false;
        return admins.remove(user);
    }
    
    public boolean isAdmin(User user){
        return admins.contains(user);
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
