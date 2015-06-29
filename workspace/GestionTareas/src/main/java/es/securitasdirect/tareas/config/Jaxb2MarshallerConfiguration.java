package es.securitasdirect.tareas.config;

import es.securitasdirect.tareas.support.XmlMarshaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.Marshaller;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class Jaxb2MarshallerConfiguration {
    /**
     * Marshaller for domain objects
     */
    @Bean
    public XmlMarshaller xmlMarshaller() {
        XmlMarshaller xmlMarshaller = new XmlMarshaller();

        xmlMarshaller.setCheckForXmlRootElement(true);
        xmlMarshaller.setPackagesToScan("es.securitasdirect.tareas.model");


        Map<String,Object> marshallerProperties = new HashMap<String,Object>();
        marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //Eliminar linea inicial del XML
        //marshallerProperties.put(Marshaller.JAXB_FRAGMENT, true);
        //Configurado como ISO-8859-1 porque el Applet de firma da error al firmar UTF-8 con acentos
        marshallerProperties.put(Marshaller.JAXB_ENCODING,"ISO-8859-1");
        xmlMarshaller.setMarshallerProperties(marshallerProperties);

        return xmlMarshaller;
    }
}
