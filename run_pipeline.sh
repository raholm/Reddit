#!/bin/sh

mvn clean package

HADOOP_CONFIG=./conf/hadoop/hadoop-local.xml

FILTER_SUBREDDIT_INPUT=./input/reddit_comments.json
FILTER_SUBREDDIT_OUTPUT=./output/subreddit
FILTER_SUBREDDIT_DRIVER=reddit.app.FilterSubredditCommentRecordDriver

FILTER_FIELDS_INPUT=$FILTER_SUBREDDIT_OUTPUT/part-*
FILTER_FIELDS_OUTPUT=./output/fields
FILTER_FIELDS_DRIVER=reddit.app.FilterAndFormatRedditCommentRecordFieldsDriver

STREAMING_INPUT=$FILTER_FIELDS_OUTPUT/part-*
STREAMING_OUTPUT=./output/streaming
STREAMING_JARS=./target/reddit-1.0-SNAPSHOT.jar
STREAMING_FILES="./src/main/python/reddit/app/reddit_thread_scraper_mapper.py,./src/main/python/reddit/app/redditscraper.zip"
STREAMING_MAPPER=reddit_thread_scraper_mapper.py

rm -rf $FILTER_SUBREDDIT_OUTPUT $FILTER_FIELDS_OUTPUT $STREAMING_OUTPUT

hadoop jar ./target/reddit-1.0-SNAPSHOT.jar $FILTER_SUBREDDIT_DRIVER \
       -conf $HADOOP_CONFIG \
       $FILTER_SUBREDDIT_INPUT $FILTER_SUBREDDIT_OUTPUT

hadoop jar ./target/reddit-1.0-SNAPSHOT.jar $FILTER_FIELDS_DRIVER \
       -conf $HADOOP_CONFIG \
       $FILTER_FIELDS_INPUT $FILTER_FIELDS_OUTPUT

hadoop jar $HADOOP_PATH/share/hadoop/tools/lib/hadoop-streaming-2.7.3.jar \
       -conf $HADOOP_CONFIG \
       -libjars $STREAMING_JARS \
       -files $STREAMING_FILES \
       -D mapreduce.job.reduces=0 \
       -input $STREAMING_INPUT \
       -output $STREAMING_OUTPUT \
       -mapper $STREAMING_MAPPER

echo "Output:"
# cat $FILTER_SUBREDDIT_OUTPUT/part-*
# cat $FILTER_FIELDS_OUTPUT/part-*
cat $STREAMING_OUTPUT/part-*
