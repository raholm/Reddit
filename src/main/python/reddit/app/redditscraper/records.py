class RedditHTMLRecord(object):
    pass

class RedditHTMLThreadRecord(RedditHTMLRecord):
    def __init__(self):
        self.id = None
        self.author_id = None
        self.author = None
        self.subreddit_id = None
        self.subreddit = None
        self.title = None
        self.body = None
        self.domain = None
        self.url = None
        self.creation_date = None
