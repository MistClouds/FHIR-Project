package ObserverPattern;

/**
 * The Patient class that makes it easy to store all data related to a Patient in one place.
 * Is used in the patientTable class later.
 */
public class Patient {
    //all stored as strings for now - its easy to parse them later.
    private String name;
    private String totCholesterol;
    private String time;
    private String ID;
    private String gender;
    private String birthday;
    private String address;
    private String[] sysBloodPressure;
    private String[] diaBloodPressure;
    private String[] bloodTime;

    //flags for whether or not they should appear in various views.
    private boolean inTableChol;
    private boolean inTableBlood;
    private boolean inExpanded;
    private boolean inBlood;

    /**
     * Constructor for a patient. Just stores all the fields in the right spots.
     * @param name a string
     * @param totCholesterol a string. will be casted to a float later, so no units.
     * @param time a string
     * @param ID a string
     * @param gender a string
     * @param birthday a string
     * @param address a string
     */
    public Patient(String name, String totCholesterol, String time, String ID,
                   String gender, String birthday, String address, String[] sysBloodPressure,
                   String[] diaBloodPressure, String[] bloodTime){
        this.name = name;
        this.totCholesterol = totCholesterol;
        this.time = time;
        this.ID = ID;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.sysBloodPressure = sysBloodPressure;
        this.diaBloodPressure = diaBloodPressure;
        this.bloodTime = bloodTime;

        //set the flags to false.
        this.inTableChol = false;
        this.inTableBlood = false;

        this.inExpanded = false;
        this.inBlood = false;
    }

    /**
     * getter for the patient's name.
     * @return a string
     */
    public String getName() {
        return name;
    }

    /**
     * getter for the patient's total blood cholesterol.
     * @return a string
     */
    public String getTotCholesterol() {
        return totCholesterol;
    }
    /**
     * getter for the time the patient's stats were reported.
     * @return a string
     */
    public String getTime() {
        return time;
    }
    /**
     * getter for the patient's ID.
     * @return a string
     */
    public String getID() {
        return ID;
    }

    /**
     * getter for the patient's gender.
     * @return a string
     */
    public String getGender() {
        return gender;
    }

    /**
     * getter for the patient's birthday.
     * @return a string
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * getter for the patient's total blood cholesterol.
     * @return a string
     */
    public String getAddress() {
        return address;
    }

    /**
     * getter for the patient's 5 most recent systolic blood pressure.
     * @return a string
     */
    public String[] getSysBloodPressure(){
        return sysBloodPressure;
    }

    /**
     * getter for the patient's 5 most recent diastolic blood pressure.
     * @return a string
     */
    public String[] getDiaBloodPressure(){
        return diaBloodPressure;
    }

    /**
     * getter for the patient's 5 most recent blood pressure observation times.
     * @return a string
     */
    public String[] getBloodTime(){
        return bloodTime;
    }

    /**
     * setter for the patient's name.
     * @param name a string
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setter for the patient's total blood cholesterol data.
     * @param totCholesterol a string
     */
    public void setTotCholesterol(String totCholesterol) {
        this.totCholesterol = totCholesterol;
    }

    /**
     * setter for the patient's total blood cholesterol data.
     * @param diaBloodPressure a string[]
     */
    public void setDiaBloodPressure(String[] diaBloodPressure) {
        this.diaBloodPressure = diaBloodPressure;
    }

    /**
     * setter for the patient's total blood cholesterol data.
     * @param sysBloodPressure a string[]
     */
    public void setSysBloodPressure(String[] sysBloodPressure) {
        this.sysBloodPressure = sysBloodPressure;
    }

    /**
     * setter for the patient's total blood cholesterol data.
     * @param bloodTime a string[]
     */
    public void setBloodTime(String[] bloodTime) {
        this.bloodTime = bloodTime;
    }

    /**
     * setter for the time when the patient's report was made.
     * @param time a string
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * setter for the patient's gender
     * @param gender a string
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * getter to check whether or not the patient is in the table.
     * @return a boolean
     */
    public boolean isInTableChol() {
        return inTableChol;
    }

    /**
     * getter to check whether or not the patient is in the table.
     * @return a boolean
     */
    public boolean isInTableBlood() {
        return inTableBlood;
    }

    /**
     * getter to check whether or not the patient is in the table.
     * @return a boolean
     */
    public boolean isInBlood() {
        return inBlood;
    }


    /**
     * setter for if a patient should appear in the table with cholesterol values.
     * @param inTableChol a boolean
     */
    public void setInTableChol(boolean inTableChol) {
        this.inTableChol = inTableChol;
    }

    /**
     * setter for if a patient should appear in the table with blood pressure values.
     * @param inTableBlood a boolean
     */
    public void setInTableBlood(boolean inTableBlood)
    {
        this.inTableBlood = inTableBlood;
    }

    /**
     * setter for if a patient should appear in the table with blood pressure values.
     * @param inBlood a boolean
     */
    public void setInBlood(boolean inBlood)
    {
        this.inBlood = inBlood;
    }


    /**
     * getter to check whether or not the patient is in the expanded view.
     * @return a boolean
     */
    public boolean isInExpanded() {
        return inExpanded;
    }

    /**
     * setter for if a patient should appear in the expanded view.
     * @param inExpanded a boolean
     */
    public void setInExpanded(boolean inExpanded) {
        this.inExpanded = inExpanded;
    }


}
