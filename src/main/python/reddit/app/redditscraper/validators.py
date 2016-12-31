class RedditHTMLRecordValidator(object):
    """
    Base class for validating Reddit HTML Records.
    """

    def validate(self, record):
        raise NotImplemented

class RedditHTMLThreadRecordValidator(object):
    """
    Validator for Reddit HTML Thread Records.
    Validatation is done by the 'validate(record)' method
    and it returns True if valid, False otherwise.
    """

    def validate(self, record):
        return self.__validate_id(record) and \
            self.__validate_author_id(record) and \
            self.__validate_author(record) and \
            self.__validate_subreddit_id(record) and \
            self.__validate_subreddit(record) and \
            self.__validate_title(record) and \
            self.__validate_body(record) and \
            self.__validate_domain(record) and \
            self.__validate_url(record) and \
            self.__validate_created_date(record)

    def __validate_id(self, record):
        return record["id"] != ""

    def __validate_author_id(self, record):
        return record["author_id"] != ""

    def __validate_author(self, record):
        return record["author"] != ""

    def __validate_subreddit_id(self, record):
        return record["subreddit_id"] != ""

    def __validate_subreddit(self, record):
        return record["subreddit"] != ""

    def __validate_title(self, record):
        return record["title"] != ""

    def __validate_body(self, record):
        return True

    def __validate_domain(self, record):
        return record["domain"] != ""

    def __validate_url(self, record):
        return record["url"] != ""

    def __validate_created_date(self, record):
        return record["created_date"] != -1
