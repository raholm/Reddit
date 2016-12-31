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

for line in sys.stdin:
    obj = json.loads(line)
    try:
        subreddit_thread_ids[obj["subreddit"]].append(obj["threadId"][3:])
    except KeyError:
        continue

RedditThreadSpiderRunner(subreddit_thread_ids).run()
