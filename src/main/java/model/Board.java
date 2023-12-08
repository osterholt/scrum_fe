package model;
import java.util.ArrayList;
import java.util.UUID;

import java.io.PrintWriter;
import java.time.LocalDateTime;
/**
 * @author Cam Osterholt
 * @version v1.0
 * Date: 10/15/2023
 */

public class Board {
    private String title;
    private String description;
    private ArrayList<Column> columns;
    private boolean open; //If can be accessed by anyone
    private User scrumMaster;
    private User productOwner;
    private ArrayList<User> developers;
    private Leaderboard leaderboard;

    private final String[] DEF_COLUMNS = {"Todo", "Doing", "Done"};

    public Board(String title, boolean open) {
        init(title, null, open);
    }
    public Board(String title, String description, boolean open) {
        init(title, description, open);
    }

    private void init(String title, String description, boolean open) {
        setDefaultColumns();
        this.developers = new ArrayList<User>();
        this.developers.add(AppFacade.getInstance().getActiveUser());
        leaderboard = new Leaderboard();
        setPermissions(open);
        setTitle(title);
        setDescription(description);
        
    }

    /**
     * Sets the default columns. Current implemtation has Todo, In Progress, and Done. 
     */
    private void setDefaultColumns() {
        columns = new ArrayList<Column>();
        for(String str : DEF_COLUMNS) {
            columns.add(new Column(str, null));
        }
    }

    public boolean completeTask(UUID id) {
        Task task = getTask(id);
        if(task != null) {
            this.leaderboard.incrementScore(AppFacade.getInstance().getActiveUser());
            return task.resolve();
        }
        return false;
    }

    public boolean deleteTask(UUID id, String name) {
        return getTask(id, name) != null;
    }

    public Leaderboard getLeaderboard() {
        if(this.open || canEdit())
            return this.leaderboard;
        return null;
    }

    /**
     * Creates new task in Todo Column
     * @param name String representing task's name
     * @return completion if true
     */
    public boolean createTask(String name) {
        return createTask(DEF_COLUMNS[0], null, name, null, null, AppFacade.getInstance().getActiveUser(), null, null, false, 3, 0);
    }

    public boolean createTask(String column, UUID id, String name, String description, LocalDateTime time, User author, User assignee, Category category, boolean resolved, int priority, float timeRequired) {
        Column temp = getColumn(column);
        if(temp != null) 
            return temp.addTask(id, name, description, time, author, assignee, category, resolved, priority, timeRequired);
        return false;
    }
    public boolean addColumn(Column column) {
        if(column != null && !columns.contains(column)) {
            columns.add(column);
            return true;
        }
        return false;
    }

    public boolean createColumn(String title) {
        return createColumn(title, null);
    }

    public boolean createColumn(String title, String description) {
        if(canCreateColumn(title)) {
            columns.add(columns.size() - 2, new Column(title, description));
            return true;
        }
        else
            return false;
    }
    
    public boolean addDev(User user) {
        if(developers.contains(user) || user == null)
            return false;
        return developers.add(user);
    }

    private boolean isDev() {
        for(User dev : this.developers) {
            if(AppFacade.getInstance().getActiveUser().equals(dev))
                return true;
        }
        return false;
    }
    private boolean canEdit() {
        if(this.open)
            return true;
        if(isDev())
            return true;
        return false;
    }

    public Column getColumn(String title) {
        for(Column column : columns) {
            if(title.equals(column.getTitle()))
                return column;
        }
        return null;
    }
    public Column getColumn(int index) {
        return columns.get(index);
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }

    /**
     * Decides you can make a column by looping thru to decide if the title has already been picked.
     * @return boolean if you can create.
     */
    private boolean canCreateColumn(String title) {
        return getColumn(title) == null;
    }

    public Task getTask(UUID id) {
        return getTask(id, null);
    }

    public Task getTask(String name) {
        return getTask(null, name);
    }

    public Task getTask(UUID id, String name) {
        if(id != null) {
            for(Column col : columns) {
                Task temp = col.getTask(id);
                if(temp != null)
                    return temp;
            }
        }
        else {
            for(Column col : columns) {
                Task temp = col.getTask(name);
                if(temp != null)
                    return temp;
            }
        }
        return null;
    }

    public boolean moveTask(String oldCol, String newCol, String taskName) {
        if(oldCol == null || newCol == null || taskName == null || getColumn(oldCol) == null)
            return false;
        if(getColumn(newCol) == null)
            createColumn(newCol);
        Task toMove = getColumn(oldCol).getTask(taskName);
        
        return getColumn(oldCol).removeTask(taskName) && getColumn(newCol).addTask(toMove);
    }


    //---Getters and Setters---

    public String getTitle() {
        if(this.open || canEdit())
            return title;
        else {
            return null;
        }
    }
    public boolean setTitle(String title) {
        if(canEdit()) {
            this.title = title;
            return true;
        }
        else {
            return false;
        }
    }
    public String getDescription() {
        if(this.open || canEdit())
            return this.description;
        else {
            return null;
        }
    }
    public void setDescription(String description) {
        if(canEdit())
            this.description = description;
    }
    public boolean getPermissions() {
        return open;
    }
    public void setPermissions(boolean open) {
        if(canEdit())
            this.open = open;
    }
    public User getScrumMaster() {
        if(this.open || canEdit())
            return this.scrumMaster;
        else {
            return null;
        }
    }
    public void setScrumMaster(User scrumMaster) {
        if(canEdit() && scrumMaster != null)
            this.scrumMaster = scrumMaster;
            
    }   
    public User getProductOwner() {
        if(this.open || canEdit())
            return this.productOwner;
        else {
            return null;
        }
    }
    public boolean setProductOwner(User productOwner) {
        if(canEdit() && productOwner != null) {
            this.productOwner = productOwner;
            return true;
        }
        return false;
    }
    public ArrayList<User> getDevelopers() {
        return developers;
    }
    public String toString(){
        String toReturn = "\nTitle: " +title;
        try {
            toReturn += "\n  Description: " + description;
        } catch (Exception e) {
            toReturn += "\n  Description: null";
        }
        try {
            toReturn += "\n  Scrum Master: " + scrumMaster.getFirstName() + " " + scrumMaster.getLastName();
        } catch (Exception e) {
            toReturn += "\n  Scrum Master: null";
        }
        try {
            toReturn += "\n  Product Owner: " + productOwner.getFirstName() + " " + productOwner.getLastName();
        } catch (Exception e) {
            toReturn += "\n  Product Owner: null";
        }
        toReturn += "\n  Columns: ";
        for(int i = 0; i < columns.size(); i++){
            toReturn += columns.get(i).toString();
        }
        return toReturn;
    
        
    }
    // give the path
    public void writeToTextFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println(this.toString());
            System.out.println("Content has been written to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}