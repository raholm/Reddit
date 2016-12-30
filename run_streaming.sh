#!/bin/sh

rm -rf ./output/streaming

hadoop jar $HADOOP_PATH/share/hadoop/tools/lib/hadoop-streaming-2.7.3.jar \
       -conf ./conf/hadoop/hadoop-local.xml \
       -libjars ./target/reddit-1.0-SNAPSHOT.jar \
       -input ./input/reddit_comments.json \
       -output ./output/streaming \
       -mapper reddit.app.FilterAndFormatRedditCommentRecordFieldsMapperOld \
       -reducer ./src/main/python/reddit/app/identity_reducer.py
