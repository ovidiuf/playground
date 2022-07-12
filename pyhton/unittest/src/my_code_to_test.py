def some_function(arg):
    if 'special' == arg:
        raise ValueError('stumbled over the special value')
    return arg.upper()


if __name__ == '__main__':
    print(some_function('a'))


