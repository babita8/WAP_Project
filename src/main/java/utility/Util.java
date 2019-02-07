package utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import model.Group;
import model.Task;
import model.User;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

public class Util {

    static MongoClient mongoClient;
    static MongoDatabase dbTask;

    // Method to make a connection to the mongodb server listening on a default port
    private static MongoClient getConnection() {

        MongoClientURI uri = new MongoClientURI(
                "mongodb://admin:admin@cluster0-shard-00-00-nsfcl.gcp.mongodb.net:27017,cluster0-shard-00-01-nsfcl.gcp.mongodb.net:27017,cluster0-shard-00-02-nsfcl.gcp.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true");
        if (mongoClient == null) {
            mongoClient = new MongoClient(uri);
        }
        return mongoClient;
    }


    public static MongoCollection getTable(String collection) {
        String db_name = "taskdb", db_collection_name = collection;
        // Get the mongodb connection
        if (dbTask == null) {
            PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(), CodecRegistries.fromProviders(pojoCodecProvider));

            dbTask = getConnection().getDatabase(db_name).withCodecRegistry(pojoCodecRegistry);
            //dbTask = getConnection().getDatabase(db_name);
        }

        // Get the mongodb collection.
        MongoCollection myCollection = dbTask.getCollection(db_collection_name);

