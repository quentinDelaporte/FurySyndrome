package com.delaporte.furysyndrom.utils;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConfigReader {
    private  Properties prop1;
    private  Properties prop2;
    public ConfigReader(){
        prop1 = new Properties();
        prop2 = new Properties();
        String fileName1 = "../../Assets/Config/parameters.config";
        String fileName2 = "../../Assets/Config/keybind.config";

        try (FileInputStream file = new FileInputStream(fileName1)) {
            prop1.load(file);
        } catch (FileNotFoundException exception) {
        } catch (IOException exception) {
        }
        try (FileInputStream file = new FileInputStream(fileName2)) {
            prop2.load(file);
        } catch (FileNotFoundException exception) {
        } catch (IOException exception) {
        }
    }

    public String getKeyProperties(String prop){
        return prop2.getProperty(prop);
    }
    public String getGeneralProperties(String prop){
        return prop1.getProperty(prop);
    }
}
