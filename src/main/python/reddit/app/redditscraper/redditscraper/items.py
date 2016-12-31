# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy

class RedditThreadScraperItem(scrapy.Item):
    id = scrapy.Field()
    author_id = scrapy.Field()
    author = scrapy.Field()
    subreddit_id = scrapy.Field()
    subreddit = scrapy.Field()
    title = scrapy.Field()
    body = scrapy.Field()
    domain = scrapy.Field()
    url = scrapy.Field()
    creation_date = scrapy.Field()
