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
    authorId = scrapy.Field()
    author = scrapy.Field()
    subredditId = scrapy.Field()
    subreddit = scrapy.Field()
    title = scrapy.Field()
    body = scrapy.Field()
    domain = scrapy.Field()
    url = scrapy.Field()
    createdDate = scrapy.Field()
    retrievedDate = scrapy.Field()
