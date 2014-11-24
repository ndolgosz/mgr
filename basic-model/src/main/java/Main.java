package basic.model;



import java.io.File;
import java.io.IOException;

public class Main {
    private static final String CONFIG_FILE = "config.properties";
    private static final String USER_CONFIG_FILE = "user.properties";

  

    public AnalysisEngineDescription engineDescr;

    private static Configuration readConfig() throws ConfigurationException {
        CompositeConfiguration config = new CompositeConfiguration();
        File userConfig = new File(USER_CONFIG_FILE);
        if (userConfig.exists() && !userConfig.isDirectory()) {
            log.log(Level.INFO, "Adding user configuration");
            config.addConfiguration(new PropertiesConfiguration(
                    USER_CONFIG_FILE));
        }
        config.addConfiguration(new PropertiesConfiguration(CONFIG_FILE));
        if (config.getString("output_dir").isEmpty()) {
            config.setProperty("output_dir", System.getProperty("user.dir")
                    + "/output");
        }
        if (config.containsKey("debug") && config.getBoolean("debug")) {
            System.out.println("Effective configuration:");
            ConfigurationUtils.dump(config, System.out);
            System.out.println();
        }
        return config;
    }
  
    public static void main(String[] args) throws IOException {
        Configuration config = null;
        try {
            config = readConfig();
            org.apache.commons.io.FileUtils.forceMkdir(new File(config
                    .getString("output_dir")));
        } catch (ConfigurationException e) {
            log.log(Level.SEVERE, "Failed to load configuration");
            System.exit(1);
        }

      
    }
}
