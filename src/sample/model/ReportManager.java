package sample.model;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

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
     * @param key limit to the last updated report
     * @param callback the loading view
     * @throws IllegalArgumentException if returnArrayList is null or amount is smaller than zero
     */
    public void getReportsByKey(ArrayList<Report> report, int key, LoadingView callback) {
        if (key / 100000000 > 0 && key / 100000000 < 10) {
            throw new IllegalArgumentException("enter valid args");
        }
        callback.setUpLoadingView();
        mDatabase.child("Entries").child(String.valueOf(key)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Report r = new Report(key,
                        (String) dataSnapshot.child("Created Date").getValue(),
                        (String) dataSnapshot.child("Location Type").getValue(),
                        (String) dataSnapshot.child("Incident Zip").getValue(),
                        (String) dataSnapshot.child("City").getValue(),
                        (String) dataSnapshot.child("Borough").getValue(),
                        (String) dataSnapshot.child("Longitude").getValue(),
                        (String) dataSnapshot.child("Latitude").getValue(),
                        (String) dataSnapshot.child("Incident Address").getValue()
                );
                report.add(r);
                callback.setDownLoadingView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
