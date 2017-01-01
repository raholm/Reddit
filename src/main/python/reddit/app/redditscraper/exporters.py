import json

from scrapy.exporters import JsonLinesItemExporter

class JsonItemExporter(JsonLinesItemExporter):
    def __init__(self, **kwargs):
        self._configure(kwargs)
        self.encoder = json.JSONEncoder(**kwargs)

    def start_exporting(self):
        pass

    def finish_exporting(self):
        pass

    def export_item(self, item):
        itemdict = dict(self._get_serialized_fields(item))
        print(self.encoder.encode(itemdict))
