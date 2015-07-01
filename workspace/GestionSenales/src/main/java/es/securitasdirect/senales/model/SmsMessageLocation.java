package es.securitasdirect.senales.model;

/**
 * Created by Roberto on 01/07/2015.
 * Bean class for the definition of located SMS messages by country, with all the possible messages.
 */
public class SmsMessageLocation {
    private String another;
    private String outOfWorkingHours;
    public static String DEFAULT = "DEF";


    public String getAnother() {
        return another;
    }

    public void setAnother(String another) {
        this.another = another;
    }

    public String getOutOfWorkingHours() {
        return outOfWorkingHours;
    }

    public void setOutOfWorkingHours(String outOfWorkingHours) {
        this.outOfWorkingHours = outOfWorkingHours;
    }

    @Override
    public String toString() {
        return "SmsMessageLocation{" +
                "another='" + another + '\'' +
                ", outOfWorkingHours='" + outOfWorkingHours + '\'' +
                '}';
    }
}
