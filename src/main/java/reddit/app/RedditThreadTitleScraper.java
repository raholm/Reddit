package reddit.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class RedditThreadTitleScraper {
  private String threadTitle;
  private String latestURLString;

  private final String baseURL = "https://www.reddit.com/r/";

  public Boolean scrape(String subreddit, String threadId) throws Exception {
    threadTitle = null;

    try {
      URL url = getURL(subreddit, threadId);
      HttpsURLConnection con = getConnection(url);
      String content = getHTMLContent(con);
      Document doc = Jsoup.parse(content);
      threadTitle = getThreadTitle(doc);
    } catch (IOException e) {
      System.err.println("Something went terribly wrong: " + latestURLString);
      e.printStackTrace();
    }

    return threadTitle != null;
  }

  private URL getURL(String subreddit, String threadId) throws MalformedURLException {
    latestURLString = new String(baseURL + subreddit + "/comments/" + threadId + "/");
    URL url = null;

    try {
      url = new URL(latestURLString);
    } catch (MalformedURLException e) {
      System.err.println("Malformed URL: " + latestURLString);
    }

    return url;
  }

  private HttpsURLConnection getConnection(URL url) throws IOException {
    HttpsURLConnection connection = null;

    if (url != null) {
      try {
        connection = (HttpsURLConnection) url.openConnection();
        connection.connect();
      } catch (IOException e) {
        System.err.println("Could not open connection: " + latestURLString);
        throw e;
      }

      Integer responseCode = connection.getResponseCode();

      System.out.println(connection);
      System.out.println(responseCode);

      if (!(responseCode >= 200 && responseCode < 300)) {
        connection.disconnect();
        connection = null;
      }
    }

    return connection;
  }

  private String getHTMLContent(HttpsURLConnection connection) throws IOException {
    String content = "";

	if (connection != null){
      InputStream inputStream = null;

      try {
        inputStream = connection.getInputStream();
        content = IOUtils.toString(inputStream, "UTF-8");
      } catch (IOException e) {
        System.err.println("Could not open input stream: " + latestURLString);
        throw e;
      } finally {
        if (inputStream != null) {
          inputStream.close();
        }

        connection.disconnect();
      }
    }

    return content;
  }

  private String getThreadTitle(Document document) {
    String threadTitle = null;

    if (document != null) {
      try {
        threadTitle = document.select("a[class*=title]").first().text();
      } catch (Exception e) {
        System.err.println("Could not extract title: " + latestURLString);
      }
    }

    return threadTitle;
  }

  /**
   * @return the threadTitle
   */
  public String getThreadTitle() {
    return threadTitle;
  }

  public static void main(String[] args) throws Exception {
    RedditThreadTitleScraper scraper = new RedditThreadTitleScraper();
    scraper.scrape("AskReddit", "5kpmxs");
    System.out.println(scraper.getThreadTitle());
  }
}
