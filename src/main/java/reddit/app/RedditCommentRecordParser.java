package reddit.app;

import org.apache.hadoop.io.Text;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class RedditCommentRecordParser {
  private RedditCommentRecord redditCommentRecord = new RedditCommentRecord();

  public Boolean parse(String record) throws JSONException {
    JSONObject obj = new JSONObject(record);

    redditCommentRecord.clear();

    try {
      redditCommentRecord.setRecordId(parseRecordId(obj));
      redditCommentRecord.setThreadId(parseThreadId(obj));
      redditCommentRecord.setParentId(parseParentId(obj));
      redditCommentRecord.setSubredditId(parseSubredditId(obj));
      redditCommentRecord.setSubreddit(parseSubreddit(obj));
      redditCommentRecord.setBody(parseBody(obj));
      redditCommentRecord.setAuthor(parseAuthor(obj));
      redditCommentRecord.setCreationTime(parseCreationTime(obj));
      redditCommentRecord.setDowns(parseDowns(obj));
      redditCommentRecord.setUps(parseUps(obj));
      redditCommentRecord.setScore(parseScore(obj));
      redditCommentRecord.setGild(parseGild(obj));
    } catch (JSONException e) {
      // Do nothing
    }

    return redditCommentRecord.isFilled();
  }

  public Boolean parse(Text record)  throws JSONException {
    return parse(record.toString());
  }

  public RedditCommentRecord getRecord() {
    return redditCommentRecord;
  }

  private String parseRecordId(JSONObject obj) throws JSONException {
    return parseStringAlternative2(obj, "id", "recordId");
  }

  private String parseThreadId(JSONObject obj) throws JSONException {
    return parseStringAlternative2(obj, "link_id", "threadId");
  }

  private String parseParentId(JSONObject obj) throws JSONException {
    return parseStringAlternative2(obj, "parent_id", "parentId");
  }

  private String parseSubredditId(JSONObject obj) throws JSONException {
    return parseStringAlternative2(obj, "subreddit_id", "subredditId");
  }

  private String parseSubreddit(JSONObject obj) throws JSONException {
    return parseStringAlternative2(obj, "subreddit", "subreddit");
  }

  private String parseBody(JSONObject obj) throws JSONException {
    return parseStringAlternative2(obj, "body", "body");
  }

  private String parseAuthor(JSONObject obj) throws JSONException {
    return parseStringAlternative2(obj, "author", "author");
  }

  private Integer parseCreationTime(JSONObject obj) throws JSONException {
    return Integer.parseInt(parseStringAlternative2(obj, "created_utc", "creationTime"));
  }

  private Integer parseDowns(JSONObject obj) throws JSONException {
    return parseIntegerAlternative2(obj, "downs", "downs");
  }

  private Integer parseUps(JSONObject obj) throws JSONException {
    return parseIntegerAlternative2(obj, "ups", "ups");
  }

  private Integer parseScore(JSONObject obj) throws JSONException {
    return parseIntegerAlternative2(obj, "score", "score");
  }

  private Integer parseGild(JSONObject obj) throws JSONException {
    return parseIntegerAlternative2(obj, "gilded", "gild");
  }

  private String parseStringAlternative2(JSONObject obj,
                                         String alternative1,
                                         String alternative2)
    throws JSONException {
    String result = RedditCommentRecord.INVALID_STRING_VALUE;

    try {
      result = obj.getString(alternative1);
    } catch (JSONException _) {
      try {
        result = obj.getString(alternative2);
      } catch (JSONException e) {
        // Do nothing
      }
    }

    return result;
  }

  private Integer parseIntegerAlternative2(JSONObject obj,
                                           String alternative1,
                                           String alternative2)
    throws JSONException {
    Integer result = RedditCommentRecord.INVALID_INTEGER_VALUE;

    try {
      result = obj.getInt(alternative1);
    } catch (JSONException e1) {
      try {
        result = obj.getInt(alternative2);
      } catch (JSONException e2) {
        // Do nothing
      }
    }

    return result;
  }
}
