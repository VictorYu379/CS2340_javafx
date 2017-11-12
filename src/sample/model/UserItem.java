package sample.model;

import java.util.ArrayList;

/**
 * Created by xuruihan on 2017/9/30.
 */

public class UserItem {
    String userID;
    ArrayList<String> reports = new ArrayList<>();
    Boolean isAdmin;

    /**
     * the constructor of UserItem
     * @param userID the username of the userItem
     * @param password the password of userItem
     * @param isAdmin the account status of the userItem
     */
    public UserItem (String userID, ArrayList<String> reports, Boolean isAdmin) {
        this.userID = userID;
        this.reports = reports;
        this.isAdmin = isAdmin;
    }

    /**
     * Setter for username
     * @param user the user to be set
     */
    public void setUser(String user) {
        userID = user;
    }

    public void setReportHistory(ArrayList<String> reports) {
        this.reports = reports;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
