package es.securitasdirect.tareas.model;

import java.util.Date;

/**
 * Model class for Create Task
 *
 * @author Team Vision
 */
public class CreateTaskModel {

    private InstallationData installationData;

    private Integer installationNumber;

    private String holder;

    private String telephone;

    private String panel;

    private String contact;

    private String version;

    private String requiredBy;

    //TODO definir tipo de dato
    private String hourFrom;

    private String hourTo;

    private String type;

    private String reason;

    private String comment;

    public Integer getInstallationNumber() {
        return installationNumber;
    }

    public void setInstallationNumber(Integer installationNumber) {
        this.installationNumber = installationNumber;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPanel() {
        return panel;
    }

    public void setPanel(String panel) {
        this.panel = panel;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRequiredBy() {
        return requiredBy;
    }

    public void setRequiredBy(String requiredBy) {
        this.requiredBy = requiredBy;
    }

    public String getHourFrom() {
        return hourFrom;
    }

    public void setHourFrom(String hourFrom) {
        this.hourFrom = hourFrom;
    }

    public String getHourTo() {
        return hourTo;
    }

    public void setHourTo(String hourTo) {
        this.hourTo = hourTo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public InstallationData getInstallationData() {
        return installationData;
    }

    public void setInstallationData(InstallationData installationData) {
        this.installationData = installationData;
    }

    @Override
    public String toString() {
        return "CreateTaskModel{" +
                "installationData=" + installationData +
                ", installationNumber=" + installationNumber +
                ", holder='" + holder + '\'' +
                ", telephone='" + telephone + '\'' +
                ", panel='" + panel + '\'' +
                ", contact='" + contact + '\'' +
                ", version='" + version + '\'' +
                ", requiredBy='" + requiredBy + '\'' +
                ", hourFrom='" + hourFrom + '\'' +
                ", hourTo='" + hourTo + '\'' +
                ", type='" + type + '\'' +
                ", reason='" + reason + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    /**
     *
     # CREAR TAREA
     createtask.installationnumber = N\u00b0 Instalaci\u00f3n
     createtask.button.searchinstallation = Buscar Instalaci\u00f3n
     createtask.holder = Titular
     createtask.telephone = Tel\u00e9fono
     createtask.panel = Panel
     createtask.contact = Persona de contacto
     createtask.version = Versi\u00f3n
     createtask.requiredby = Requerido por
     createtask.horary = Horario
     createtask.hour.from = Desde
     createtask.hour.to = Hasta
     createtask.type = Tipo
     createtask.reason = Motivo
     createtask.comment = Observaciones
     */

 }
