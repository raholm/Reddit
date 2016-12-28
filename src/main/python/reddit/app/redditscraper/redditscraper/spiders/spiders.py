import scrapy


class RedditSpider(scrapy.Spider):
    name = "reddit"
    allowed_domains = ["reddit.com"]

