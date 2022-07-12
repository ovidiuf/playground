import logging

logging.basicConfig(level=logging.DEBUG)
some_logger = logging.getLogger('some-logger')

logging.debug('debugging')
some_logger.debug('debugging via some_logger')