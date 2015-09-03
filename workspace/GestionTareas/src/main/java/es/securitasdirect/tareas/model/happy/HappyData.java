package es.securitasdirect.tareas.model.happy;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Happy App status info
 */
public class HappyData {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date upSince;
    //Mapa con la lista de servicios y el estatus
    private Map<String, String> services;

    public Date getUpSince() {
        return upSince;
    }

    public void setUpSince(Date upSince) {
        this.upSince = upSince;
    }

    public Map<String, String> getServices() {
        return services;
    }

    public void setServices(Map<String, String> services) {
        this.services = services;
    }

    public void addService(String serviceName, String serviceStatus) {
        if (services==null) {
            services = new HashMap<String, String>();
        }
        services.put(serviceName,serviceStatus);
    }

    public void addService(String serviceName, String serviceStatus, String serviceDescription) {
        String desc = serviceStatus;
        if (serviceDescription!=null && !serviceDescription.isEmpty()) {
            desc=desc + " - " + serviceDescription;
        }
        addService(serviceName,desc);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HappyData{");
        sb.append("upSince=").append(upSince);
        sb.append(", services=").append(services);
        sb.append('}');
        return sb.toString();
    }
}
