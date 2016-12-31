#!/usr/local/bin/python3.5

import sys
import json
import time

thread_ids = []

for line in sys.stdin:
    obj = json.loads(line)
    try:
        thread_ids.append(obj["threadId"][3:])
    except KeyError:
        continue

[print(thread_id) for thread_id in thread_ids]
