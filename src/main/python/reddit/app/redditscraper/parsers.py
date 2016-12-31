from redditscraper.records import RedditHTMLThreadRecord

class RedditHTMLRecordParser(object):
    """
    Base HTML parser class for Reddit website.
    """

    def parse(self, response):
        raise NotImplemented

class RedditHTMLThreadRecordParser(RedditHTMLRecordParser):
    """
    Parsing Reddit thread information given a response object
    using the 'parse' method.
    The resulting data is stored in 'record' as a RedditHTMLThreadRecord.
    """

    def __init__(self):
        self.record = RedditHTMLThreadRecord()

        self.thread_head_xpath = '//div[@id="siteTable"]'

    def parse(self, response):
        self.record.id = self.__parse_id(response)
        self.record.author_id = self.__parse_author_id(response)
        self.record.author = self.__parse_author(response)
        self.record.subreddit_id = self.__parse_subreddit_id(response)
        self.record.subreddit = self.__parse_subreddit(response)
        self.record.title = self.__parse_title(response)
        self.record.body = self.__parse_body(response)
        self.record.domain = self.__parse_domain(response)
        self.record.url = self.__parse_url(response)
        self.record.created_date = self.__parse_created_date(response)

    def __parse_id(self, response):
        thread_id = response.url.split("/")[-3]
        return thread_id.encode("utf-8")

    def __parse_subreddit_id(self, response):
        thread_subreddit_id_xpath = self.thread_head_xpath + '/div/@data-subreddit-fullname'

        try:
            thread_subreddit_id = response.xpath(thread_subreddit_id_xpath).extract()[0]
        except IndexError:
            thread_subreddit_id = ""

        return thread_subreddit_id.encode("utf-8")

    def __parse_subreddit(self, response):
        thread_subreddit_xpath = self.thread_head_xpath + '/div/@data-subreddit'

        try:
            thread_subreddit = response.xpath(thread_subreddit_xpath).extract()[0]
        except IndexError:
            thread_subreddit = ""

        return thread_subreddit.encode("utf-8")

    def __parse_author_id(self, response):
        thread_author_id_xpath = self.thread_head_xpath + '/div/@data-author-fullname'

        try:
            thread_author_id = response.xpath(thread_author_id_xpath).extract()[0]
        except IndexError:
            thread_author_id = ""

        return thread_author_id.encode("utf-8")

    def __parse_author(self, response):
        thread_author_xpath = self.thread_head_xpath + '/div/@data-author'

        try:
            thread_author = response.xpath(thread_author_xpath).extract()[0]
        except IndexError:
            thread_author = ""

        return thread_author.encode("utf-8")

    def __parse_title(self, response):
        thread_title_xpath = '//p[@class="title"]/a/text()'

        try:
            thread_title = response.xpath(thread_title_xpath).extract()[0]
        except IndexError:
            thread_title = ""

        return thread_title.encode("utf-8")

    def __parse_body(self, response):
        thread_body_xpath = self.thread_head_xpath + '//div[contains(@class, "usertext-body")]/div//text()'

        try:
            thread_body = response.xpath(thread_body_xpath).extract()
            thread_body = ''.join(thread_body)
        except IndexError:
            thread_body = ""

        return thread_body.encode("utf-8")

    def __parse_domain(self, response):
        thread_domain_xpath = self.thread_head_xpath + '/div/@data-domain'

        try:
            thread_domain = response.xpath(thread_domain_xpath).extract()[0]
        except IndexError:
            thread_domain = ""

        return thread_domain.encode("utf-8")

    def __parse_url(self, response):
        thread_url = response.url
        return thread_url.encode("utf-8")

    def __parse_created_date(self, response):
        thread_created_date_xpath = self.thread_head_xpath + '/div/@data-timestamp'

        try:
            thread_created_date = int(response.xpath(thread_created_date_xpath).extract()[0])
        except IndexError:
            thread_created_date = -1

        return thread_created_date
