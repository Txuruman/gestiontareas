package es.securitasdirect.senales.reader;

import es.securitasdirect.senales.model.Message;
import es.securitasdirect.senales.service.GestionSenalesService;
import es.securitasdirect.senales.support.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads and write the message from the backup directory
 */
@Named(value = "fileReader")
@Singleton
public class FileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileReader.class);

    @Autowired
    protected GestionSenalesService gestionSenalesService;
    @Autowired
    protected FileService fileService;


    /**
     * Read the Error files and calls the Message processor.
     * Used by scheduler.
     */
    public void processErrorMessages() {
        for (File temp : fileService.getMessageFiles()) {
            LOGGER.debug("Reprocessing error message file {}", temp.getName());
            //Cargar Mensaje
            Message message = fileService.readMessage(temp);
            //Eliminar fichero, si da error se volverá a crear por el flujo normal
            if (!temp.delete()) {
                LOGGER.error("Can't delete file {}", temp.getAbsolutePath());
            }
            //Enviar el mensaje a procesar
            gestionSenalesService.onMessage(message);

        }
    }


}

