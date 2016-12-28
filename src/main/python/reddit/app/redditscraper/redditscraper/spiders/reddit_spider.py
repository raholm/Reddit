import scrapy

from ..items import RedditThreadScraperItem

class RedditSpider(scrapy.Spider):
    name = "reddit"
    allowed_domains = ["reddit.com"]


class RedditThreadSpider(RedditSpider):
    start_urls = ["https://www.reddit.com/r/AskReddit/comments/5kpmxs/"]

    def parse(self, response):
        self.logger.info('Current response is from %s.' % (response.url,))

        item = RedditThreadScraperItem()
        item["title"] = self.get_thread_title(response)

        return item

    def get_title(self, response):
        thread_title_xpath = '//p[@class="title"]/a/text()'
        try:
            thread_title = response.xpath(thread_title_xpath).extract()[0].encode("utf-8")
        except IndexError:
            log_helper(msg="Could not extract thread title.",
                       logger=self.spider_logger, level="warn")
            thread_title = ""

        return thread_title

