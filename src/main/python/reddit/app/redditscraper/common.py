import datetime

def get_utc_timestamp():
    epoch = datetime.datetime(1970,1,1)
    current_time = datetime.datetime.utcnow()
    return int((current_time - epoch).total_seconds()) * 1000
