package utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import model.Task;
import model.User;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

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

        User myUser = col.find(Filters.and(Filters.eq("userName", userName), Filters.eq("passWord", passWord)), User.class).first();

        System.out.println("Found this user:" + myUser);
        return myUser;


    }

    //1. insert User
    public static boolean insertUser(User inputUser) {

        // Get the mongodb collection.
        MongoCollection<User> userCollection = getTable("users").withDocumentClass(User.class);
        //Is username existing?
        User myDoc = userCollection.find(Filters.eq("userName", inputUser.getUserName())).first();
        //if No
        if (myDoc == null) {
            userCollection.insertOne(inputUser);
            return true;

        } else {
            System.out.println(myDoc.toString());
            System.out.println("User" + inputUser.getUserName() + " already exists");
            return false;
        }
    }

    //2. insert Task
    public static boolean insertTask(Task inputTask) {

        // Get the mongodb collection.
        MongoCollection taskCollection = getTable("tasklist").withDocumentClass(Task.class);

    if(inputTask.getId()!=0)
        taskCollection.insertOne(inputTask);
    else
        return false;
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

    //4. insert User
    public static ArrayList<Task> getTaskList(int assignedUserId) {
        MongoCollection<Task> taskCollection = getTable("tasklist").withDocumentClass(Task.class);

        ArrayList<Task> result = null;
        if (assignedUserId == 0) {
            System.out.println("Search All");
            result = taskCollection.find(Task.class).into(new ArrayList<Task>());
        } else {

            System.out.println("Search by Condition");
            result = taskCollection.find(Filters.eq("assignUser", assignedUserId), Task.class).into(new ArrayList<Task>());
        }

        result.forEach(System.out::println);
        return result;


    }

    /*---------------------------Test Code with void main--------------------------*/


    public static void testUpdateTask() {

        List<Task> taskList = new MockData().retrieveTaskList();
        taskList.forEach((task) -> System.out.println(task.getTask()));
        taskList.forEach((task) -> task.setTask(task.getTask() + " updated"));

        taskList.forEach((task) -> {
            updateTask(task);
        });
    }

    ;


    public static void MockDatane() {
        //insert data for Users
        ArrayList<User> userList = new ArrayList<>();

        userList.add(new User(1, "account", "account", "account@mum.edu", "016414523688"));
        userList.add(new User(2, "compro", "compro", "compro@mum.edu", "016414523689"));
        userList.add(new User(3, "admission", "admission", "admission@mum.edu", "016414523687"));
        userList.add(new User(4, "marketing", "marketing", "marketing@mum.edu", "016414523680"));


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
            insertTask(task);
        });


    }

    public static void main(String[] args) {
        System.out.println("Bong Hoa");


        //Drop database
        MongoCollection userCollection = getTable("users");
        userCollection.deleteMany(new Document());

        MongoCollection taskCollection = getTable("tasklist");
        taskCollection.deleteMany(new Document());

        //Mockup Data
        MockDatane();

        //Testing

        //searchUserInDb("account", "account");

        //testUpdateTask();

        User search = searchUserInDb("account", "account");
        getTaskList(search.getId());




    }

}
