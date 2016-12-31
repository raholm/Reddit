package reddit.app;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class RedditCommentRecord {
  private String recordId;
  private String threadId;
  private String parentId;
  private String subredditId;

  private String subreddit;
  private String body;
  private String author;

  private Integer createdDate;
  private Integer retrievedDate;

  private Integer downs;
  private Integer ups;
  private Integer score;
  private Integer gild;

  static public String INVALID_STRING_VALUE = "";
  static public Integer INVALID_INTEGER_VALUE = Integer.MIN_VALUE;

  public RedditCommentRecord() {
    clear();
  }

  public void clear() {
    recordId = INVALID_STRING_VALUE;
    threadId = INVALID_STRING_VALUE;
    parentId = INVALID_STRING_VALUE;
    subredditId = INVALID_STRING_VALUE;
    subreddit = INVALID_STRING_VALUE;
    body = INVALID_STRING_VALUE;
    author = INVALID_STRING_VALUE;
    createdDate = INVALID_INTEGER_VALUE;
    retrievedDate = INVALID_INTEGER_VALUE;
    downs = INVALID_INTEGER_VALUE;
    ups = INVALID_INTEGER_VALUE;
    score = INVALID_INTEGER_VALUE;
    gild = INVALID_INTEGER_VALUE;
  }

  public boolean isFilled() {
    return !recordId.equals(INVALID_STRING_VALUE) &&
      !threadId.equals(INVALID_STRING_VALUE) &&
      !parentId.equals(INVALID_STRING_VALUE) &&
      !subredditId.equals(INVALID_STRING_VALUE) &&
      !subreddit.equals(INVALID_STRING_VALUE) &&
      !body.equals(INVALID_STRING_VALUE) &&
      !author.equals(INVALID_STRING_VALUE) &&
      createdDate != INVALID_INTEGER_VALUE &&
      retrievedDate != INVALID_INTEGER_VALUE &&
      downs != INVALID_INTEGER_VALUE &&
      ups != INVALID_INTEGER_VALUE &&
      score != INVALID_INTEGER_VALUE &&
      gild != INVALID_INTEGER_VALUE;
  }

  public JSONObject toJSON() throws JSONException {
    JSONObject obj = new JSONObject()
      .put("recordId", recordId)
      .put("threadId", threadId)
      .put("parentId", parentId)
      .put("subredditId", subredditId)
      .put("subreddit", subreddit)
      .put("body", body)
      .put("author", author)
      .put("createdDate", createdDate)
      .put("retrievedDate", retrievedDate)
      .put("downs", downs)
      .put("ups", ups)
      .put("score", score)
      .put("gild", gild);
    return obj;
  }

  /**
   * @return the recordId
   */
  public String getRecordId() {
    return recordId;
  }

  /**
   * @param recordId the recordId to set
   */
  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

  /**
   * @return the threadId
   */
  public String getThreadId() {
    return threadId;
  }

  /**
   * @param threadId the threadId to set
   */
  public void setThreadId(String threadId) {
    this.threadId = threadId;
  }

  /**
   * @return the parentId
   */
  public String getParentId() {
    return parentId;
  }

  /**
   * @param parentId the parentId to set
   */
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  /**
   * @return the subredditId
   */
  public String getSubredditId() {
    return subredditId;
  }

  /**
   * @param subredditId the subredditId to set
   */
  public void setSubredditId(String subredditId) {
    this.subredditId = subredditId;
  }

  /**
   * @return the subreddit
   */
  public String getSubreddit() {
    return subreddit;
  }

  /**
   * @param subreddit the subreddit to set
   */
  public void setSubreddit(String subreddit) {
    this.subreddit = subreddit;
  }

  /**
   * @return the body
   */
  public String getBody() {
    return body;
  }

  /**
   * @param body the body to set
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * @return the author
   */
  public String getAuthor() {
    return author;
  }

  /**
   * @param author the author to set
   */
  public void setAuthor(String author) {
    this.author = author;
  }

  /**
   * @return the creationTime
   */
  public Integer getCreatedDate() {
    return createdDate;
  }

  /**
   * @param creationTime the creationTime to set
   */
  public void setCreatedDate(Integer creationTime) {
    this.createdDate = creationTime;
  }

  /**
   * @return the receivedDate
   */
  public Integer getRetrievedDate() {
    return retrievedDate;
  }

  /**
   * @param receivedDate the receivedDate to set
   */
  public void setRetrievedDate(Integer receivedDate) {
    this.retrievedDate = receivedDate;
  }

  /**
   * @return the downs
   */
  public Integer getDowns() {
    return downs;
  }

  /**
   * @param downs the downs to set
   */
  public void setDowns(Integer downs) {
    this.downs = downs;
  }

  /**
   * @return the ups
   */
  public Integer getUps() {
    return ups;
  }

  /**
   * @param ups the ups to set
   */
  public void setUps(Integer ups) {
    this.ups = ups;
  }

  /**
   * @return the score
   */
  public Integer getScore() {
    return score;
  }

  /**
   * @param score the score to set
   */
  public void setScore(Integer score) {
    this.score = score;
  }

  /**
   * @return the gild
   */
  public Integer getGild() {
    return gild;
  }

  /**
   * @param gild the gild to set
   */
  public void setGild(Integer gild) {
    this.gild = gild;
  }
}
