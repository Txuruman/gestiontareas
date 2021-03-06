package es.securitasdirect.senales.support;

import es.securitasdirect.senales.model.Message;
import es.securitasdirect.senales.model.cibb.CIBB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * XML Marshaller para objetos de dominio.
 *
 * http://docs.spring.io/spring-ws/site/reference/html/oxm.html
 */
public class XmlMarshaller extends Jaxb2Marshaller {

    private static final Logger log = LoggerFactory.getLogger(XmlMarshaller.class);

    protected String marshalObject(Object graph) {
        StringWriter out = new StringWriter();
        this.marshal(graph, new StreamResult(out));
        return out.toString();
    }

    protected Object unmarshalObject(String xmlInput) {
        StringReader reader = new StringReader(xmlInput);
        Object o = this.unmarshal(new StreamSource(reader));
        return o;
    }

    protected String marshall(final CIBB params) {
        return marshalObject(params);
    }

    public CIBB unmarshallCIBB(String xmlInput) {
        return (CIBB) unmarshalObject(xmlInput);
    }

    public String marshallMessage(final Message message) {
        return marshalObject(message);
    }

    public Message unmarshallMessage(String xmlInput) {
        return (Message) unmarshalObject(xmlInput);
    }


//
//    public String marshal(final CertificadoAdministrativo graph) {
//        return marshalObject(graph);
//    }
//
//    public String marshal(final SolicitudCertificadoAdministrativo graph) {
//        return marshalObject(graph);
//    }


//    /**
//     * Obtiene la representación en XML de los DatosDinamicosPage, centrado en los valores introducidos por pantalla
//     *
//     * @return
//     */
//    public DatosDinamicosPage unmarshalValores(final String datosDinamicosValores) {
//        try {
//            StringReader reader = new StringReader(datosDinamicosValores);
//            DatosDinamicosPage o = (DatosDinamicosPage) this.unmarshal(new StreamSource(reader));
//            return o;
//        } catch (Exception e) {
//            log.error("Error al generar el XML de DatosDinamicosPage");
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

//    /**
//     * Obtiene la representación en XML de los DatosDinamicosForm, centrado en el formato del formulario dinámico
//     *
//     * @param datosDinamicosForm
//     * @return
//     */
//    public String marshalDatosDinamicosForm_Formulario(final DatosDinamicosForm datosDinamicosForm) {
//        try {
//            //Clone
//            DatosDinamicosForm form = new DatosDinamicosForm(datosDinamicosForm);
//            //Eliminamos todos los valores de todas las páginas
//            if (form.getPaginas() != null) {
//                for (DatosDinamicosPage page : form.getPaginas()) {
//                    if (page.getItems() != null) {
//                        for (DatosDinamicosItem datosDinamicosItem : page.getItems()) {
//                            datosDinamicosItem.setValue(null);
//                            datosDinamicosItem.setValues(null);
//                        }
//                    }
//                }
//            }
//            return marshalObject(form);
//        } catch (Exception e) {
//            log.error("Error al generar el XML de DatosDinamicosForm_Formulario");
//            throw new RuntimeException(e);
//        }
//    }
//
//





}
