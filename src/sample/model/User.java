package sample.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by League of Users on 2017/10/01.
 */
public class User {

    private static User user = new User();
    private UserItem userItem;
    private static DatabaseReference mDatabase;

    /**
     * Create a user with userItem
     * @param userItem the particular user with basic info
     */
    public static User getInstance(UserItem userItem) {
        user.setUserItem(userItem);
        return user;
    }

    public static User getInstance() {
        return user;
    }

    /**
     * Getter method for userItem
     * @return the userItem
     */
    public UserItem getUserItem() {
        return userItem;
    }

    /**
     * Setter for user
     * @param user the username to be set
     * @param pass the password to be set
     */
    public void setUserItem(UserItem userItem) {
        this.userItem = userItem;
    }

    /**
     * the getter for firebase
     * @param mDatabase the database to be get
     * @return the database
     */
//    public static DatabaseReference getmDatabase(DatabaseReference mDatabase) {
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        return mDatabase;
//    }

//    /**
//     *
//     * @param jsonObj the info about user read from database
//     * @return the user from Json
//     * @throws JSONException if the jsonObj parameter is null
//     */
//    public static User fromJson(JSONObject jsonObj) throws JSONException {
//        return new User(userItem);
//
//    }
}

