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
      redditCommentRecord.setRecordId(obj.getString("id"));
      redditCommentRecord.setThreadId(obj.getString("link_id"));
      redditCommentRecord.setParentId(obj.getString("parent_id"));
      redditCommentRecord.setSubredditId(obj.getString("subreddit_id"));
      redditCommentRecord.setSubreddit(obj.getString("subreddit"));
      redditCommentRecord.setBody(obj.getString("body"));
      redditCommentRecord.setAuthor(obj.getString("author"));
      redditCommentRecord.setCreationTime(Integer.parseInt(obj.getString("created_utc")));
      redditCommentRecord.setDowns(obj.getInt("downs"));
      redditCommentRecord.setUps(obj.getInt("ups"));
      redditCommentRecord.setScore(obj.getInt("score"));
      redditCommentRecord.setGild(obj.getInt("gilded"));
    } catch (JSONException e) {
      // e.printStackTrace();
    }

    return redditCommentRecord.isFilled();
  }

  public Boolean parse(Text record)  throws JSONException {
    return parse(record.toString());
  }

  public RedditCommentRecord getRecord() {
    return redditCommentRecord;
  }
}
