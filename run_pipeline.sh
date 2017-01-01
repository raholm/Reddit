#!/bin/sh

# Hadoop Settings
HADOOP_CONFIG=./conf/hadoop/hadoop-local.xml

# Filter Subreddit MapReduce Task Settings
FILTER_SUBREDDIT_INPUT=input
FILTER_SUBREDDIT_OUTPUT=output/subreddit
FILTER_SUBREDDIT_DRIVER=reddit.app.FilterSubredditCommentRecordDriver

# Filter Fields MapReduce Task Settings
FILTER_FIELDS_INPUT=$FILTER_SUBREDDIT_OUTPUT
FILTER_FIELDS_OUTPUT=output/fields
FILTER_FIELDS_DRIVER=reddit.app.FilterAndFormatRedditCommentRecordFieldsDriver

# Streaming MapReduce Task Settings
STREAMING_INPUT=$FILTER_FIELDS_OUTPUT
STREAMING_OUTPUT=output/streaming
STREAMING_JARS=target/reddit-1.0-SNAPSHOT.jar
STREAMING_FILES="./src/main/python/reddit/app/reddit_thread_scraper_mapper.py,./src/main/python/reddit/app/redditscraper.zip"
STREAMING_MAPPER=reddit_thread_scraper_mapper.py

# Functions
preprocess () {
    rm -f ./reddit-1.0-SNAPSHOT.jar ./redditscraper.zip ./reddit_thread_scraper_mapper.py
    cd ./src/main/python/reddit/app/
    rm -f redditscraper.zip
    zip -r redditscraper.zip redditscraper/
    cd ../../../../../
    mvn clean package
}

filter_subreddit () {
    hadoop jar ./target/reddit-1.0-SNAPSHOT.jar $FILTER_SUBREDDIT_DRIVER \
           -conf $HADOOP_CONFIG \
           $FILTER_SUBREDDIT_INPUT $FILTER_SUBREDDIT_OUTPUT
}

filter_fields () {
    hadoop jar ./target/reddit-1.0-SNAPSHOT.jar $FILTER_FIELDS_DRIVER \
           -conf $HADOOP_CONFIG \
           $FILTER_FIELDS_INPUT $FILTER_FIELDS_OUTPUT
}

stream () {
    hadoop jar $HADOOP_PATH/share/hadoop/tools/lib/hadoop-streaming-2.7.3.jar \
           -conf $HADOOP_CONFIG \
           -libjars $STREAMING_JARS \
           -files $STREAMING_FILES \
           -D mapreduce.job.reduces=0 \
           -input $STREAMING_INPUT \
           -output $STREAMING_OUTPUT \
           -mapper $STREAMING_MAPPER
}

# Program
TOTAL_START_TIME=$(date +%s)

preprocess

rm -rf $FILTER_SUBREDDIT_OUTPUT $FILTER_FIELDS_OUTPUT $STREAMING_OUTPUT

FILTER_SUBREDDIT_START_TIME=$(date +%s)

filter_subreddit

FILTER_SUBREDDIT_END_TIME=$(date +%s)
FILTER_SUBREDDIT_DIFF_TIME=$((FILTER_SUBREDDIT_END_TIME - FILTER_SUBREDDIT_START_TIME))

FILTER_FIELDS_START_TIME=$(date +%s)

filter_fields

FILTER_FIELDS_END_TIME=$(date +%s)
FILTER_FIELDS_DIFF_TIME=$((FILTER_FIELDS_END_TIME - FILTER_FIELDS_START_TIME))

FILTER_STREAMING_START_TIME=$(date +%s)

# stream

FILTER_STREAMING_END_TIME=$(date +%s)
FILTER_STREAMING_DIFF_TIME=$((FILTER_STREAMING_END_TIME - FILTER_STREAMING_START_TIME))

TOTAL_END_TIME=$(date +%s)
TOTAL_DIFF_TIME=$(($TOTAL_END_TIME - $TOTAL_START_TIME))

echo "Filter subreddit took $FILTER_SUBREDDIT_DIFF_TIME seconds"
echo "Filter fields took $FILTER_FIELDS_DIFF_TIME seconds"
echo "Streaming took $FILTER_STREAMING_DIFF_TIME seconds"
echo "Complete run took $TOTAL_DIFF_TIME seconds"
