package sample.model;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;
import java.util.List;

public class ReportManager {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;
    private Query query;
    private static ReportManager ourInstance = new ReportManager();

    public static ReportManager getInstance() {
        return ourInstance;
    }

    /**
     * Method to get the latest updated report
     * @param returnArrayList the updated arraylist of reports
     * @param amount limit to the last updated report
     * @param callback the loading view
     * @throws IllegalArgumentException if returnArrayList is null or amount is smaller than zero
     */
    public void getLatestReports(List<Report> returnArrayList, int amount, LoadingView callback) {
        if (returnArrayList == null || amount < 0) {
            throw new IllegalArgumentException("enter valid args");
        }
        callback.setUpLoadingView();
        query = mDatabase.child("Entries").orderByChild("Created Date").limitToLast(amount);
        reportsGetter(query, returnArrayList, callback);
    }

    /**
     * Methods to get reports by date
     * @param returnArrayList the result list of reports to be returned
     * @param startDate the start date
     * @param endDate the end date
     * @param callback the loading view
     */
    public void getReportsByDate(List<Report> returnArrayList, String startDate, String endDate, LoadingView callback) {
        callback.setUpLoadingView();
        query = mDatabase.child("Entries").orderByChild("Created Date").startAt(startDate).endAt(endDate);
        reportsGetter(query, returnArrayList, callback);
    }

    private void reportsGetter(Query query, List<Report> returnArrayList, LoadingView callback) {
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Report report = new Report(Integer.parseInt(postSnapshot.getKey()),
                            (String) postSnapshot.child("Created Date").getValue(),
                            (String) postSnapshot.child("Location Type").getValue(),
                            (String) postSnapshot.child("Incident Zip").getValue(),
                            (String) postSnapshot.child("City").getValue(),
                            (String) postSnapshot.child("Borough").getValue(),
                            (String) postSnapshot.child("Longitude").getValue(),
                            (String) postSnapshot.child("Latitude").getValue(),
                            (String) postSnapshot.child("Incident Address").getValue());
                    returnArrayList.add(report);
                }
                callback.setDownLoadingView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                System.out.println("The read failed: " + databaseError.getCode());
                // ...
            }
        });
    }

}
