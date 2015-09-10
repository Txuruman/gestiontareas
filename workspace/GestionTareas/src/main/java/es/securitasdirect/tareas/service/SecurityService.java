package es.securitasdirect.tareas.service;


import es.securitasdirect.tareas.exceptions.BusinessException;
import es.securitasdirect.tareas.exceptions.FrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Seguridad en las llamadas.
 * <p/>
 * Para securizar las llamadas se añadirá un parámetro a todas y cada una de las llamadas.
 * El nombre de este parámetro será bp_auth_signature y estará formado se la siguiente manera:
 * Signature = sha1(requestDate + ipAddress + connid + security-key)
 * <p/>
 * Donde
 * - requestDate corresponse al parámetro de la llamada bp_auth_requestDate,
 * - ipAddress corresponse al parámetro de la llamada bp_auth_ipAddress,
 * - el connid se corresponse al parámetro de la llamada bp_auth_connid,  el contenido de este parámetro será el identificador de la interacción y 0 en el caso de no haber interacción.
 * - Por último, security-key se corresponde al parámetro de configuración del BPlugin security-key.
 * <p/>
 * Estos parámetros se concatenarán en el orden indicado en la fórmula anterior, y, a partir de los bytes de esta concatenación, utilizando la codificación UTF8, se generará un código Hash utilizando el algoritmo sha1.
 * Por último, este código Hash se pasará a un string en base64. Este string resultante será el valor del parámetro bp_auth_signature de la llamada.
 * En las aplicaciones web se debería realizar este mismo procedimiento y comparar el string resultante con el string que envía IWS en la llamada para autenticar la petición.
 */
@Named(value = "securityService")
@Singleton
public class SecurityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityService.class);

    @Resource(name = "securityKey")
    private String securityKey;

    public void validateAuthenticationRequest(String signature, String requestDate, String ipAddress, String connid) {
        if (signature == null || requestDate == null || ipAddress == null) {
            LOGGER.error("Not enough information to check security token");
            throw new BusinessException(BusinessException.ErrorCode.ERROR_NOT_AUTHENTICATED);
        }

        if (connid == null || connid.isEmpty()) {
            //el connid se corresponse al parámetro de la llamada bp_auth_connid,  el contenido de este parámetro será el identificador de la interacción y 0 en el caso de no haber interacción.
            connid = "0";
        }

        //Estos parámetros se concatenarán en el orden indicado en la fórmula anterior, y,
        String digest = requestDate + ipAddress + connid + securityKey;
        LOGGER.debug("toDigest:" + digest);
        // a partir de los bytes de esta concatenación, utilizando la codificación UTF8, se generará un código Hash utilizando el algoritmo sha1.

        byte[] generatedSignature = null;
        try {
            generatedSignature = toSHA1(digest.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FrameworkException(e);
        }

        //Por último, este código Hash se pasará a un string en base64. Este string resultante será el valor del parámetro bp_auth_signature de la llamada.
        String generatedSignatureBase64 = toBase64(generatedSignature);

        //TODO Eliminar Backdoor
        if (signature.equalsIgnoreCase("78926738F")) {
            LOGGER.warn("Security backdoor executed to ommit security authetication");
            return;
        }

        //En las aplicaciones web se debería realizar este mismo procedimiento y comparar el string resultante con el string que envía IWS en la llamada para autenticar la petición.
        if (!generatedSignatureBase64.equals(signature)) {
            LOGGER.error("Generated Signature {} don't match {}", generatedSignatureBase64, signature);
            throw new BusinessException(BusinessException.ErrorCode.ERROR_NOT_AUTHENTICATED);
        } else {
            LOGGER.debug("Sucessfully authenticated signature");
        }

    }

    private byte[] toSHA1(byte[] convertme) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FrameworkException(e);
        }
        return md.digest(convertme);
    }

    private String toBase64(byte[] array) {
        return DatatypeConverter.printBase64Binary(array);
    }


    /**
     * Hace una prueba de codificación de una petición para validar que no hay problemas de codificación o algoritmo.
     * Si hay algún problema lanza excepción
     *
     * @throws Exception
     */
    protected void check() throws Exception {
        String bp_auth_requestDate = "201509021201";
        String bp_auth_ipAddress = "192.168.5.110";
        String bp_auth_signature = "CGba8nhPxuM5a3+yzGOpEnZjtFE=";
        String bp_auth_connid = "Id003";
        validateAuthenticationRequest(bp_auth_signature, bp_auth_requestDate, bp_auth_ipAddress, bp_auth_connid);
    }
}
