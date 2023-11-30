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
                Company newCompany = new Company(companyName);
                JSONArray userIDs = (JSONArray)company_JSON.get(COMPANY_USERS);
                JSONArray boardIDs = (JSONArray)company_JSON.get(COMPANY_BOARDS);
                JSONArray adminIDs = (JSONArray)company_JSON.get(COMPANY_ADMINS);
                for (Object userID : userIDs) {
                    newCompany.addUser(LoginManager.getInstance().getUser(UUID.fromString((String)userID)));
                }
                /*for (Object boardID : boardIDs) {
                    for(Company company : CompanyManager.getInstance().getCompanies()) {
                        newCompany.add //TODO: addBoard
                    }
                }*/
                for (Object adminID : adminIDs) {
                    newCompany.addAdmin(LoginManager.getInstance().getUser(UUID.fromString((String)adminID)));
                }
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
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
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

    AppFacade.getInstance().login("jdietrich@gmail.com", "password1");
        User user1 = new User("John", "L", "jl@email.com", "password11");
        User user2 = new User("Sherry", "begay", "shb@email.com", "password12");
        Category cat =Category.FRONTEND;
        Task t1 = new Task(UUID.randomUUID(), "taskname", "taskdescription", LocalDateTime.now(), user1, user2, cat, false, 1, 1);
        Task t2 = new Task(UUID.randomUUID(), "taskname2", "taskdescription2", LocalDateTime.now(), user1, user2, cat, false, 1, 1);
        Column column = new Column("Todo", "Tasks that need to be done");
        column.addTask(t1);
        column.addTask(t2);
        Board board = new Board("Test Board", "description",true);
        ArrayList<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        board.getColumn("Todo").addTask(t1);
        board.getColumn("Todo").addTask(t2);
        Company company = new Company("Test Company", user1, users, UUID.randomUUID());
        company.addBoard(board);
        CompanyManager companyManager = CompanyManager.getInstance();
        companyManager.addCompany(company);
        DataWriter.saveTasks();
        System.out.println(DataWriter.getTasks().get(0).getName());
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
    }
}