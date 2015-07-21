package es.securitasdirect.senales.support;

import es.securitasdirect.senales.model.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manager to abstract file use of the error messages
 */
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    /**
     * Path to store and read messages
     */
    private String folderPath;
    private String fileHeader;
    private String fileTail;

    private File sourceDir;

    @Autowired
    private XmlMarshaller xmlMarshaller;

    @PostConstruct
    private void init() {
        sourceDir = new File(folderPath);
        if (!sourceDir.exists()) {
            LOGGER.error("The configured folderPath {} not exists", folderPath);
            sourceDir = null;
        } else if (!sourceDir.isDirectory()) {
            LOGGER.error("The configured folderPath {} is not a directory", folderPath);
            sourceDir = null;
        } else if (!sourceDir.canRead()) {
            LOGGER.error("The configured folderPath {} can't be readed", folderPath);
            sourceDir = null;
        }
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public void setFileHeader(String fileHeader) {
        this.fileHeader = fileHeader;
    }

    public void setFileTail(String fileTail) {
        this.fileTail = fileTail;
    }

    protected boolean matchPattern(File file) {
        Pattern p = Pattern.compile(fileHeader + ".*" + fileTail);
        Matcher m = p.matcher(file.getName());
        return m.matches();
    }

    private String getFileName(final Message message) {
        return fileHeader + message.getId().toString() + fileTail;
    }


    /**
     * Writes the message to a file
     */
    public void writeMessage(final Message message) {
        try {
            String outFilePath = folderPath + getFileName(message);
            FileOutputStream fout = new FileOutputStream(outFilePath);
            PrintWriter oos = new PrintWriter(fout);
            oos.write(xmlMarshaller.marshallMessage(message));
            oos.close();
            fout.close();
        } catch (Exception e) {
            LOGGER.error("Error writing message file {}", getFileName(message),e);
        }
    }

    public Message readMessage(File file) {
        Message message = new Message();
        try {
            message = xmlMarshaller.unmarshallMessage(readFile(file));
        } catch (Exception e) {
            LOGGER.error("Error reading message file {}", file.getAbsolutePath(),e);
        }
        return message;
    }

    public List<File> getMessageFiles() {
        List<File> messageFiles = new ArrayList<File>();
        if (sourceDir != null) {
            File[] allFiles = sourceDir.listFiles();
            if (allFiles != null) {
                for (File temp : allFiles) {
                    if (temp.isFile() && matchPattern(temp)) {
                        messageFiles.add(temp);
                    }
                }
            }
        }
        return messageFiles;
    }

    private String readFile(File file) throws IOException {
        InputStream ins = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line).append("\n");
        }
        reader.close();
        return out.toString();
    }
}
