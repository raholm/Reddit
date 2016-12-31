package reddit.app;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;
import org.codehaus.jettison.json.JSONException;

public class FilterAndFormatRedditCommentRecordFieldsMapper
  extends Mapper<Object, Text, IntWritable, Text> {

  private enum Counters {
    Records
  }

  private RedditCommentRecordParser parser = new RedditCommentRecordParser();

  public void map(Object key, Text value, Context context)
    throws IOException, InterruptedException {
    try {
      if (parser.parse(value)) {
        RedditCommentRecord record = parser.getRecord();
        Counter counter = context.getCounter(Counters.Records);
        counter.increment(1);
        context.write(new IntWritable((int) counter.getValue()),
                      new Text(record.toJSON().toString()));
      }
    } catch (JSONException e) {
      // Do nothing
    }
  }
}
