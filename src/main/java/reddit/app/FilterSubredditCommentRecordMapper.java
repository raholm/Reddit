package reddit.app;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.codehaus.jettison.json.JSONException;

public class FilterSubredditCommentRecordMapper
  extends Mapper<Object, Text, NullWritable, Text> {

  private RedditCommentRecordParser parser = new RedditCommentRecordParser();

  @Override
  protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
    try {
      if (parser.parse(value)) {
        RedditCommentRecord record = parser.getRecord();
        Configuration conf = context.getConfiguration();
        String subreddit = conf.get("subreddit", "").toLowerCase();

        if (!subreddit.equals("") &&
            record.getSubreddit().toLowerCase().equals(subreddit)) {
          context.write(NullWritable.get(), value);
        }
      }
    } catch (JSONException e) {
      // Do nothing
    }
  }
}
