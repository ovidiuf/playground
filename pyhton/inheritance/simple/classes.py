
class SomeClass:
    def __init__(self, color):
        self.color = color

    def dump(self):
        print(f'hello, I am SomeClass {self.color}')


class SomeSubClass(SomeClass):

    def dump(self):
        print(f'hello, I am SomeSubClass {self.color}')


def main():
    c = SomeClass('blue')
    c.dump()
    sc = SomeSubClass('red')
    sc.dump()


if __name__ == '__main__':
    main()

