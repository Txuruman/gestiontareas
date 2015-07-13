package es.securitasdirect.senales.model;

/**
 * Created by Roberto on 10/07/2015.
 */
public class JMSConnectionData {
    private String aliasName;
    private String bus;
    private String port;
    private String qfcName;
    private String queueName;
    private String user;
    private String pass;

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getQfcName() {
        return qfcName;
    }

    public void setQfcName(String qfcName) {
        this.qfcName = qfcName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "JMSConnectionData{" +
                "aliasName='" + aliasName + '\'' +
                ", bus='" + bus + '\'' +
                ", port='" + port + '\'' +
                ", qfcName='" + qfcName + '\'' +
                ", queueName='" + queueName + '\'' +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
