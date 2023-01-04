import logging
from A import SomeClass

# logging.basicConfig(level=logging.DEBUG)
# some_logger = logging.getLogger('some-logger')
#
# logging.debug('debugging')
# some_logger.debug('debugging via some_logger')

sc = SomeClass()
sc.some_property = 'elephant'
assert sc.some_property == 'elephant'

# from Color import Color
# c = Color("bright red")
# print(c.name)
# c.name = "red"
# print(c.name)
