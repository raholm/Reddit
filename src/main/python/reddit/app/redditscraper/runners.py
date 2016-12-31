from scrapy.crawler import CrawlerRunner
from scrapy.utils.log import configure_logging
from twisted.internet import reactor

from redditscraper.spiders import RedditThreadSpider

class RedditSpiderRunner(object):
    def run(self):
        raise NotImplemented

class RedditThreadSpiderRunner(object):
    def __init__(self, subreddit_thread_ids):
        self.subreddit_thread_ids = subreddit_thread_ids

    def run(self):
        configure_logging({"LOG_LEVEL": "ERROR"})
        runner = CrawlerRunner()
        d = runner.crawl(RedditThreadSpider, **{"subreddit_thread_ids": self.subreddit_thread_ids})
        d.addBoth(lambda _: reactor.stop())
        reactor.run()  # the script will block here until the crawling is finished
