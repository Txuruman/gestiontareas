//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2015.06.25 a las 09:11:45 AM CEST 
//


package es.securitasdirect.senales.model.params;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para EVENTSType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="EVENTSType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EVENT" type="{}EVENTType"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="MMV" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="Modelo" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Counter" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Ack" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="InsNumber" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="InsNumber_e" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="DataTime" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="TypeProtocol" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVENTSType", propOrder = {
    "event"
})
public class EVENTSType {

    @XmlElement(name = "EVENT", required = true)
    protected EVENTType event;
    @XmlAttribute(name = "MMV")
    protected Integer mmv;
    @XmlAttribute(name = "Modelo")
    protected String modelo;
    @XmlAttribute(name = "Counter")
    protected String counter;
    @XmlAttribute(name = "Ack")
    protected Integer ack;
    @XmlAttribute(name = "InsNumber")
    protected Integer insNumber;
    @XmlAttribute(name = "InsNumber_e")
    protected Integer insNumberE;
    @XmlAttribute(name = "DataTime")
    protected Integer dataTime;
    @XmlAttribute(name = "TypeProtocol")
    protected String typeProtocol;

    /**
     * Obtiene el valor de la propiedad event.
     * 
     * @return
     *     possible object is
     *     {@link EVENTType }
     *     
     */
    public EVENTType getEVENT() {
        return event;
    }

    /**
     * Define el valor de la propiedad event.
     * 
     * @param value
     *     allowed object is
     *     {@link EVENTType }
     *     
     */
    public void setEVENT(EVENTType value) {
        this.event = value;
    }

    /**
     * Obtiene el valor de la propiedad mmv.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMMV() {
        return mmv;
    }

    /**
     * Define el valor de la propiedad mmv.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMMV(Integer value) {
        this.mmv = value;
    }

    /**
     * Obtiene el valor de la propiedad modelo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Define el valor de la propiedad modelo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelo(String value) {
        this.modelo = value;
    }

    /**
     * Obtiene el valor de la propiedad counter.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCounter() {
        return counter;
    }

    /**
     * Define el valor de la propiedad counter.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCounter(String value) {
        this.counter = value;
    }

    /**
     * Obtiene el valor de la propiedad ack.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAck() {
        return ack;
    }

    /**
     * Define el valor de la propiedad ack.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAck(Integer value) {
        this.ack = value;
    }

    /**
     * Obtiene el valor de la propiedad insNumber.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInsNumber() {
        return insNumber;
    }

    /**
     * Define el valor de la propiedad insNumber.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInsNumber(Integer value) {
        this.insNumber = value;
    }

    /**
     * Obtiene el valor de la propiedad insNumberE.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInsNumberE() {
        return insNumberE;
    }

    /**
     * Define el valor de la propiedad insNumberE.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInsNumberE(Integer value) {
        this.insNumberE = value;
    }

    /**
     * Obtiene el valor de la propiedad dataTime.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDataTime() {
        return dataTime;
    }

    /**
     * Define el valor de la propiedad dataTime.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDataTime(Integer value) {
        this.dataTime = value;
    }

    /**
     * Obtiene el valor de la propiedad typeProtocol.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeProtocol() {
        return typeProtocol;
    }

    /**
     * Define el valor de la propiedad typeProtocol.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeProtocol(String value) {
        this.typeProtocol = value;
    }

    @Override
    public String toString() {
        return "EVENTSType{" +
                "event=" + event +
                ", mmv=" + mmv +
                ", modelo='" + modelo + '\'' +
                ", counter='" + counter + '\'' +
                ", ack=" + ack +
                ", insNumber=" + insNumber +
                ", insNumberE=" + insNumberE +
                ", dataTime=" + dataTime +
                ", typeProtocol='" + typeProtocol + '\'' +
                '}';
    }
}
