package reddit.app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RedditConfiguration {
  private static String subreddit = "";
  private static String sslCertificatePath = "";
  private static Boolean loaded = false;

  public static void loadProperties() throws IOException {
    if (!loaded) {
      Properties properties = new Properties();
      String propertiesFileName = "conf/reddit.properties";

      InputStream inputStream = null;

      try {
        inputStream = RedditConfiguration.class.getClassLoader().getResourceAsStream(propertiesFileName);

        if (inputStream != null) {
          properties.load(inputStream);
        } else {
          throw new FileNotFoundException("property file '" + propertiesFileName + "' not found in the classpath");
        }

        subreddit = properties.getProperty("subreddit");
        sslCertificatePath = properties.getProperty("reddit.ssl.certificate.path");
        loaded = true;
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (inputStream != null) {
          inputStream.close();
        }
      }
    }
  }

  /**
   * @return the subreddit
   */
  public static String getSubreddit() {
    return subreddit;
  }

  /**
   * @return the sslCertificatePath
   */
  public static String getSSLCertificatePath() {
    return sslCertificatePath;
  }
}
