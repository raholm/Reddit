package reddit.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.codehaus.jettison.json.JSONException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class RedditCommentRecordParserTest
  extends TestCase
{
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public RedditCommentRecordParserTest(String testName)
  {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite()
  {
    return new TestSuite(RedditCommentRecordParserTest.class);
  }

  /**
   * Rigourous Test :-)
   */
  public void testParsingValidRecord() throws JSONException
  {
    try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/data/test_valid_data_RedditCommentRecordParser.json"))) {
      String line = br.readLine();

      RedditCommentRecordParser parser = new RedditCommentRecordParser();
      assertTrue(parser.parse(line));

      RedditCommentRecord record = parser.getRecord();

      assertTrue(record.isFilled());
      assertTrue(record.getRecordId().equals("testRecordId"));
      assertTrue(record.getThreadId().equals("testThreadId"));
      assertTrue(record.getParentId().equals("testParentId"));
      assertTrue(record.getSubredditId().equals("testSubredditId"));
      assertTrue(record.getSubreddit().equals("testSubreddit"));
      assertTrue(record.getBody().equals("testBody"));
      assertTrue(record.getAuthor().equals("testAuthor"));
      assertTrue(record.getCreatedDate() == 0);
      assertTrue(record.getRetrievedDate() == 1);
      assertTrue(record.getDowns() == 2);
      assertTrue(record.getUps() == 3);
      assertTrue(record.getScore() == 4);
      assertTrue(record.getGild() == 5);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void testParsingInvalidRecord() throws JSONException
  {
    try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/data/test_invalid_data_RedditCommentRecordParser.json"))) {
      String line = br.readLine();

      RedditCommentRecordParser parser = new RedditCommentRecordParser();
      assertFalse(parser.parse(line));

      RedditCommentRecord record = parser.getRecord();
      assertFalse(record.isFilled());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
