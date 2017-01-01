# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html
from scrapy.exceptions import DropItem

from redditscraper.items import RedditThreadScraperItem
from redditscraper.validators import RedditHTMLThreadRecordValidator
from redditscraper.exporters import JsonItemExporter

class RedditThreadPrintItemPipeline(object):
    def open_spider(self, spider):
        self.exporter = JsonItemExporter()
        self.exporter.start_exporting()

    def close_spider(self, spider):
        self.exporter.finish_exporting()

    def process_item(self, item, spider):
        self.exporter.export_item(item)
        return item

class RedditThreadValidateItemPipeline(object):
    def process_item(self, item, spider):
        if self.valid_item(item):
            return item
        raise DropItem(item)

    def valid_item(self, item):
        if isinstance(item, RedditThreadScraperItem):
            return self.valid_thread_item(item)

        return False

    def valid_thread_item(self, item):
        if not hasattr(self, "thread_validator"):
            self.thread_validator = RedditHTMLThreadRecordValidator()

        return self.thread_validator.validate(item)
