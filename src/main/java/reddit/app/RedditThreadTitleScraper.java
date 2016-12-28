package reddit.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class RedditThreadTitleScraper {
  private String threadTitle = "";
  private final String baseURL = "https://www.reddit.com/r/";

  public Boolean scrape(String subreddit, String threadId) throws Exception {
    String urlString = new String(baseURL + subreddit + "/comments/" + threadId + "/");
    Document doc = null;

    try {
      URL url = new URL(urlString);
      HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
      String content = getHTMLContent(con);
      doc = Jsoup.parse(content);
    } catch (MalformedURLException e) {
      System.err.println("Malformed URL: " + urlString);
    } catch (IOException e) {
      System.err.println("Could not open connection: " + urlString);
      e.printStackTrace();
    }

    threadTitle = getThreadTitle(doc);

    return !threadTitle.equals("");
  }

  private String getHTMLContent(HttpsURLConnection con) throws IOException {
    StringBuilder content = new StringBuilder();

	if (con != null){
      try {
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;

        while ((line = br.readLine()) != null){
          content.append(line);
        }

        br.close();
      } catch (IOException e) {
        throw e;
      }
    }

    return content.toString();
  }

  private String getThreadTitle(Document doc) {
    String threadTitle = "";

    if (doc != null) {
      try {
        threadTitle = doc.select("a[class*=title]").first().text();
      } catch (Exception e) {
        System.err.println("Could not extract title.");
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
    scraper.scrape("AskReddit", "5knhp5");
    System.out.println(scraper.getThreadTitle());
  }
}
