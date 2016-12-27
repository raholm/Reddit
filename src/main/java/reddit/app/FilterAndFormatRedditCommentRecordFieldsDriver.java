package reddit.app;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.codehaus.jettison.json.JSONException;

public class FilterAndFormatRedditCommentRecordFieldsDriver
  extends Configured implements Tool {

  public static class FilterAndFormatRedditCommentRecordFieldsMapper
    extends Mapper<Object, Text, NullWritable, Text> {

    private RedditCommentRecordParser parser = new RedditCommentRecordParser();

	@Override
	protected void map(Object key, Text value, Context context)
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

  public static class FilterAndFormatRedditCommentRecordFieldsReducer
    extends Reducer<NullWritable, Text, NullWritable, Text> {

    @Override
    protected void reduce(NullWritable key, Iterable<Text> values, Context context)
      throws IOException, InterruptedException {
      for (Text value : values) {
        context.write(NullWritable.get(), value);
      }
    }
  }

  @Override
  public int run(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.printf("Usage: %s [generic options] <input> <output>\n",
                        getClass().getSimpleName());
      ToolRunner.printGenericCommandUsage(System.err);
      return -1;
    }

    Configuration conf = getConf();
    conf.addResource("conf/reddit.xml");

    Job job = Job.getInstance(conf, "Filter And Format Comment Fields");
    job.setJarByClass(FilterAndFormatRedditCommentRecordFieldsDriver.class);

    job.setMapperClass(FilterAndFormatRedditCommentRecordFieldsMapper.class);
    job.setCombinerClass(FilterAndFormatRedditCommentRecordFieldsReducer.class);
    job.setReducerClass(FilterAndFormatRedditCommentRecordFieldsReducer.class);

    job.setOutputKeyClass(NullWritable.class);
    job.setOutputValueClass(Text.class);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    return job.waitForCompletion(true) ? 0 : 1;
  }

  public static void main(String[] args) throws Exception {
    int exitCode = ToolRunner.run(new FilterAndFormatRedditCommentRecordFieldsDriver(), args);
    System.exit(exitCode);
  }
}
