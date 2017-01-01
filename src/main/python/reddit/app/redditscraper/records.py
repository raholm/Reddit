class RedditHTMLRecord(object):
    """
    Base class for Reddit HTML records.
    """
    pass

class RedditHTMLThreadRecord(RedditHTMLRecord):
    """
    Stores information parsed from HTML fetched from a Reddit thread.
    """

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
        self.created_date = None

    def adjust_for_deleted_fields(self):
        if self.author == "[deleted]":
            self.author_id = "[deleted]"
