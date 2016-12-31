import argparse

from scrapy.crawler import CrawlerRunner
from scrapy.utils.log import configure_logging
from twisted.internet import reactor

from redditscraper.spiders.reddit_thread_spider import RedditThreadSpider

def main():
        parser = argparse.ArgumentParser()
        parser.add_argument("-t", "--test", action="store_true",
                            help="Testing....")
        args = parser.parse_args()

        if args.test:
                print("Testing...")

        run_spider(**{"subreddit_thread_ids": {"tifu": ["5l8oeq"]}})


def run_spider(**kwargs):
        configure_logging({"LOG_LEVEL": "ERROR"})
        runner = CrawlerRunner()
        d = runner.crawl(RedditThreadSpider, **kwargs)
        d.addBoth(lambda _: reactor.stop())
        reactor.run()  # the script will block here until the crawling is finished

if __name__ == "__main__":
        main()
