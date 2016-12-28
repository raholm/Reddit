# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html

from redditscraper.items import RedditThreadScraperItem

class RedditThreadPrintItemPipeline(object):
    def process_item(self, item, spider):
        # print(item)
        # print(item["title"])
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
        return item["id"] != "" and item["title"] != ""
