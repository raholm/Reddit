# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html
from scrapy.exceptions import DropItem

from redditscraper.items import RedditThreadScraperItem
from redditscraper.validators import RedditHTMLThreadRecordValidator
from redditscraper.exporters import JsonItemExporter


class RedditThreadOutputPipeline(object):
    def open_spider(self, spider):
        self.exporter = JsonItemExporter()
        self.exporter.start_exporting()

    def close_spider(self, spider):
        self.exporter.finish_exporting()

    def process_item(self, item, spider):
        prefix = str(spider.crawler.stats.get_value("item_valid_count")) + ";"
        self.exporter.export_item(item, prefix=prefix)
        return item

class RedditPrintItemPipeline(object):
    def process_item(self, item, spider):
        print(item)
        return item

class RedditThreadValidateItemPipeline(object):
    def open_spider(self, spider):
        spider.crawler.stats.set_value("item_valid_count", 0)
        spider.crawler.stats.set_value("item_invalid_count", 0)

    def process_item(self, item, spider):
        if self.valid_item(item):
            spider.crawler.stats.inc_value("item_valid_count")
            return item

        spider.crawler.stats.inc_value("item_invalid_count")
        raise DropItem(item)

    def valid_item(self, item):
        if isinstance(item, RedditThreadScraperItem):
            return self.valid_thread_item(item)

        return False

    def valid_thread_item(self, item):
        if not hasattr(self, "thread_validator"):
            self.thread_validator = RedditHTMLThreadRecordValidator()

        return self.thread_validator.validate(item)
