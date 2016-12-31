package reddit.app;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Counters.Counter;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.codehaus.jettison.json.JSONException;

public class FilterAndFormatRedditCommentRecordFieldsMapperOld
  extends MapReduceBase implements Mapper<Object, Text, IntWritable, Text> {

  private enum Counters {
    Records
  }

  private RedditCommentRecordParser parser = new RedditCommentRecordParser();

  public void map(Object key, Text value, OutputCollector<IntWritable, Text> output, Reporter reporter)
    throws IOException {
    try {
      if (parser.parse(value.toString())) {
        RedditCommentRecord record = parser.getRecord();
        Counter counter = reporter.getCounter(Counters.Records);
        counter.increment(1);
        output.collect(new IntWritable((int) counter.getValue()),
                       new Text(record.toJSON().toString()));
      }
    } catch (JSONException e) {
      // Do nothing
    }
  }
}
