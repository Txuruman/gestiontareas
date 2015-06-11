package es.securitasdirect.tareas.service;

import org.wso2.ws.dataservice.SPAVISOSOPERACIONESPortType;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 *
 */
@Named(value = "avisoService")
@Singleton
public class AvisoService {

    protected SPAVISOSOPERACIONESPortType spAvisosOperaciones;

}
