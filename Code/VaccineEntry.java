public class VaccineEntry {
    private String firstName;
    private String lastName;
    private String date;
    private String location;
    private String type;
    private int idNumber;

    //construct a new null vaccine entry
    public VaccineEntry() {
        this.firstName = null;
        this.lastName = null;
        this.date = null;
        this.location = null;
        this.type = null;
    }

    //************getters********************
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public String getDate() {
        return this.date;
    }
    public String getLocation() {
        return this.location;
    }
    public String getType() {
        return this.type;
    }
    public int getIdNumber() {
        return this.idNumber;
    }

    //**************setters******************
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setLocation(String location) {
        this.location = location;
    }public void setType(String type) {
        this.type = type;
    }
    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }
    
    //print the data from the entry
    public String toString() {
       return "ID: "+this.idNumber+"\nFirst Name: "+this.firstName+"\nLastName: "+this.lastName+"\nType: "+this.type+"\nDate: "+this.date+"\nLocation: "+this.location+"\n\n";
    }
}
