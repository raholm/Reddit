package reddit.app;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.codehaus.jettison.json.JSONException;

public class FilterAndFormatRedditCommentRecordFieldsMapperOld
  extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

  private RedditCommentRecordParser parser = new RedditCommentRecordParser();

  public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter)
    throws IOException {
    try {
      if (parser.parse(value.toString())) {
        RedditCommentRecord record = parser.getRecord();
        output.collect(new Text(record.toJSON().toString()), new Text(""));
      }
    } catch (JSONException e) {
      // Do nothing
    }
  }
}
