package model;
import java.util.UUID;
/**
 * @author Cam Osterholt
 * @version v1.0
 * Date: 10/10/2023
 */

public class AppFacade {
    private User activeUser;
    private Company activeCompany;
    private Board activeBoard;
    private static AppFacade appFacade;

    private AppFacade() {
        activeUser = null;
        activeCompany = null;
        activeBoard = null;
    }

    public static AppFacade getInstance() {
        if(appFacade == null)
            appFacade = new AppFacade();
        return appFacade;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public boolean setActiveUser(User active){
        if(active == null)
            return false;
        activeUser = active;
        return true;
    }

    public Board getActiveBoard() {
        return activeBoard;
    }

    public boolean login(String username, String password) {
        activeUser = LoginManager.getInstance().getUser(username, password);
        if(activeUser == null){
            return false;
        }
        return true;
    }

    public User getCurrentUser(){
        return activeUser;
    }

    //TODO: What if user exists??
    public UUID signUp(String firstName, String lastName, String email, String password) {
        User user = new User(firstName, lastName, email, password);
        LoginManager.getInstance().addUser(user);
        setActiveUser(user);
        return user.getId();
    }

    public User getUser(UUID id) {
        return LoginManager.getInstance().getUser(id);
    }

    public void logOut() {
        LoginManager.getInstance().saveUsers();
        LoginManager.getInstance().saveTasks();
        LoginManager.getInstance().saveCompanies();
        System.exit(0);
    }

    public void removeActive() {
        activeUser = null;
        activeCompany = null;
        activeBoard = null;
    }

    public Company getActiveCompany() {
        return activeCompany;
    }

    public boolean setActiveCompany(String name) {
        if(name == null)
            return false;
        return null != (activeCompany = CompanyManager.getInstance().getCompany(name));
    }
    
    public boolean setActiveCompany(Company company) {
        if(company == null)
            return false;
        return null != (activeCompany = company);
    }

    public boolean setActiveBoard(String name) {
        return (activeBoard = AppFacade.getInstance().getActiveCompany().getBoard(name)) != null;
    }

    public String toString(){
        String toReturn = "\nActive User: "+ activeUser.getFirstName()+ " "+ activeUser.getLastName();
        toReturn += "\nActive Company: "+ activeCompany.getName();
        toReturn += "\nActive Board: "+ activeBoard.getTitle();
        return toReturn;
    }

}
