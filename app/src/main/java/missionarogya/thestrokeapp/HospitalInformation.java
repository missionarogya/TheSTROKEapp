package missionarogya.thestrokeapp;

/**
 * Created by Sonali Sinha on 1/28/2016.
 */
public class HospitalInformation {
    private String hospitalInformation;
    private static HospitalInformation ourInstance = new HospitalInformation();

    private HospitalInformation(){}

    public static HospitalInformation getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(HospitalInformation ourInstance) {
        HospitalInformation.ourInstance = ourInstance;
    }

    public String getHospitalInformation() {
        return hospitalInformation;
    }

    public void setHospitalInformation(String hospitalInformation) {
        this.hospitalInformation = hospitalInformation;
    }
}
