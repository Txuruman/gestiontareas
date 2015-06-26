//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2015.06.25 a las 09:11:45 AM CEST 
//


package es.securitasdirect.senales.model.params;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para PROPSType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="PROPSType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="tfno" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="texto" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="pais" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="host" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="op" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="centro" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Numero" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="user" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="err" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="transId" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="TimeIn" type="{http://www.w3.org/2001/XMLSchema}decimal" /&gt;
 *       &lt;attribute name="RecepName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Medio" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="TansmisionType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="SeviceType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ProtocolType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="InOrOut" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="DestinoType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="OrigenType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ModeloId" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="origen" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Ok" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="ServiceType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PROPSType")
public class PROPSType {

    @XmlAttribute(name = "tfno")
    protected Integer tfno;
    @XmlAttribute(name = "texto")
    protected String texto;
    @XmlAttribute(name = "pais")
    protected String pais;
    @XmlAttribute(name = "host")
    protected String host;
    @XmlAttribute(name = "op")
    protected String op;
    @XmlAttribute(name = "centro")
    protected String centro;
    @XmlAttribute(name = "Numero")
    protected String numero;
    @XmlAttribute(name = "tipo")
    protected String tipo;
    @XmlAttribute(name = "user")
    protected Integer user;
    @XmlAttribute(name = "err")
    protected String err;
    @XmlAttribute(name = "transId")
    protected String transId;
    @XmlAttribute(name = "TimeIn")
    protected BigDecimal timeIn;
    @XmlAttribute(name = "RecepName")
    protected String recepName;
    @XmlAttribute(name = "Medio")
    protected String medio;
    @XmlAttribute(name = "TansmisionType")
    protected String tansmisionType;
    @XmlAttribute(name = "SeviceType")
    protected String seviceType;
    @XmlAttribute(name = "ProtocolType")
    protected String protocolType;
    @XmlAttribute(name = "InOrOut")
    protected String inOrOut;
    @XmlAttribute(name = "DestinoType")
    protected String destinoType;
    @XmlAttribute(name = "OrigenType")
    protected String origenType;
    @XmlAttribute(name = "ModeloId")
    protected String modeloId;
    @XmlAttribute(name = "origen")
    protected String origen;
    @XmlAttribute(name = "Ok")
    protected Boolean ok;
    @XmlAttribute(name = "ServiceType")
    protected String serviceType;

