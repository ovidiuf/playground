class Box:
    def __init__(self):
        self.color = None

    def paint(self, value):
        self.color = value


boxes = [Box(), Box(), Box()]

[b.paint('blue') for b in boxes]

for b in boxes:
    assert b.color == 'blue'





