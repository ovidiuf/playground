class Color:
    def __init__(self, name):
        self._name = name

    def _get_name(self):
        return self._name

    def _set_name(self, name):
        if not name:
            raise Exception("Invalid Name")
        self._name = name

    name = property(_get_name, _set_name)
