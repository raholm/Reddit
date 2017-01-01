#!/home/bigdata/.virtualenvs/scrapy3/bin/python3.5

from collections import defaultdict

import sys
import json
import time
import zipimport

importer = zipimport.zipimporter('redditscraper.zip')
redditscraper = importer.load_module('redditscraper')

from redditscraper.runners import RedditThreadSpiderRunner

subreddit_thread_ids = defaultdict(list)
threads_to_fetch = 0
max_thread_fetch_size = 10000

for line in sys.stdin:
    try:
        record = line.split(";")[1]
        obj = json.loads(record)
        subreddit_thread_ids[obj["subreddit"]].append(obj["threadId"][3:])
    except (KeyError, ValueError):
        continue

    threads_to_fetch += 1

    if threads_to_fetch >= max_thread_fetch_size:
        RedditThreadSpiderRunner(subreddit_thread_ids).run()
        threads_to_fetch = 0
        subreddit_thread_ids = defaultdict(list)

if threads_to_fetch > 0:
    RedditThreadSpiderRunner(subreddit_thread_ids).run()
