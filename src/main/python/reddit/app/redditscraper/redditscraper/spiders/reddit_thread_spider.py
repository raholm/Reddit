import scrapy

from redditscraper.spiders.reddit_spider import RedditSpider
from redditscraper.items import RedditThreadScraperItem


class RedditThreadSpider(RedditSpider):
    start_urls = ["https://www.reddit.com/r/AskReddit/comments/5kpmxs/"]

    ITEM_PIPELINES = {
        'redditscraper.pipelines.RedditThreadValidateItemPipeline': 100,
        'redditscraper.pipelines.RedditThreadPrintItemPipeline': 300,
    }

    def parse(self, response):
        self.logger.info('Current response is from %s.' % (response.url,))

        item = RedditThreadScraperItem()
        item["id"] = self.get_thread_id(response)
        item["title"] = self.get_thread_title(response)

        return item

    def get_thread_id(self, response):
        thread_id = response.url.split("/")[-3]
        return thread_id

    def get_thread_title(self, response):
        thread_title_xpath = '//p[@class="title"]/a/text()'

        try:
            thread_title = response.xpath(thread_title_xpath).extract()[0].encode("utf-8")
        except IndexError:
            log_helper(msg="Could not extract thread title.",
                       logger=self.spider_logger, level="warn")
            thread_title = ""

        return thread_title
