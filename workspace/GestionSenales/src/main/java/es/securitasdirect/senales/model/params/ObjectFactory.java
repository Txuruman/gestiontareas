//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2015.06.25 a las 09:11:45 AM CEST 
//


package es.securitasdirect.senales.model.params;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.securitasdirect.senales.model.params package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PARAMS_QNAME = new QName("", "PARAMS");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.securitasdirect.senales.model.params
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PARAMSType }
     * 
     */
    public PARAMSType createPARAMSType() {
        return new PARAMSType();
    }

    /**
     * Create an instance of {@link CIBBType }
     * 
     */
    public CIBBType createCIBBType() {
        return new CIBBType();
    }

    /**
     * Create an instance of {@link PROPSType }
     * 
     */
    public PROPSType createPROPSType() {
        return new PROPSType();
    }

    /**
     * Create an instance of {@link EVENTSType }
     * 
     */
    public EVENTSType createEVENTSType() {
        return new EVENTSType();
    }

    /**
     * Create an instance of {@link EVENTType }
     * 
     */
    public EVENTType createEVENTType() {
        return new EVENTType();
    }

    /**
     * Create an instance of {@link EventStatusType }
     * 
     */
    public EventStatusType createEventStatusType() {
        return new EventStatusType();
    }

    /**
     * Create an instance of {@link DevIdentificationType }
     * 
     */
    public DevIdentificationType createDevIdentificationType() {
        return new DevIdentificationType();
    }

    /**
     * Create an instance of {@link EventTypeType }
     * 
     */
    public EventTypeType createEventTypeType() {
        return new EventTypeType();
    }

    /**
     * Create an instance of {@link PanelInFaultType }
     * 
     */
    public PanelInFaultType createPanelInFaultType() {
        return new PanelInFaultType();
    }

    /**
     * Create an instance of {@link CancelPendingType }
     * 
     */
    public CancelPendingType createCancelPendingType() {
        return new CancelPendingType();
    }

    /**
     * Create an instance of {@link ZoneType }
     * 
     */
    public ZoneType createZoneType() {
        return new ZoneType();
    }

    /**
     * Create an instance of {@link ArmForcedType }
     * 
     */
    public ArmForcedType createArmForcedType() {
        return new ArmForcedType();
    }

    /**
     * Create an instance of {@link ArmTypeType }
     * 
     */
    public ArmTypeType createArmTypeType() {
        return new ArmTypeType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PARAMSType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PARAMS")
    public JAXBElement<PARAMSType> createPARAMS(PARAMSType value) {
        return new JAXBElement<PARAMSType>(_PARAMS_QNAME, PARAMSType.class, null, value);
    }

}
