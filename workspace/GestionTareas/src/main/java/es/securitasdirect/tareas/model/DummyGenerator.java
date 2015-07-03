package es.securitasdirect.tareas.model;

/**
 * Created by Javier Naval on 03/07/2015.
 */
public class DummyGenerator {

    public static InstallationData getInstallationData() {
        InstallationData ins = new InstallationData();
        ins.setNumeroInstalacion("111111");
        ins.setPersonaContacto("DUMMY");
        return ins;
    }
}
