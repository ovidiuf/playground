class MyClass():
    def __init__(self, color):
        self.color = color


    def confess(self):
        print(f'i am a {self.color} MyClass')

class MySubClass(MyClass):

    def confess(self):
        print(f'i am a {self.color} MySubClass')


c = MySubClass('red')


c.confess()