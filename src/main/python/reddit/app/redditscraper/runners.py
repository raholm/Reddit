from scrapy.crawler import CrawlerRunner
from scrapy.utils.log import configure_logging
from twisted.internet import reactor

from redditscraper.spiders import RedditThreadSpider

class RedditSpiderRunner(object):
    """
    Base class for running a Reddit spider.
    """
    def run(self):
        raise NotImplemented

class RedditThreadSpiderRunner(object):
    """
    Used for fetching thread information by calling the .run() method.

    Parameters
    -----------
    subreddit_thread_ids: dict of list
    A dictionary where the key is the subreddit and value is a list of thread ids to fetch data from.
    Format: {"subreddit1": ["thread_id1", "thread_id2", ...], "subreddit2": [...], ...}
    """

    def __init__(self, subreddit_thread_ids):
        self.subreddit_thread_ids = subreddit_thread_ids

    def run(self):
        configure_logging({"LOG_LEVEL": "ERROR"})
        runner = CrawlerRunner()
        d = runner.crawl(RedditThreadSpider, **{"subreddit_thread_ids": self.subreddit_thread_ids})
        d.addBoth(lambda _: reactor.stop())
        reactor.run()  # the script will block here until the crawling is finished
