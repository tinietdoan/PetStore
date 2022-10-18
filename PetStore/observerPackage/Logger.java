package observerPackage;
import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class Logger {
    String fileName;

    public Logger() {
        this.fileName = "";
    }

    public void setName(String name) {
        this.fileName = name;
    }

    public void writeLog(String pubString){
        // publish to logger file
        try {   
            File yourFile = new File(fileName);
            yourFile.createNewFile(); // if file already exists will do nothing 
            FileOutputStream fs = new FileOutputStream(yourFile, true);
 
            byte[] array = pubString.getBytes();
 
            // writing the string to the file by writing
            // each character one by one
            // Writes byte to the file
            fs.write(array);
 
            fs.close();
        }
 
        catch (Exception e) {
            e.getStackTrace();
        }
    }
}
