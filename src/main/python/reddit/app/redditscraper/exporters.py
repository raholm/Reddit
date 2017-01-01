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

    def export_item(self, item, prefix=None, postfix=None):
        itemdict = dict(self._get_serialized_fields(item))
        string = self.encoder.encode(itemdict)

        if prefix is not None:
            string = prefix + string

        if postfix is not None:
            string = string + postfix

        print(string)
