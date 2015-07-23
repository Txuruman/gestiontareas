package es.securitasdirect.senales.model;

import java.util.List;

/**
 * Aditional information for each allowed type of signal
 */
/*
La forma en la que se implementa el control de señales permitidas mediante parámetros en el fichero de configuración de CCL (CCLconfig.properties) es la siguiente:

1)	Un parámetro ALLOWED_QSIGNALS que indicará una lista de los códigos de señal permitidos, separados por “;”.
2)	Para cada señal permitida:
2.1)  Un parámetro con el identificador para luego invocar el método de obtención de  calling list y campaña (XXX_SIGNAL_CL_ID).
2.2)  Otro parámetro para indicar el status permitido para la señal (XXX_SIGNAL_STATUS).Donde XXX es el código de señal. Pueden ser varios.

Ejemplo:

ALLOWED_QSIGNALS=ILS;UCB
ILS_SIGNAL_CL_ID=DIY_TAREAS_BTN
ILS_SIGNAL_STATUS=1
UCB_SIGNAL_CL_ID=DIY_TAREAS_BTN
UCB_SIGNAL_STATUS=2


 */
public class SignalMetadata {
    private String clId;
    private List<Integer> allowedStatus;

    public SignalMetadata() {
    }

    public SignalMetadata(String clId) {
        this.clId = clId;
    }

    public String getClId() {
        return clId;
    }

    public void setClId(String clId) {
        this.clId = clId;
    }

    public List<Integer> getAllowedStatus() {
        return allowedStatus;
    }

    public void setAllowedStatus(List<Integer> allowedStatus) {
        this.allowedStatus = allowedStatus;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SignalMetadata{");
        sb.append("clId='").append(clId).append('\'');
        sb.append(", allowedStatus=").append(allowedStatus);
        sb.append('}');
        return sb.toString();
    }
}
