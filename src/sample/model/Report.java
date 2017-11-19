package sample.model;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ruixuan on 10/6/17.
 */

public class Report {

    private int key;
    private String date;
    private String locationType;
    private String zip;
    private String city;
    private String borough;
    private String longitude;
    private String latitude;
    private String address;

    /**
     * Default constructor of user to create a null report
     */
    public Report() {
        this.key = 0;
        this.date = null;
        this.locationType = null;
        this.zip = null;
        this.city = null;
        this.borough = null;
        this.longitude = null;
        this.latitude = null;
        this.address = null;
    }

    /**
     *
     * @param key report ID
     * @param date the date the report was created
     * @param locationType the type of location where the rat was seen
     * @param zip the zip code of the location
     * @param city the city where the rat was seen
     * @param borough the borough of the sighting location
     * @param longitude the longitude of the sighting location
     * @param latitude the latitude of the sighting location
     * @param address The address of the sighting location
     */
    public Report(int key, String date, String locationType, String zip, String city, String borough, String longitude,
                  String latitude, String address) {
        this.key = key;
        this.date = date;
        this.locationType = locationType;
        this.zip = zip;
        this.city = city;
        this.borough = borough;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    /**
     * Getter for description
     *@return the description
     */
    public String getDescription() {
        return "Date : " + date + "\nAddress : " + address;
    }


    /**
     * Getter for the borough
     * @return the borough info
     */

    public String getBorough() {
        return borough;
    }


    /**
     * Setter for the borough
     * @param borough the borought to be set
     */
    public void setBorough(String borough) {
        this.borough = borough;
    }

    /**
     * Getter for the key
     * @return the key info
     */
    public int getKey() {
        return key;
    }

    /**
     * Setter for the key
     * @param key the key to be set
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * Getter for the data
     * @return the date info
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter for the date
     * @param date the date to be set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter for the location type
     * @return the location type info
     */
    public CharSequence getLocationType() {
        return locationType;
    }

    /**
     * Setter for the location type
     * @param locationType the location type to be set
     */
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    /**
     * Getter for the zip
     * @return the zip info
     */
    public CharSequence getZip() {
        return zip;
    }

    /**
     * Setter for the zip
     * @param zip the zip info to be set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Getter for the city
     * @return the city info
     */
    public CharSequence getCity() {
        return city;
    }

    /**
     * Setter for the city
     * @param city the city info to be set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Getter for the longtitude
     * @return the longitude of the sight
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Setter for the longitude
     * @param longitude the longitude info to be set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Getter for the latitude
     * @return the latitude info of the sight
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Setter for the latitude
     * @param latitude the latitude info to be set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Getter for the address
     * @return the address info of the sight
     */
    public CharSequence getAddress() {
        return address;
    }

    /**
     * Setter for the address
     * @param address the address info to be set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    //Help ReportManager to Test this method
//    public Map<String, String> assignGraphData(Iterable<Report> returnArrayList, LoadingView callback) {
//        Map<String, String> returnMap = new HashMap<>();
//        if (returnArrayList != null) {
//            for (Report report : returnArrayList) {
//                String key = report.getDate().substring(0, 4);
//                if (returnMap.get(key) == null) {
//                    returnMap.put(key, "1");
//                } else {
//                    returnMap.put(key, String.valueOf(Integer.parseInt(returnMap.get(key)) + 1));
//                }
//            }
//        }
//        return returnMap;
//    }
}
