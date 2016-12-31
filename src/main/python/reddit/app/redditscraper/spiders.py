import scrapy

from redditscraper.items import RedditThreadScraperItem
from redditscraper.parsers import RedditHTMLThreadRecordParser

class RedditSpider(scrapy.Spider):
    name = "reddit"
    allowed_domains = ["reddit.com"]

class RedditThreadSpider(RedditSpider):
    custom_settings = {
        'ITEM_PIPELINES': {
            'redditscraper.pipelines.RedditThreadValidateItemPipeline': 100,
            'redditscraper.pipelines.RedditThreadPrintItemPipeline': 300,
        }
    }

    def __init__(self, subreddit_thread_ids):
        self.parser = RedditHTMLThreadRecordParser()
        self.subreddit_thread_ids = subreddit_thread_ids

    def start_requests(self):
        base_url = "https://www.reddit.com/r/"

        for subreddit in self.subreddit_thread_ids:
            for thread_id in self.subreddit_thread_ids[subreddit]:
                url = base_url + subreddit + "/comments/" + thread_id
                yield scrapy.Request(url, callback=self.parse)

    def parse(self, response):
        self.logger.info('Current response is from %s.' % (response.url,))
        return self.__create_item(response)

    def __create_item(self, response):
        self.parser.parse(response)
        record = self.parser.record

        item = RedditThreadScraperItem()
        item["id"] = record.id
        item["author_id"] = record.author_id
        item["author"] = record.author
        item["subreddit_id"] = record.subreddit_id
        item["subreddit"] = record.subreddit
        item["title"] = record.title
        item["body"] = record.body
        item["domain"] = record.domain
        item["url"] = record.url
        item["creation_date"] = record.creation_date

        return item
