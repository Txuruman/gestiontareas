package es.securitasdirect.tareas.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by jel on 24/06/2015.
 */
@XmlRootElement
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TestModel1 {
    String nombre;
    String apellido;
    String edad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "TestModel1{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad='" + edad + '\'' +
                '}';
    }
}
