package es.securitasdirect.senales.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearch {

    private String extensionToSearch;
    private List<String> result = new ArrayList<String>();

    public String getExtensionToSearch() {
        return extensionToSearch;
    }

    public void setExtensionToSearch(String extensionToSearch) {
        this.extensionToSearch = extensionToSearch;
    }

    public List<String> getResult() {
        return result;
    }


    public void searchDirectory(File directory, String extensionToSearch) {

        setExtensionToSearch(extensionToSearch);

        if (directory.isDirectory()) {
            search(directory);
        } else {
            System.out.println(directory.getAbsoluteFile() + " is not a directory!");
        }

    }

    private void search(File file) {

        if (file.isDirectory()) {

            //do you have permission to read this directory?	
            if (file.canRead()) {
                for (File temp : file.listFiles()) {
                    if (temp.isDirectory()) {
                        search(temp);
                    } else {
                        if (temp.getName().toLowerCase().endsWith(getExtensionToSearch())) {
                            result.add(temp.getAbsoluteFile().toString());
                        }

                    }
                }

            } else {
                System.out.println(file.getAbsoluteFile() + "Permission Denied");
            }
        }

    }

}