# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy

class RedditThreadScraperItem(scrapy.Item):
    """
    Stores information fetched from a Reddit thread.
    """

    id = scrapy.Field()
    author_id = scrapy.Field()
    author = scrapy.Field()
    subreddit_id = scrapy.Field()
    subreddit = scrapy.Field()
    title = scrapy.Field()
    body = scrapy.Field()
    domain = scrapy.Field()
    url = scrapy.Field()
    created_date = scrapy.Field()
    received_date = scrapy.Field()
