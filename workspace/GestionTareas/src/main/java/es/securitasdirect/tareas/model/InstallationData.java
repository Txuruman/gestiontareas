package es.securitasdirect.tareas.model;

/**
 * Subconjunto de datos para mostrar en la pantalla sobre la instalación.
 */
public class InstallationData {

    private String numeroInstalacion;

    private String titular;

    private String panel;

    private String version;

    private String requerido;

    private String personaContacto;

    private String horarioDesde;

    private String horarioHasta;

    public String getNumeroInstalacion() {
        return numeroInstalacion;
    }

    public void setNumeroInstalacion(String numeroInstalacion) {
        this.numeroInstalacion = numeroInstalacion;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getPanel() {
        return panel;
    }

    public void setPanel(String panel) {
        this.panel = panel;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRequerido() {
        return requerido;
    }

    public void setRequerido(String requerido) {
        this.requerido = requerido;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public String getHorarioDesde() {
        return horarioDesde;
    }

    public void setHorarioDesde(String horarioDesde) {
        this.horarioDesde = horarioDesde;
    }

    public String getHorarioHasta() {
        return horarioHasta;
    }

    public void setHorarioHasta(String horarioHasta) {
        this.horarioHasta = horarioHasta;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InstallationData{");
        sb.append("numeroInstalacion=").append(numeroInstalacion);
        sb.append(", titular='").append(titular).append('\'');
        sb.append(", panel='").append(panel).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append(", requerido='").append(requerido).append('\'');
        sb.append(", personaContacto='").append(personaContacto).append('\'');
        sb.append(", horarioDesde='").append(horarioDesde).append('\'');
        sb.append(", horarioHasta='").append(horarioHasta).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