        return myCollection;
    }


    // Method to search a user in the mongodb
    public static User searchUserInDb(String userName, String passWord) {
        boolean user_found = false;
        String db_collection_name = "users";

        // Get the mongodb collection.
        MongoCollection<User> col = getTable(db_collection_name).withDocumentClass(User.class);

        User myUser = col.find(Filters.and(eq("userName", userName), eq("passWord", passWord)), User.class).first();

        System.out.println("Found this user:" + myUser);
        return myUser;


    }

    //list of Group
    public static ArrayList<Group> getAllGroups()
    {
        MongoCollection<Group> taskCollection = getTable("groups").withDocumentClass(Task.class);

        ArrayList<Group> result = null;
            result = taskCollection.find(Group.class).into(new ArrayList<Group>());
        return result;
    }

    // Update User
    public static boolean upsertUser(User myUpdate) {

        try {
            MongoCollection<Document> userCollection = getTable("users");
            // findOneAndUpdate using JSON into MongoDB
            System.out.println(myUpdate);

            //generate max number for User id from DB
            /*if(myUpdate.getId()==0) {
                List<Document> ids = new ArrayList<Document>();


                userCollection.aggregate(Arrays.asList(
                        Aggregates.sort(Sorts.descending("_id")),
                        Aggregates.limit(1)
                )).into(ids);

                System.out.println(ids.get(0));

               // if (ids.size() != 0) myUpdate.setId(ids.get(0).getId() + 1);
               // else myUpdate.setId(1);
            }*/

            //end

            ObjectMapper mapper = new ObjectMapper();
            BasicDBObject query = new BasicDBObject();
            query.append("_id", myUpdate.getId());
            String jsonString = mapper.writeValueAsString(myUpdate);
            BasicDBObject doc = BasicDBObject.parse(jsonString);
            Bson newDocument = new Document("$set", doc);


            Document resultDocument = userCollection.findOneAndUpdate(query,
                    newDocument, (new FindOneAndUpdateOptions()).upsert(true).returnDocument(ReturnDocument.AFTER));

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    //1. insert User
    public static boolean insertUser(User inputUser) {

        // Get the mongodb collection.
        MongoCollection<User> userCollection = getTable("users").withDocumentClass(User.class);
        try {
            List<User> ids = new ArrayList<User>();
            userCollection.aggregate(Arrays.asList(
                    Aggregates.sort(Sorts.descending("_id")),
                    Aggregates.limit(1)
            )).into(ids);

            if (ids.size() != 0) inputUser.setId(ids.get(0).getId() + 1);
            else inputUser.setId(1);

            userCollection.insertOne(inputUser);
        }
        catch (Exception ex) {
            System.out.println("insertUser error");
            ex.printStackTrace();
            return false;
        }

        return true;
    }


    //2. insert Task
    public static boolean insertTask(Task inputTask) {
//        System.out.println("B=================================");
//        System.out.println(inputTask.toString());
//        System.out.println("E=================================");
        // Get the mongodb collection.
        MongoCollection<Task> taskCollection = getTable("tasklist").withDocumentClass(Task.class);

        try {
            List<Task> ids = new ArrayList<Task>();
            taskCollection.aggregate(
                    Arrays.asList(
                            Aggregates.sort(Sorts.descending("_id")),
                            Aggregates.limit(1)
                    )
            ).into(ids);

            if (ids.size() != 0) {
                inputTask.setId(ids.get(0).getId() + 1);
            } else {
                inputTask.setId(1);
            }
            taskCollection.insertOne(inputTask);
        }
        catch (Exception ex)
        {
            System.out.println("insertTask error");
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    //3. Update Task
    public static boolean updateTask(Task myUpdate) {

        try {
            MongoCollection<Document> taskCollection = getTable("tasklist");
            // findOneAndUpdate using JSON into MongoDB
            System.out.println(myUpdate);
            ObjectMapper mapper = new ObjectMapper();
            BasicDBObject query = new BasicDBObject();
            query.append("_id", myUpdate.getId());
            String jsonString = mapper.writeValueAsString(myUpdate);
            BasicDBObject doc = BasicDBObject.parse(jsonString);
            Bson newDocument = new Document("$set", doc);


            Document resultDocument = taskCollection.findOneAndUpdate(query,
                    newDocument, (new FindOneAndUpdateOptions()).upsert(true).returnDocument(ReturnDocument.AFTER));

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    // Mark task as Complete
    public static boolean completeTask(int taskId) {
        boolean res = true;

        try {
            MongoCollection<Document> taskCollection = getTable("tasklist");
            taskCollection.updateOne(eq("_id", taskId),new Document("$set", new Document("status", "Complete")));

        } catch(Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }
    // Deprecated method
    //4. insert User
//    public static ArrayList<Task> getTaskList(int assignedUserId) {
//        MongoCollection<Task> taskCollection = getTable("tasklist").withDocumentClass(Task.class);
//
//        ArrayList<Task> result = null;
//        if (assignedUserId == 0) {
//            System.out.println("Search All");
//            result = taskCollection.find(Task.class).into(new ArrayList<Task>());
//        } else {
//
//            System.out.println("Search by Condition");
//            result = taskCollection.find(Filters.eq("assignUser", assignedUserId), Task.class).into(new ArrayList<Task>());
//        }
//
//        result.forEach(System.out::println);
//        return result;
//
//
//    }

    public static Document getGroupofUser(int userId) {
        MongoCollection<Document> userCol = getTable("users");

        Document res = userCol.find(eq("_id", userId))
                                .projection(fields(include("groupId", "group"), exclude("_id")))
                                .first();
        System.out.println("**************************************");
        System.out.println("getGroupofUser result: " + res);

        return res;
    }

    public static ArrayList<Document> getUserList(int groupId) {
        MongoCollection<Document> userCol = getTable("users");
        ArrayList<Document> res = new ArrayList<>();
        if(groupId > 0)
            userCol.find(eq("groupId", groupId))
                    .projection(fields(include("_id", "userName"))).into(res);
        else
            userCol.find()
                    .projection(fields(include("_id", "userName"))).into(res);

        return res;
    }

    public static ArrayList<Document> getTaskListByGroup(int groupId) {
        ArrayList<Document> res = new ArrayList<>();
        MongoCollection<Document> taskCol = getTable("tasklist");

        getUserList(groupId).forEach(e -> res.addAll(getTaskListJson((int)e.get("_id"))));

        return res;
    }





    public static ArrayList<Document> getTaskListJson(int assignedUserId) {
        // View DB data
        List<Document> taskListDB = new ArrayList<>();
        getTable("tasklist").find().into(taskListDB);

        List<Document> usersDB = new ArrayList<>();
        getTable("users").find().into(usersDB);
        // End of View DB data

        List<Document> result = new ArrayList<>();
        MongoCollection taskList = getTable("tasklist");

        Document fieldsAs = new Document("from", "users");
        fieldsAs.put("localField", "assignUser");
        fieldsAs.put("foreignField", "_id");
        fieldsAs.put("as", "AssUser");
        Document lookupAs = new Document("$lookup", fieldsAs);

        Document fieldsCr = new Document("from", "users");
        fieldsCr.put("localField", "createUser");
        fieldsCr.put("foreignField", "_id");
        fieldsCr.put("as", "CrUser");
        Document lookupCr = new Document("$lookup", fieldsCr);

        List<Bson> filters = new ArrayList<>();
        filters.add(lookupAs);
        filters.add(lookupCr);

        if(assignedUserId > 0) {
            Document con = new Document("assignUser", assignedUserId);
            filters.add(new Document("$match", con));
        }

        Collection<Document> it = taskList.aggregate(filters).into(new ArrayList<Document>());

        return new ArrayList<Document>(it);

    }


    public static String tasksUserInfo(int userId)
    {
        return "";
    }


    /*---------------------------Test Code with void main--------------------------*/

    //4. insert User
    public static void testInsertUser() {
//        User u = new User("maimai", "maimai", "thi.tran@mum.edu", "0908112345", 1, "IT");
//        insertUser(u);
        MongoCollection<Task> taskCollection = getTable("users").withDocumentClass(User.class);
        Collection<User> result = taskCollection.find(User.class).into(new ArrayList<User>());

        result.forEach(System.out::println);


    }


    public static void insertGroupName() {

        // Get the mongodb collection.
        MongoCollection userCollection = getTable("groups");
        userCollection.deleteMany(new Document());

        MongoCollection<Document> groupsCollection = getTable("groups");
        groupsCollection.insertOne(new Document().append("_id",1).append("name","IT"));
        groupsCollection.insertOne(new Document().append("_id",2).append("name","Sales"));
        groupsCollection.insertOne(new Document().append("_id",3).append("name","Finance"));
        groupsCollection.insertOne(new Document().append("_id",4).append("name","HR"));


    }

    public static void testUpdateTask() {

        List<Task> taskList = new MockData().retrieveTaskList();
        taskList.forEach((task) -> System.out.println(task.getTask()));
        taskList.forEach((task) -> task.setTask(task.getTask() + " updated"));

        taskList.forEach((task) -> {
            updateTask(task);
        });
    }

    public static void testInsertTask() {
//        Task t = new Task("Mai task Feb 03", "2017-02-03", "Personal");
//        insertTask(t);
        MongoCollection<Task> taskCollection = getTable("tasklist").withDocumentClass(Task.class);
        Collection<Task> result = taskCollection.find(Task.class).into(new ArrayList<Task>());

        result.forEach(System.out::println);
    }

    public static void MockDatane() {
        //insert data for Users
        ArrayList<User> userList = new ArrayList<>();

        userList.add(new User("account", "account", "account@mum.edu", "016414523688", 1, "IT"));
        userList.add(new User("compro", "compro", "compro@mum.edu", "016414523689", 1, "IT"));
        userList.add(new User("admission", "admission", "admission@mum.edu", "016414523687", 2, "Sales"));
        userList.add(new User("marketing", "marketing", "marketing@mum.edu", "016414523680", 2, "Sales"));


        userList.forEach((user) -> System.out.println(user.getUserName()));
        userList.forEach((user) -> {
            insertUser(user);
        });


        // insert Tasks for account User
        User insertTaskUser = searchUserInDb("account", "account");


        //insert data for Tasks
        List<Task> taskList = new MockData().retrieveTaskList();
        taskList.forEach((task) -> System.out.println(task.getTask()));
        taskList.forEach((task) -> {
            task.setAssignUser(insertTaskUser.getId());
            task.setCreateUser(insertTaskUser.getId());
            //task.setPriority(1);
            //task.setStatus("New");
            insertTask(task);
        });


    }

    public static void main(String[] args) {

//
//
        //Drop database
//        MongoCollection userCollection = getTable("users");
//        userCollection.deleteMany(new Document());
//
//        MongoCollection taskCollection = getTable("tasklist");
//        taskCollection.deleteMany(new Document());
//
//        //Mockup Data
//        MockDatane();

        //Testing

        //searchUserInDb("account", "account");

        //testUpdateTask();

//        User search = searchUserInDb("account", "account");
//        getTaskList(search.getId());


       // testInsertTask();


//        insertGroupName();

//        getAllGroups().forEach(System.out::println);

//        completeTask(25);
//        getTaskListJson(1).forEach(System.out::println);
    }

}
