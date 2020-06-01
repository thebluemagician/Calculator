package com.nec.iudx.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import com.nec.iudx.vertical.CalculatorVerticle;

/**
 * loading the application.properties key-value pairs for configuration
 * 
 * @author Md.Adil
 *
 */
public class ConfigProperties {

  static Properties prop = new Properties();

  static {
    try {
      URL resrouceFile = CalculatorVerticle.class.getResource("/application.properties");
      InputStream input = resrouceFile.openStream();
      // input = new FileInputStream();
      prop.load(input);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static String getProperties(String key) {
    return prop.getProperty(key);

  }
}
