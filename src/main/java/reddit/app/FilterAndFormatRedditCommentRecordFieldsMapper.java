package reddit.app;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.codehaus.jettison.json.JSONException;

public class FilterAndFormatRedditCommentRecordFieldsMapper
  extends Mapper<NullWritable, Text, NullWritable, Text> {

  private RedditCommentRecordParser parser = new RedditCommentRecordParser();

  public void map(NullWritable key, Text value, Context context)
    throws IOException, InterruptedException {
    try {
      if (parser.parse(value)) {
        RedditCommentRecord record = parser.getRecord();
        context.write(NullWritable.get(), new Text(record.toJSON().toString()));
      }
    } catch (JSONException e) {
      // Do nothing
    }
  }
}