    /**
     * Obtiene el valor de la propiedad tfno.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTfno() {
        return tfno;
    }

    /**
     * Define el valor de la propiedad tfno.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTfno(Integer value) {
        this.tfno = value;
    }

    /**
     * Obtiene el valor de la propiedad texto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Define el valor de la propiedad texto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTexto(String value) {
        this.texto = value;
    }

    /**
     * Obtiene el valor de la propiedad pais.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPais() {
        return pais;
    }

    /**
     * Define el valor de la propiedad pais.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPais(String value) {
        this.pais = value;
    }

    /**
     * Obtiene el valor de la propiedad host.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHost() {
        return host;
    }

    /**
     * Define el valor de la propiedad host.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHost(String value) {
        this.host = value;
    }

    /**
     * Obtiene el valor de la propiedad op.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOp() {
        return op;
    }

    /**
     * Define el valor de la propiedad op.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOp(String value) {
        this.op = value;
    }

    /**
     * Obtiene el valor de la propiedad centro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCentro() {
        return centro;
    }

    /**
     * Define el valor de la propiedad centro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCentro(String value) {
        this.centro = value;
    }

    /**
     * Obtiene el valor de la propiedad numero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Define el valor de la propiedad numero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumero(String value) {
        this.numero = value;
    }

    /**
     * Obtiene el valor de la propiedad tipo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define el valor de la propiedad tipo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

    /**
     * Obtiene el valor de la propiedad user.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUser() {
        return user;
    }

    /**
     * Define el valor de la propiedad user.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUser(Integer value) {
        this.user = value;
    }

    /**
     * Obtiene el valor de la propiedad err.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErr() {
        return err;
    }

    /**
     * Define el valor de la propiedad err.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErr(String value) {
        this.err = value;
    }

    /**
     * Obtiene el valor de la propiedad transId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransId() {
        return transId;
    }

    /**
     * Define el valor de la propiedad transId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransId(String value) {
        this.transId = value;
    }

    /**
     * Obtiene el valor de la propiedad timeIn.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTimeIn() {
        return timeIn;
    }

    /**
     * Define el valor de la propiedad timeIn.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTimeIn(BigDecimal value) {
        this.timeIn = value;
    }

    /**
     * Obtiene el valor de la propiedad recepName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecepName() {
        return recepName;
    }

    /**
     * Define el valor de la propiedad recepName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecepName(String value) {
        this.recepName = value;
    }

    /**
     * Obtiene el valor de la propiedad medio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedio() {
        return medio;
    }

    /**
     * Define el valor de la propiedad medio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedio(String value) {
        this.medio = value;
    }

    /**
     * Obtiene el valor de la propiedad tansmisionType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTansmisionType() {
        return tansmisionType;
    }

    /**
     * Define el valor de la propiedad tansmisionType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTansmisionType(String value) {
        this.tansmisionType = value;
    }

    /**
     * Obtiene el valor de la propiedad seviceType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeviceType() {
        return seviceType;
    }

    /**
     * Define el valor de la propiedad seviceType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeviceType(String value) {
        this.seviceType = value;
    }

    /**
     * Obtiene el valor de la propiedad protocolType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocolType() {
        return protocolType;
    }

    /**
     * Define el valor de la propiedad protocolType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocolType(String value) {
        this.protocolType = value;
    }

    /**
     * Obtiene el valor de la propiedad inOrOut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInOrOut() {
        return inOrOut;
    }

    /**
     * Define el valor de la propiedad inOrOut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInOrOut(String value) {
        this.inOrOut = value;
    }

    /**
     * Obtiene el valor de la propiedad destinoType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinoType() {
        return destinoType;
    }

    /**
     * Define el valor de la propiedad destinoType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinoType(String value) {
        this.destinoType = value;
    }

    /**
     * Obtiene el valor de la propiedad origenType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigenType() {
        return origenType;
    }

    /**
     * Define el valor de la propiedad origenType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigenType(String value) {
        this.origenType = value;
    }

    /**
     * Obtiene el valor de la propiedad modeloId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeloId() {
        return modeloId;
    }

    /**
     * Define el valor de la propiedad modeloId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeloId(String value) {
        this.modeloId = value;
    }

    /**
     * Obtiene el valor de la propiedad origen.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * Define el valor de la propiedad origen.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigen(String value) {
        this.origen = value;
    }

    /**
     * Obtiene el valor de la propiedad ok.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOk() {
        return ok;
    }

    /**
     * Define el valor de la propiedad ok.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOk(Boolean value) {
        this.ok = value;
    }

    /**
     * Obtiene el valor de la propiedad serviceType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * Define el valor de la propiedad serviceType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceType(String value) {
        this.serviceType = value;
    }

    @Override
    public String toString() {
        return "PROPSType{" +
                "tfno=" + tfno +
                ", texto='" + texto + '\'' +
                ", pais='" + pais + '\'' +
                ", host='" + host + '\'' +
                ", op='" + op + '\'' +
                ", centro='" + centro + '\'' +
                ", numero='" + numero + '\'' +
                ", tipo='" + tipo + '\'' +
                ", user=" + user +
                ", err='" + err + '\'' +
                ", transId='" + transId + '\'' +
                ", timeIn=" + timeIn +
                ", recepName='" + recepName + '\'' +
                ", medio='" + medio + '\'' +
                ", tansmisionType='" + tansmisionType + '\'' +
                ", seviceType='" + seviceType + '\'' +
                ", protocolType='" + protocolType + '\'' +
                ", inOrOut='" + inOrOut + '\'' +
                ", destinoType='" + destinoType + '\'' +
                ", origenType='" + origenType + '\'' +
                ", modeloId='" + modeloId + '\'' +
                ", origen='" + origen + '\'' +
                ", ok=" + ok +
                ", serviceType='" + serviceType + '\'' +
                '}';
    }
}
