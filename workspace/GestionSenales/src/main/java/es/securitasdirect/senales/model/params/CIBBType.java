//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2015.06.25 a las 09:11:45 AM CEST 
//


package es.securitasdirect.senales.model.params;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CIBBType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CIBBType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EVENTS" type="{}EVENTSType"/&gt;
 *         &lt;element name="PROPS" type="{}PROPSType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CIBBType", propOrder = {
    "events",
    "props"
})
public class CIBBType {

    @XmlElement(name = "EVENTS", required = true)
    protected EVENTSType events;
    @XmlElement(name = "PROPS", required = true)
    protected PROPSType props;

    /**
     * Obtiene el valor de la propiedad events.
     * 
     * @return
     *     possible object is
     *     {@link EVENTSType }
     *     
     */
    public EVENTSType getEVENTS() {
        return events;
    }

    /**
     * Define el valor de la propiedad events.
     * 
     * @param value
     *     allowed object is
     *     {@link EVENTSType }
     *     
     */
    public void setEVENTS(EVENTSType value) {
        this.events = value;
    }

    /**
     * Obtiene el valor de la propiedad props.
     * 
     * @return
     *     possible object is
     *     {@link PROPSType }
     *     
     */
    public PROPSType getPROPS() {
        return props;
    }

    /**
     * Define el valor de la propiedad props.
     * 
     * @param value
     *     allowed object is
     *     {@link PROPSType }
     *     
     */
    public void setPROPS(PROPSType value) {
        this.props = value;
    }

    @Override
    public String toString() {
        return "CIBBType{" +
                "events=" + events +
                ", props=" + props +
                '}';
    }
}
