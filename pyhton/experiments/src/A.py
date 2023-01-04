class SomeClass:

    def __init__(self):
        self._internal_state = None

    @property
    def some_property(self):
        return self._internal_state

    @some_property.setter
    def some_property(self, value):
        self._internal_state = value

    @some_property.deleter
    def some_property(self):
       pass




