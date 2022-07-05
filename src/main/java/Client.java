import ObserverPattern.PatientTable;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.util.BundleUtil;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class where all the API Calls will happen
 * can be called from GUI to make things simple
 */
public class Client {

    GUI gui;

    FhirContext ctx;

    IGenericClient client;


    PatientTable patTable = new PatientTable();


    /**
     * This initialises the GUI to the Practitioner ID input screen. Also initialises the connection to the client.
     */
    public void initial(){
        gui = new GUI();

        gui.showIDScreen(this);

        ctx = FhirContext.forR4();
        client = ctx.newRestfulGenericClient("https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/");
        ctx.getRestfulClientFactory().setSocketTimeout(200 * 1000);
    }

    /**
     * This is the method that is called when the button is clicked, meaning a practitioner has input an ID
     * So this has ID as a param. API calls happen here according to the ID.
     * @param ID a string
     */
    public void initialPopulate(String ID){
        this.populateInitialData(ID);
    }


    public void getUpdatedCholesterolData(){
        String linkStart = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?patient=";
        String linkEnd = "&code=2093-3&_sort=date";
        for(int i = 0; i < patTable.getpatTable().size(); i++){
            List<IBaseResource> currentPatientObservations = searchDatabase(linkStart + patTable.getpatTable().get(i).getID() + linkEnd);
            if(currentPatientObservations.size() != 0){
                Observation currentObservation = client.read().resource(Observation.class).withId(currentPatientObservations.get(currentPatientObservations.size() - 1).getIdElement()).execute();
                patTable.getpatTable().get(i).setTotCholesterol(currentObservation.getValueQuantity().getValue().toString());
            }
        }
        patTable.notifyObservers();
    }


    public List<IBaseResource> searchDatabase(String url){
        List<IBaseResource> searchResultsList = new ArrayList<>();

        Bundle bundle = client.search()
                .byUrl(url)
                .returnBundle(Bundle.class)
                .execute();
        searchResultsList.addAll(BundleUtil.toListOfResources(ctx, bundle));

        while (bundle.getLink(IBaseBundle.LINK_NEXT) != null) {
            bundle = client
                    .loadPage()
                    .next(bundle)
                    .execute();
            searchResultsList.addAll(BundleUtil.toListOfResources(ctx, bundle));
        }

        return searchResultsList;
    }

    public String identifyIdentifierFromId(String PracId){
        Practitioner pracIdentifier = client.read().resource(Practitioner.class).withId(PracId).execute();
        return pracIdentifier.getIdentifierFirstRep().getValue();
    }

    public List<String> getPatientsFromIdentifier(String PracIdentifier){
        String linkStart = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Encounter?participant.identifier=http://hl7.org/fhir/sid/us-npi%7C";
        String linkEnd = "&_include=Encounter.participant.individual&_include=Encounter.patient";

        List<IBaseResource> patientBundle = searchDatabase(linkStart + PracIdentifier + linkEnd);

        List<String> patientList = new ArrayList<>();
        for(int f = 0; f < patientBundle.size(); f++){
            Encounter encounter = client.read().resource(Encounter.class).withId(patientBundle.get(f).getIdElement().getIdPart()).execute();
            String currentPatient = encounter
                    .getSubject()
                    .getReference()
                    .replaceAll("Patient/", "");

            if(patientList.size() == 0){
                patientList.add(currentPatient);
            }

            if(patientList.contains(currentPatient) == false){
                patientList.add(currentPatient);
            }
        }
        return patientList;
    }

    public void addPatientDataToReport(List<String> patientIds){
        String linkStart = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?patient=";
        String linkEndChol = "&code=2093-3&_sort=-date";
        String linkEndBlood = "&code=55284-4&_sort=-date";
        String currentObservationCholValue, currentObservationCholTime;
        String[] currentObservationSystolicValue, currentObservationDiastolicValue, currentObservationBloodTime;

        for(int i = 0; i < patientIds.size(); i++){
            Patient currentPatient = client.read().resource(Patient.class).withId(patientIds.get(i)).execute();

            //Search Database for cholesterol data
            List<IBaseResource> currentPatientObservationsChol = searchDatabase(linkStart + patientIds.get(i) + linkEndChol);
            List<IBaseResource> currentPatientObservationsBlood = searchDatabase(linkStart + patientIds.get(i) + linkEndBlood);

            //Checking if patient has either cholesterol data or blood data
            if(currentPatientObservationsChol.size() == 0 && currentPatientObservationsBlood.size() == 0){
                continue;
            }

            //Fill in values for cholesterol data if applicable
            if(currentPatientObservationsChol.size() == 0){
                currentObservationCholValue = "-";
                currentObservationCholTime = "-";
            } else {
                Observation currentObservationChol = client.read().resource(Observation.class).withId(currentPatientObservationsChol.get(0).getIdElement()).execute();
                currentObservationCholValue = currentObservationChol.getValueQuantity().getValue().toString();
                currentObservationCholTime = currentObservationChol.getEffectiveDateTimeType().toHumanDisplay();
            }

            //Fill in values for cholesterol data if applicable
            currentObservationSystolicValue = new String[]{"-", "-", "-", "-", "-"};
            currentObservationDiastolicValue = new String[]{"-", "-", "-", "-", "-"};
            currentObservationBloodTime = new String[]{"-", "-", "-", "-", "-"};

            if(currentPatientObservationsBlood.size() != 0){
                for(int j = 0; j < currentPatientObservationsBlood.size() && j < 5; j ++){
                    Observation currentObservationBlood = client.read().resource(Observation.class).withId(currentPatientObservationsBlood.get(j).getIdElement()).execute();
                    currentObservationDiastolicValue[j] = currentObservationBlood.getComponent().get(0).getValueQuantity().getValue().toString();
                    currentObservationSystolicValue[j] = currentObservationBlood.getComponent().get(0).getValueQuantity().getValue().toString();
                    currentObservationBloodTime[j] = currentObservationBlood.getEffectiveDateTimeType().toHumanDisplay();
                }
            }

            patTable.addPatient(new ObserverPattern.Patient(
                    currentPatient.getNameFirstRep().getGivenAsSingleString() + " " + currentPatient.getNameFirstRep().getFamily(),
                    currentObservationCholValue,
                    currentObservationCholTime,
                    //currentObservationChol.getValueQuantity().getValue().toString(),
                    //currentObservationChol.getEffectiveDateTimeType().toHumanDisplay(),
                    currentPatient.getIdElement().getIdPart(),
                    currentPatient.getGender().toString(),
                    currentPatient.getBirthDate().toString(),
                    currentPatient.getAddress().get(0).getLine().get(0).toString(),
                    currentObservationSystolicValue,
                    currentObservationDiastolicValue,
                    currentObservationBloodTime
            ));
            
        }
        return;
    }

    public void populateInitialData(String PracId){
        String practitionerIdentifier = identifyIdentifierFromId(PracId);
        List<String> patientList = getPatientsFromIdentifier(practitionerIdentifier);
        addPatientDataToReport(patientList);
        gui.createApp(patTable);
    }

}