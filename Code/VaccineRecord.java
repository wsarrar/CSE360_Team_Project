import java.util.ArrayList;
import java.util.List;
/*
    Holds all of the contents of each entry (row) in an ArrayList type format to be manipulated and added to from here
 */
public class VaccineRecord {
    //list of all the vaccine entries in the data table
    private List<VaccineEntry> dataRows;
    //constructor for the vaccine records
    public VaccineRecord() {
        this.dataRows = new ArrayList<VaccineEntry>();
    }
    //return the row at the index (starting at 0) of the list of data
    public VaccineEntry getRow(int row) {
        //check to see if in the row size
        if(row > dataRows.size() || row < 0) {
            System.out.println("Error: Trying to get row out of data range, accessing row "+row+" in size "+dataRows.size());
        }
        return dataRows.get(row);
    }
    //given a row, add it to the list
    public void addNewRow(VaccineEntry newRow) {
        dataRows.add(newRow);
    }
    //return how many rows (vaccine records) there are in the list
    public int size() {
        return dataRows.size();
    }
    //return the list of vaccine records
    public List<VaccineEntry> getRecord() {
        return dataRows;
    }
}
