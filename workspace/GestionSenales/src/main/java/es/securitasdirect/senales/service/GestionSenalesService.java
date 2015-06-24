package es.securitasdirect.senales.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import es.securitasdirect.senales.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

/**
 * Servicio con la lógica principal para la gestión de señales.
 */
@Named(value = "gestionSenales")
@Singleton
public class GestionSenalesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GestionSenalesService.class);

    /**
     * List of processed Messajes to avoid repetition.
     */
    Cache<String, String> processed = CacheBuilder.newBuilder()
            .maximumSize(50)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();


    @PostConstruct
    private void init() {
        LOGGER.info("Initializating...");
    }

    public void onMessage(Message message) {
        LOGGER.debug("Processing Message {}", message);
        processMessage(message);
    }

    /**
     * Main proces of the message.
     * @param message
     * @throws Exception
     */
    private void processMessage(Message message)  {
        if (isDuplicated(message)) {
            LOGGER.debug("Discarting duplicated message {}", message);
        } else {
            try {
                //TODO Procesarlo

            } catch (Exception e) {
                LOGGER.error("Error processing message {}",message,e);
                onError(message);
            }
            markProcessed(message);
        }

    }

    /**
     * Check if a message has been processed
     * @param message
     * @return
     */
    private boolean isDuplicated (Message message) {
        return processed.getIfPresent(message.getId())!=null;
    }

    private void markProcessed(Message message) {
        processed.put(message.getId(),message.getId());//TODO Guardar algo más interesante
    }

    /**
     * Store the message to be processed later, used when there are errors
     */
    private void onError(Message message) {
        LOGGER.error("Storing Message on error to try later {}", message);

    }
}
