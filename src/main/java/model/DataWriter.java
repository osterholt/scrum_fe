package model;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ArrayList;
import java.util.UUID;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataWriter extends DataConstants{

    //write users json
    public static boolean saveUsers() {
        LoginManager users = LoginManager.getInstance();
        ArrayList<User> userList = users.getUsers();
        JSONArray jsonUsers = new JSONArray();
        for(int i=0; i< userList.size(); i++) {
			jsonUsers.add(getUserJSON(userList.get(i)));
		}
        try (FileWriter file = new FileWriter(USER_FILE_NAME)) {
 
            file.write(jsonUsers.toJSONString());
            file.flush();
            return true;
 
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean saveTasks() {
        CompanyManager companyManager = CompanyManager.getInstance();
        JSONArray jsonTasks = new JSONArray();
        for(Company company : companyManager.getCompanies()) {
            for(Board board : company.getBoards()) {
                for(Column column : board.getColumns()) {
                    for(Task task : column.getTasks()) {
                        jsonTasks.add(getTaskJSON(task));
                    }
                }
            }
        }
        try (FileWriter file = new FileWriter(TASK_FILE_NAME)) {
 
            file.write(jsonTasks.toJSONString());
            file.flush();
            return true;
 
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //get useres json object to write it to the json file
    public static boolean saveCompanies() {
        CompanyManager companies = CompanyManager.getInstance();
        ArrayList<Company> companyList = companies.getCompanies();
        JSONArray jsonCompanies = new JSONArray();
        for(int i=0; i< companyList.size(); i++) {
            jsonCompanies.add(getCompanyJSON(companyList.get(i)));
        }
        try (FileWriter file = new FileWriter(COMPANY_FILE_NAME)) {
            file.write(jsonCompanies.toJSONString());
            file.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static JSONObject getUserJSON(User user) {
        JSONObject userDetails = new JSONObject();
        userDetails.put(USER_ID, user.getId().toString());
        userDetails.put(USER_FIRST_NAME, user.getFirstName());
        userDetails.put(USER_LAST_NAME, user.getLastName());
        userDetails.put(USER_EMAIL, user.getEmail());
        userDetails.put(USER_PASSWORD, user.getPassword());

        JSONArray companyList = new JSONArray();
        for (Company company : user.getCompanies()) {
            companyList.add(company.getID().toString());
        }

        userDetails.put(USER_COMPANIES, companyList);

        return userDetails;
    }

    public static JSONObject getCompanyJSON(Company company) {
        JSONObject companyDetails = new JSONObject();
        companyDetails.put(COMPANY_ID, company.getID().toString());
        companyDetails.put(COMPANY_NAME, company.getName());
        
        JSONArray adminList = new JSONArray();
        for (User admin : company.getAdmins()) {
            adminList.add(admin.getId().toString());
        }
        companyDetails.put(COMPANY_ADMINS, adminList);
        
        JSONArray userList = new JSONArray();
        for (User user : company.getUsers()) {
            userList.add(user.getId().toString());
        }
        companyDetails.put(COMPANY_USERS, userList);

        JSONArray boardList = new JSONArray();
        for (Board board : company.getBoards()) {
            System.out.println(board.getTitle());
            boardList.add(getBoardObject(board));
            
        }
        companyDetails.put(COMPANY_BOARDS, boardList);

        return companyDetails;
    }
    
    //data reader for users
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
                JSONArray companyIDs = (JSONArray)personJSON.get(USER_COMPANIES);
                User newUser = new User(id, firstName, lastName, email, password, role);
                for (Object companyID : companyIDs) {
                    newUser.addCompany(CompanyManager.getInstance().getCompany(UUID.fromString((String)companyID)));
                }
                users.add(newUser);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
      }

    public static ArrayList<Company> getCompanies() {
        ArrayList<Company> companies = new ArrayList<Company>();

        try {
            FileReader reader = new FileReader(COMPANY_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray companyJSON = (JSONArray)new JSONParser().parse(reader);

            for(int i=0; i < companyJSON.size(); i++) {
                JSONObject company_JSON = (JSONObject)companyJSON.get(i);
                UUID id = UUID.fromString((String)company_JSON.get(COMPANY_ID));
                String companyName = (String)company_JSON.get(COMPANY_NAME);
                Company newCompany = new Company(companyName, id);
                JSONArray userIDs = (JSONArray)company_JSON.get(COMPANY_USERS);
                JSONArray boards = (JSONArray)company_JSON.get(COMPANY_BOARDS);
                JSONArray adminIDs = (JSONArray)company_JSON.get(COMPANY_ADMINS);
                for (Object userID : userIDs) {
                    newCompany.addUser(LoginManager.getInstance().getUser(UUID.fromString((String)userID)));
                }
                for (Object board : boards) {
                    JSONObject company_board = (JSONObject) board;
                    String boardTitle = (String)company_board.get(BOARD_TITLE);
                    String boardDescription = (String)company_board.get(BOARD_DESCRIPTION);
                    boolean boardPrivate = (boolean)company_board.get(BOARD_PRIVATE);
                    Board newBoard = new Board(boardTitle, boardDescription, boardPrivate);
                    UUID boardScrumMasterID = UUID.fromString((String)company_board.get(BOARD_SCRUM_MASTER));
                    User boardScrumMaster = LoginManager.getInstance().getUser(boardScrumMasterID);
                    newBoard.setScrumMaster(boardScrumMaster);
                    UUID boardProductOwnerID = UUID.fromString((String)company_board.get(BOARD_PRODUCT_OWNER));
                    User boardProductOwner = LoginManager.getInstance().getUser(boardProductOwnerID);
                    newBoard.setProductOwner(boardProductOwner);
                    JSONArray columnsJSON = (JSONArray)company_board.get(BOARD_COLUMNS);
                    for (Object columnJSON : columnsJSON) {
                        JSONObject column = (JSONObject) columnJSON;
                        String columnTitle = (String)column.get(COLUMN_TITLE);
                        String columnDesc = (String)column.get(COLUMN_DESCRIPTION);
                        Column newCol = new Column(columnTitle, columnDesc);
                        JSONArray columnTaskIDs = (JSONArray)column.get(COLUMN_TASKS);
                        // get ArrayList of tasks from JSON
                        ArrayList<Task> tasks = getTasks();
                        // iterate through taskIDs for column tasks
                        for (Object taskID : columnTaskIDs) {
                            // iterate through ArrayList of tasks from JSON
                            for (Task task : tasks) {
                                // if tasks have the same ID, add task to the column
                                if(task.getID().equals(UUID.fromString((String)taskID)))
                                    newCol.addTask(task);
                            }
                        }
                        newBoard.addColumn(newCol);
                    }
                    JSONArray developers = (JSONArray)company_board.get(BOARD_DEVELOPERS);
                    for (Object developerID : developers) {
                        User developer = LoginManager.getInstance().getUser(UUID.fromString((String)developerID));
                        newBoard.addDev(developer);
                    }
                    newCompany.addBoard(newBoard);
                }
                for (Object adminID : adminIDs) {
                    newCompany.addAdmin(LoginManager.getInstance().getUser(UUID.fromString((String)adminID)));
                }
                companies.add(newCompany);
            }   
            return companies;
        } catch(Exception e) {
            return companies;
        }
    }
    public static ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        try {
            FileReader reader = new FileReader(TASK_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray tasksJSON = (JSONArray)new JSONParser().parse(reader);

            for (int i = 0; i < tasksJSON.size(); i++) {
                JSONObject taskJSON = (JSONObject)tasksJSON.get(i);
                UUID id = UUID.fromString((String)taskJSON.get(TASK_ID));
                UUID assigneeid = UUID.fromString((String)taskJSON.get(TASK_ASSIGNEE_ID));
                User assignee = LoginManager.getInstance().getUser(assigneeid);
                UUID authorid = UUID.fromString((String)taskJSON.get(TASK_AUTHOR_ID));
                User author = LoginManager.getInstance().getUser(authorid);
                String name = (String)taskJSON.get(TASK_NAME);
                String description = (String)taskJSON.get(TASK_DESCRIPTION);
                int priority = ((Long)taskJSON.get(TASK_PRIORITY)).intValue();
                float timeRequired = ((Double) taskJSON.get(TASK_TIME_REQUIRED)).floatValue();
                Category category = Category.valueOf((String)taskJSON.get(TASK_CATEGORY));
                boolean resolved = (boolean)taskJSON.get(TASK_RESOLVED);
                String dateString = (String) taskJSON.get(TASK_DATE);
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                LocalDateTime date = LocalDateTime.parse(dateString, dateFormatter);
                tasks.add(new Task(id, name, description, date, author, assignee, category, resolved, priority, timeRequired));
            }
            return tasks;
        } catch (Exception e) {
			e.printStackTrace();
		}
        return tasks;
    }

    public static JSONObject getTaskJSON(Task task) {
        JSONObject taskDetails = new JSONObject();
        taskDetails.put(TASK_ID, task.getID().toString());
        taskDetails.put(TASK_NAME, task.getName());
        taskDetails.put(TASK_DESCRIPTION, task.getDescription());
        taskDetails.put(TASK_DATE, task.dateToString());
        taskDetails.put(TASK_AUTHOR_ID, task.getAuthor().getId().toString());
        taskDetails.put(TASK_ASSIGNEE_ID, task.getAssignee().getId().toString());
        taskDetails.put(TASK_CATEGORY, task.getCategory().toString());
        taskDetails.put(TASK_RESOLVED, task.isResolved());
        taskDetails.put(TASK_PRIORITY, task.getPriority());
        taskDetails.put(TASK_TIME_REQUIRED, task.getTimeRequired());
        JSONArray historyArray = new JSONArray();
        for(History history : task.getHistory()) {
            JSONObject historyObject = new JSONObject();
            historyObject.put(HISTORY_AUTHOR_ID, history.getUser().getId().toString());
            historyObject.put(HISTORY_CHANGE, history.getChange());
            historyObject.put(HISTORY_DATE,history.getDate().toString());
            historyArray.add(historyObject);
        }
        taskDetails.put(TASK_HISTORY, historyArray);
        JSONArray commentArray = new JSONArray();
        for (Comment comment : task.getComments()) {
            commentArray.add(getCommentObject(comment));
        }
        taskDetails.put(COMMENT_COMMENTS, commentArray);
        
        return taskDetails;
    }

    private static JSONObject getCommentObject(Comment comment){
        JSONObject commentDetails = new JSONObject();
        commentDetails.put(COMMENTS_ID, comment.getAuthor().getId().toString());
        commentDetails.put(COMMENT, comment.getComment());
        JSONArray replies = new JSONArray();
        for(Comment reply : comment.getComments()) {
            replies.add(getCommentObject(reply));
        }
        commentDetails.put(COMMENT_COMMENTS, replies);
        return commentDetails;
    }

    private static JSONObject getBoardObject(Board board) {
        JSONObject boardDetails = new JSONObject();
        boardDetails.put(BOARD_TITLE, board.getTitle());
        boardDetails.put(BOARD_DESCRIPTION, board.getDescription());
        JSONArray columns = new JSONArray();
        for(Column column : board.getColumns()) {
            columns.add(getColumnObject(column));
        }
        boardDetails.put(BOARD_COLUMNS, columns);
        boardDetails.put(BOARD_PRODUCT_OWNER, board.getProductOwner().getId().toString());
        boardDetails.put(BOARD_SCRUM_MASTER, board.getScrumMaster().getId().toString());
        boardDetails.put(BOARD_PRIVATE, board.getPermissions());
        JSONArray developers = new JSONArray();
        for (User dev : board.getDevelopers()) {
            developers.add(dev.getId().toString());
        }
        boardDetails.put(BOARD_DEVELOPERS, developers);
        return boardDetails;
        
    }

    private static JSONObject getColumnObject(Column column) {
        JSONObject columnDetails = new JSONObject();
        columnDetails.put(COLUMN_TITLE, column.getTitle());
        columnDetails.put(COLUMN_DESCRIPTION, column.getDescription());
        JSONArray tasks = new JSONArray();
        for(Task task : column.getTasks()) {
            tasks.add(task.getID().toString());
        }
        columnDetails.put(COLUMN_TASKS, tasks);
        return columnDetails;
    }
    public static void main(String[] args) {
        // AppFacade.signUp("sherry", "begay", "sherry@gmail.com", "12345678910");
        // AppFacade.logOut();
    //     if(AppFacade.getInstance().login("sherry@gmail.com", "12345678910")) {
    //         System.out.println("Successfully logged in");
    //     } else {
    //         System.out.println("Not able to login");
    //     }
    //     //System.out.println(DataWriter.getTasks().get(0).getName());

    // //    ArrayList<Task> taskList = new ArrayList<>();
    //    User user1 = new User("Josh", "Dietrich", "jdd@email.com", "password1");
    //     User user2 = new User("Sherry", "begay", "shb@email.com", "password2");
    //     Category cat =Category.FRONTEND;
    //     Task t1 = new Task(UUID.randomUUID(), "taskname", "taskdescription", LocalDateTime.now(), user1, user2, cat, false, 1, 1);
    //     Column column = new Column("Todo", "Tasks that need to be done");
    //     column.addTask(t1);
    //     Board board = new Board("Test Board", "description",true);
    //     ArrayList<User> users = new ArrayList<User>();
    //     users.add(user1); users.add(user2);
    //     board.getColumn("Todo").addTask(t1);

    //AppFacade.getInstance().login("jdietrich@gmail.com", "password1");
        /*User user1 = new User("John", "L", "jl@email.com", "password11");
        User user2 = new User("Sherry", "begay", "shb@email.com", "password12");
        User usernew = new User("new", "user", "newuser@gmail.com", "newpassword");
        LoginManager.getInstance().addUser(user1);
        LoginManager.getInstance().addUser(user2);
        LoginManager.getInstance().addUser(usernew);
        DataWriter.saveUsers();
        Category cat =Category.FRONTEND;
        //Task t1 = new Task(UUID.randomUUID(), "taskname", "taskdescription", LocalDateTime.now(), user1, user2, cat, false, 1, 1);
        //Task t2 = new Task(UUID.randomUUID(), "taskname2", "taskdescription2", LocalDateTime.now(), user1, user2, cat, false, 1, 1);
        Task tnew = new Task(UUID.randomUUID(), "taskname3", "taskdescription3", LocalDateTime.now(), usernew, user2, cat, false, 1, 1);
        Column column = new Column("Todo", "Tasks that need to be done");
        //column.addTask(t1);
        //column.addTask(t2);
        Board board = new Board("Test Board", "description",true);
        ArrayList<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        //board.getColumn("Todo").addTask(t1);
        //board.getColumn("Todo").addTask(t2);
        board.getColumn("Todo").addTask(tnew);
        board.setScrumMaster(user2);
        board.setProductOwner(user1);
        System.out.println(board.getProductOwner());
        Company company = new Company("Test Company", user1, users, UUID.randomUUID());
        company.addBoard(board);
        CompanyManager companyManager = CompanyManager.getInstance();
        companyManager.addCompany(company);
        //System.out.println(companyManager.getCompanies());
        DataWriter.saveTasks();
        //System.out.println(DataWriter.getTasks().get(0).getName());
        DataWriter.saveCompanies();
        ArrayList<Company> companies = DataWriter.getCompanies();
        System.out.println(companies);*/
    //     Company company = new Company("Test Company", user1, users, UUID.randomUUID());
        
    //     company.addBoard(board);
    //     CompanyManager companyManager = CompanyManager.getInstance();
    //     companyManager.addCompany(company);

    // //     Date currentDate = new Date();
    // //    History h1 = new History(currentDate, user2, "change");
    // //    ArrayList<History> histarray= new ArrayList<>();
    // //    histarray.add(h1);
    // //    t1.setHistory(histarray);
    // //    //Comment c1 = new Comment(user2, "test comment");
    // //    //t1.addComment(c1);
    // //    JSONArray jsonTasks = new JSONArray();
    // //     taskList.add(t1);
    // //     DataWriter.saveTasks();
    //     DataWriter.saveCompanies();
     
        // User admin1 = new User("admin", "person", "admin@email.com", "coolpassword");
        // Company company1 = new Company("first", admin1, users, UUID.randomUUID());
    //     ArrayList<User> users2 = new ArrayList<User>();
    //     User bbgorl = new User("baby", "girl", "bbg@gmail.com", "waaaaawoooooo");
    //     users2.add(bbgorl);
    //     User admin2 = new User("mama", "joe", "mimimoomoo@aol.com", "weewoobooboo");
    //     Company company2 = new Company("second", admin2, users2, UUID.randomUUID());
    //     ArrayList<Company> companies = new ArrayList<Company>();
    //     companies.add(company1); companies.add(company2);
    //     JSONArray jsonCompany = new JSONArray();

        // adding scenario to JSON
        /* 
        User atticus = new User("Atticus", "Madden", "amadden@gmail.com", "password");
        User jeff = new User("Jeff", "Goldblum", "jgoldblum@gmail.com", "password");
        User owner = new User("Jeff", "Bezos", "jbezos@amazon.com", "password");
        AppFacade.getInstance().setActiveUser(atticus);
        Company mission = new Company("Code Mission Possible");
        mission.addAdmin(atticus);
        mission.addUser(jeff);
        mission.addUser(owner);
        System.out.println(mission.getUsers());
        Board ElectricMissiles = new Board("Electric Missiles", false);
        Board SoapFreeWashers = new Board("Soap Free Washers", false);
        Board AirComputers = new Board("Air Computers", false);
        ElectricMissiles.addDev(atticus);
        ElectricMissiles.addDev(jeff);
        ElectricMissiles.addDev(owner);
        ElectricMissiles.setScrumMaster(atticus);
        ElectricMissiles.setProductOwner(owner);
        ElectricMissiles.setDescription("We love missiles!");
        System.out.println(ElectricMissiles.getProductOwner());
        SoapFreeWashers.addDev(atticus);
        SoapFreeWashers.addDev(jeff);
        SoapFreeWashers.addDev(owner);
        SoapFreeWashers.setScrumMaster(atticus);
        SoapFreeWashers.setProductOwner(owner);
        System.out.println(ElectricMissiles.getProductOwner());
        SoapFreeWashers.setDescription("Washing things without buying soap is cool!");
        AirComputers.addDev(atticus);
        AirComputers.addDev(jeff);
        AirComputers.addDev(owner);
        AirComputers.setScrumMaster(atticus);
        AirComputers.setProductOwner(owner);
        System.out.println(AirComputers.getProductOwner());
        AirComputers.setDescription("Computers that run on air power!");
        mission.addBoard(AirComputers);
        mission.addBoard(ElectricMissiles);
        mission.addBoard(SoapFreeWashers);
        Task curve = new Task("Curve metal to make a cylindrical shape");
        curve.setAssignee(jeff);
        curve.setDescription("");
        curve.setCategory(Category.SOLO_PROJECT);
        Comment enough = new Comment("Not cylindrical enough");
        enough.setAuthor(jeff);
        Comment what = new Comment("What's a cylinder");
        what.setAuthor(atticus);
        curve.addComment(enough);
        curve.addComment(what);
        Task burger = new Task("Make impossible burger possible");
        burger.setAssignee(jeff);
        burger.setDescription("");
        burger.setCategory(Category.SOLO_PROJECT);
        ElectricMissiles.getColumn("Todo").addTask(curve);
        ElectricMissiles.getColumn("Doing").addTask(burger);
        LoginManager.getInstance().addUser(atticus);
        LoginManager.getInstance().addUser(jeff);
        LoginManager.getInstance().addUser(owner);
        AppFacade.getInstance().setActiveUser(atticus);
        CompanyManager.getInstance().addCompany(mission);
        DataWriter.saveTasks();
        DataWriter.saveCompanies();
        DataWriter.saveUsers();
        */
        AppFacade.getInstance().login("amadden@gmail.com", "password");
        System.out.println(LoginManager.getInstance().getUsers());
        System.out.println(CompanyManager.getInstance().getCompanies());
    }
}