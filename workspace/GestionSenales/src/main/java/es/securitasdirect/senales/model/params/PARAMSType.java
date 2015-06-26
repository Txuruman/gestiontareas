//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2015.06.25 a las 09:11:45 AM CEST 
//


package es.securitasdirect.senales.model.params;

import javax.xml.bind.annotation.*;


/**
 * <p>Clase Java para PARAMSType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="PARAMSType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CIBB" type="{}CIBBType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "PARAMS")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PARAMSType", propOrder = {
    "cibb"
})
public class PARAMSType {

    @XmlElement(name = "CIBB", required = true)
    protected CIBBType cibb;

    /**
     * Obtiene el valor de la propiedad cibb.
     * 
     * @return
     *     possible object is
     *     {@link CIBBType }
     *     
     */
    public CIBBType getCIBB() {
        return cibb;
    }

    /**
     * Define el valor de la propiedad cibb.
     * 
     * @param value
     *     allowed object is
     *     {@link CIBBType }
     *     
     */
    public void setCIBB(CIBBType value) {
        this.cibb = value;
    }

    @Override
    public String toString() {
        return "PARAMSType{" +
                "cibb=" + cibb +
                '}';
    }
}
