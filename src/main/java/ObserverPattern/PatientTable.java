package ObserverPattern;

import com.sun.security.ntlm.Client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

/**
 * class that represents the collection of patients to be displayed. Is an observable data source.
 * Holds an arraylist of patients.
 */
public class PatientTable extends Observable {
    private ArrayList<Patient> patTable; //where the patients are stored

    private int sysFilter = 130;
    private int diaFilter = 80;

    /**
     * Constructor for the patientTable. Doesn't require any parameters, patients can be added later.
     * Maybe should make a copy constructor later: TODO
     */
    public PatientTable(){
        this.patTable = new ArrayList<Patient>();
    }


    /**
     * method for adding a patient to the table.
     * @param patient a Patient
     */
    public void addPatient(Patient patient){
        patTable.add(patient);
        notifyObservers();
    }

    /**
     * flags whether the patient should be in the gui elements or not. flips true to false or false to true.
     * @param index the index of the patient
     */
    public void flagPatientTableChol(int index){
        boolean curr = patTable.get(index).isInTableChol();//get current boolean
        patTable.get(index).setInTableChol(!curr);//flip it

        notifyObservers();//update the views after this (only table is updated here, really)
    }

    /**
     * flags whether the patient should be in the gui elements or not. flips true to false or false to true.
     * @param index the index of the patient
     */
    public void flagPatientTableBlood(int index){
        boolean curr = patTable.get(index).isInTableBlood();//get current boolean
        patTable.get(index).setInTableBlood(!curr);//flip it

        notifyObservers();//update the views after this (only table is updated here, really)
    }

    /**
     * flags whether the patient should be in the expanded view or not. flips everything to false then the index to true
     * @param index the index of the patient
     */
    public void flagPatientExpanded(int index){
        for (int i = 0; i < patTable.size(); i++){
            patTable.get(i).setInExpanded(false); //make everything false
        }

        patTable.get(index).setInExpanded(true); //set the index to true

        notifyObservers();// update the views after(only the expanded one really updates)
    }

    /**
     * flags whether the patient should be in the gui elements or not. flips true to false or false to true.
     * @param index the index of the patient
     */
    public void flagPatientBlood(int index){
        boolean curr = patTable.get(index).isInBlood();//get current boolean
        patTable.get(index).setInBlood(!curr);//flip it

        notifyObservers();
    }

    /**
     * getter for filter value
     * @return an int
     */
    public int getDiaFilter()
    {
        return diaFilter;
    }

    /**
     * getter for filter value
     * @return an int
     */
    public int getSysFilter()
    {
        return sysFilter;
    }

    /**
     * setter for diastolic blood filter
     * @param diaFilter an int corresponding to the filter
     */
    public void setDiaFilter(int diaFilter)
    {
        this.diaFilter = diaFilter;
    }

    /**
     * setter for diastolic blood filter
     * @param sysFilter an int corresponding to the filter
     */
    public void setSysFilter(int sysFilter)
    {
        this.sysFilter = sysFilter;
    }

    /**
     * gets a readonly version of the patient list
     * @return a list
     */
    public List<Patient> getpatTable(){
        return Collections.unmodifiableList(patTable);
    }

}
