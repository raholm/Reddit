package reddit.app;

import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.Tool;
import org.codehaus.jettison.json.JSONException;

public class RedditThreadTitleScraperDriver
  extends Configured implements Tool {

  public static class RedditThreadTitleScraperMapper
    extends Mapper<Object, Text, Text, Text> {

    private RedditCommentRecordParser parser = new RedditCommentRecordParser();
    private RedditThreadTitleScraper scraper = new RedditThreadTitleScraper();

    @Override
    protected void map(Object key, Text value, Context context)
      throws IOException, InterruptedException {
      // Output key: threadId, value: threadTitle
      try {
        if (parser.parse(value)) {
          RedditCommentRecord record = parser.getRecord();
          // TODO: record.getThreadId() have t3_* prefix.
          if (scraper.scrape(record.getSubreddit(), record.getThreadId())) {
            context.write(new Text(record.getThreadId()), new Text(scraper.getThreadTitle()));
          }
        }
      } catch(JSONException e) {
        // Do nothing
      } catch (Exception e) {
        // Do nothing
      }
    }
  }

  public static class RedditThreadTitleScraperReducer
    extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
      throws IOException, InterruptedException {
      for (Text value : values) {
        context.write(key, value);
      }
    }
  }


  @Override
  public int run(String[] args) throws Exception {
    // TODO Auto-generated method stub
    return 0;
  }
}
