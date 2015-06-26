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
 * <p>Clase Java para EVENTType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="EVENTType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ArmType" type="{}ArmTypeType"/&gt;
 *         &lt;element name="ArmForced" type="{}ArmForcedType"/&gt;
 *         &lt;element name="Zone" type="{}ZoneType"/&gt;
 *         &lt;element name="CancelPending" type="{}CancelPendingType"/&gt;
 *         &lt;element name="PanelInFault" type="{}PanelInFaultType"/&gt;
 *         &lt;element name="EventType" type="{}EventTypeType"/&gt;
 *         &lt;element name="DevIdentification" type="{}DevIdentificationType"/&gt;
 *         &lt;element name="EventStatus" type="{}EventStatusType"/&gt;
 *         &lt;element name="ArmTime" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DevManufacturer" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVENTType", propOrder = {
    "type",
    "armType",
    "armForced",
    "zone",
    "cancelPending",
    "panelInFault",
    "eventType",
    "devIdentification",
    "eventStatus",
    "armTime",
    "devManufacturer"
})
public class EVENTType {

    protected int type;
    @XmlElement(name = "ArmType", required = true)
    protected ArmTypeType armType;
    @XmlElement(name = "ArmForced", required = true)
    protected ArmForcedType armForced;
    @XmlElement(name = "Zone", required = true)
    protected ZoneType zone;
    @XmlElement(name = "CancelPending", required = true)
    protected CancelPendingType cancelPending;
    @XmlElement(name = "PanelInFault", required = true)
    protected PanelInFaultType panelInFault;
    @XmlElement(name = "EventType", required = true)
    protected EventTypeType eventType;
    @XmlElement(name = "DevIdentification", required = true)
    protected DevIdentificationType devIdentification;
    @XmlElement(name = "EventStatus", required = true)
    protected EventStatusType eventStatus;
    @XmlElement(name = "ArmTime")
    protected int armTime;
    @XmlElement(name = "DevManufacturer", required = true)
    protected String devManufacturer;
    @XmlAttribute(name = "id")
    protected String id;

    /**
     * Obtiene el valor de la propiedad type.
     * 
     */
    public int getType() {
        return type;
    }

    /**
     * Define el valor de la propiedad type.
     * 
     */
    public void setType(int value) {
        this.type = value;
    }

    /**
     * Obtiene el valor de la propiedad armType.
     * 
     * @return
     *     possible object is
     *     {@link ArmTypeType }
     *     
     */
    public ArmTypeType getArmType() {
        return armType;
    }

    /**
     * Define el valor de la propiedad armType.
     * 
     * @param value
     *     allowed object is
     *     {@link ArmTypeType }
     *     
     */
    public void setArmType(ArmTypeType value) {
        this.armType = value;
    }

    /**
     * Obtiene el valor de la propiedad armForced.
     * 
     * @return
     *     possible object is
     *     {@link ArmForcedType }
     *     
     */
    public ArmForcedType getArmForced() {
        return armForced;
    }

    /**
     * Define el valor de la propiedad armForced.
     * 
     * @param value
     *     allowed object is
     *     {@link ArmForcedType }
     *     
     */
    public void setArmForced(ArmForcedType value) {
        this.armForced = value;
    }

    /**
     * Obtiene el valor de la propiedad zone.
     * 
     * @return
     *     possible object is
     *     {@link ZoneType }
     *     
     */
    public ZoneType getZone() {
        return zone;
    }

    /**
     * Define el valor de la propiedad zone.
     * 
     * @param value
     *     allowed object is
     *     {@link ZoneType }
     *     
     */
    public void setZone(ZoneType value) {
        this.zone = value;
    }

    /**
     * Obtiene el valor de la propiedad cancelPending.
     * 
     * @return
     *     possible object is
     *     {@link CancelPendingType }
     *     
     */
    public CancelPendingType getCancelPending() {
        return cancelPending;
    }

    /**
     * Define el valor de la propiedad cancelPending.
     * 
     * @param value
     *     allowed object is
     *     {@link CancelPendingType }
     *     
     */
    public void setCancelPending(CancelPendingType value) {
        this.cancelPending = value;
    }

    /**
     * Obtiene el valor de la propiedad panelInFault.
     * 
     * @return
     *     possible object is
     *     {@link PanelInFaultType }
     *     
     */
    public PanelInFaultType getPanelInFault() {
        return panelInFault;
    }

    /**
     * Define el valor de la propiedad panelInFault.
     * 
     * @param value
     *     allowed object is
     *     {@link PanelInFaultType }
     *     
     */
    public void setPanelInFault(PanelInFaultType value) {
        this.panelInFault = value;
    }

    /**
     * Obtiene el valor de la propiedad eventType.
     * 
     * @return
     *     possible object is
     *     {@link EventTypeType }
     *     
     */
    public EventTypeType getEventType() {
        return eventType;
    }

    /**
     * Define el valor de la propiedad eventType.
     * 
     * @param value
     *     allowed object is
     *     {@link EventTypeType }
     *     
     */
    public void setEventType(EventTypeType value) {
        this.eventType = value;
    }

    /**
     * Obtiene el valor de la propiedad devIdentification.
     * 
     * @return
     *     possible object is
     *     {@link DevIdentificationType }
     *     
     */
    public DevIdentificationType getDevIdentification() {
        return devIdentification;
    }

    /**
     * Define el valor de la propiedad devIdentification.
     * 
     * @param value
     *     allowed object is
     *     {@link DevIdentificationType }
     *     
     */
    public void setDevIdentification(DevIdentificationType value) {
        this.devIdentification = value;
    }

    /**
     * Obtiene el valor de la propiedad eventStatus.
     * 
     * @return
     *     possible object is
     *     {@link EventStatusType }
     *     
     */
    public EventStatusType getEventStatus() {
        return eventStatus;
    }

    /**
     * Define el valor de la propiedad eventStatus.
     * 
     * @param value
     *     allowed object is
     *     {@link EventStatusType }
     *     
     */
    public void setEventStatus(EventStatusType value) {
        this.eventStatus = value;
    }

    /**
     * Obtiene el valor de la propiedad armTime.
     * 
     */
    public int getArmTime() {
        return armTime;
    }

    /**
     * Define el valor de la propiedad armTime.
     * 
     */
    public void setArmTime(int value) {
        this.armTime = value;
    }

    /**
     * Obtiene el valor de la propiedad devManufacturer.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDevManufacturer() {
        return devManufacturer;
    }

    /**
     * Define el valor de la propiedad devManufacturer.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDevManufacturer(String value) {
        this.devManufacturer = value;
    }

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
