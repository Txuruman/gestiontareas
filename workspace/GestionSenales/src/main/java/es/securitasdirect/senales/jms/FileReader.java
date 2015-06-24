package es.securitasdirect.senales.jms;

import es.securitasdirect.senales.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.*;

/**
 * Reads and write the message from the backup directory
 */
@Named(value = "fileReader")
@Singleton
public class FileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileReader.class);
    /**
     * Path to store and read messages
     */
    @Inject
    private String folderPath;
    @Inject
    private String fileHeader;
    @Inject
    private String fileTail;

    /**
     * Writes the message to a file
     */
    public void writeMessage(final Message message) {
        try {
            FileOutputStream fout = new FileOutputStream(getFileName(message));
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(message);
            oos.close();
            fout.close();
        } catch (Exception e) {
            LOGGER.error("Error writing message file {}", getFileName(message));
        }
    }

    public Message readMessage(String filePath) {
        Message message = null;
        try {
            FileInputStream fin = new FileInputStream(filePath);
            ObjectInputStream ins = new ObjectInputStream(fin);
            message = (Message) ins.readObject();
            ins.close();
            fin.close();
        } catch (Exception e) {
            LOGGER.error("Error reading message file {}", filePath);
        }
        return message;
    }

    private String getFileName(final Message message) {
        return fileHeader + message.getId().trim() + fileTail;
    }
}
