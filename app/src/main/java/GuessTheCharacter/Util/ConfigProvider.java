package GuessTheCharacter.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProvider {
    
    public String loadApiKey() {
        try (FileInputStream fileIS = new FileInputStream("config.properties")) { 
            Properties properties = new Properties();
            properties.load(fileIS);
            return properties.getProperty("API_KEY");
        } catch (IOException e) { 
            System.err.println("Error in " + this.getClass().toString() + " loading API key from config.properties");
            System.err.println(e.getCause());
            return "";
        } 
    }
    
    /*CREAR ARCHIVO CONFIG (USO EXCLUSIVO DE DEBUG)*/
    public void createConfigProperties(String API_KEY) {
        Properties properties = new Properties();
        properties.setProperty("API_KEY", API_KEY);
        try (FileOutputStream fileOS = new FileOutputStream("config.properties")) { 
            properties.store(fileOS, "API KEY credentials:");
        } catch (IOException e) { 
            System.err.println("Error in " + this.getClass().toString() + " creating config.properties file");
            System.err.println(e.getCause());
        }
    }
}
