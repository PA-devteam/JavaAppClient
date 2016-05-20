package config;

import java.io.*;
import java.util.Properties;

public class ConfigManager {

    private static String propsFileName = "";
    public static Properties props = new Properties();

    public static void load(String pPropFilePath) {
        try (InputStream input = new FileInputStream(pPropFilePath)) {
            // Load properties file
            props.load(input);

            propsFileName = pPropFilePath;

            // Close input stream
            input.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public static boolean save() {
        return save(propsFileName);
    }
    
    public static boolean isEmpty() {
        return props.isEmpty();
    }

    public static boolean save(String pPropFilePath) {
        boolean saved;

        Properties tmp = (Properties) props.clone();
        
        try {
            File f = new File(pPropFilePath);
            OutputStream out = new FileOutputStream( f );
            tmp.store(out,"");
            out.close();
            saved = true;
        }
        catch (Exception e ) {
            saved = false;
            System.err.println("ConfigManager | save | An error has occured while saving properties file " + pPropFilePath);
        }

        return saved;
    }

    public static void setProperty(String pPropKey, String pPropVal) {
        props.setProperty(pPropKey, pPropVal);
    }
    
    public static String getStringProperty(String pProp) {
        return props.getProperty(pProp);
    }

    public static int getIntProperty(String pProp) {
      int res;

      try {
         res = Integer.parseInt(props.getProperty(pProp));
      } catch (NumberFormatException e) {
          res = -1;
      }

      return res;
    }
}
