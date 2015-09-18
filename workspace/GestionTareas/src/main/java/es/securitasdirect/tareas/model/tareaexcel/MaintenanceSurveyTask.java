package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

/**
 * Encuestas Mantenimientos.
 *
 *  MANTENIMIENTO		int	POST		SI
 *  TECNICO		varchar (10)	POST		SI
 *  JE		varchar (10)	POST		SI
 *  CC		varchar (5)	POST		SI
 *  RAZON		varchar (500)	POST		SI
 *  SOLUCION		varchar (500)	POST		SI
 *  COMPROMISO		varchar(500)	POST		SI
 *  DPTO_DESTINO		varchar (15)	POST		SI
 *  Motivo de cierre	?			Lista de valores		SI
 *  Conpensación	?					SI
 *
 *
 *
 * @author Team Vision
 */
public class MaintenanceSurveyTask extends TareaExcel {


    /**
     * Maintenance Number - Número de Mantenimiento
     */
    Integer maintenanceNumber;
    /**
     * Technician - Técnico.
     * varchar (10)
     */
    String technician;
    /**
     * Manager - Responsable, alias JE.
     * varchar(10)
     */
    String manager;
    /**
     * Cost Center - Centro de Coste, alias (CC).
     * varchar(5)
     */
    String costCenter;
    /**
     * ValuationKeyResaon - Razón clave de la valoración.
     * varchar (500)
     */
    String valuationKeyReason;
    /**
     * Solution - Solución.
     * varchar (500)
     */
    String solution;
    /**
     * Agreement - Compromiso.
     * varchar(500)
     */
    String agreement;
    /**
     * Destination department - Departamento destino.
     * varchar (15)
     */
    String destinationDepartment;


    public Integer getMaintenanceNumber() {
        return maintenanceNumber;
    }

    public void setMaintenanceNumber(Integer maintenanceNumber) {
        this.maintenanceNumber = maintenanceNumber;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getValuationKeyReason() {
        return valuationKeyReason;
    }

    public void setValuationKeyReason(String valuationKeyReason) {
        this.valuationKeyReason = valuationKeyReason;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getDestinationDepartment() {
        return destinationDepartment;
    }

    public void setDestinationDepartment(String destinationDepartment) {
        this.destinationDepartment = destinationDepartment;
    }
    
    public String getTypeName(){
    	return "TASK_TYPE_MAINTENANCE_SURVEY";
    }
    
    public void setTypeName(String type){
    	
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TareaEncuestaMantenimiento{");
        sb.append("maintenanceNumber=").append(maintenanceNumber);
        sb.append(", technician='").append(technician).append('\'');
        sb.append(", manager='").append(manager).append('\'');
        sb.append(", costCenter='").append(costCenter).append('\'');
        sb.append(", valuationKeyReason='").append(valuationKeyReason).append('\'');
        sb.append(", solution='").append(solution).append('\'');
        sb.append(", agreement='").append(agreement).append('\'');
        sb.append(", destinationDepartment='").append(destinationDepartment).append('\'');
        sb.append(", closingReason=").append(closingReason);
        sb.append(", compensation='").append(compensation).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
