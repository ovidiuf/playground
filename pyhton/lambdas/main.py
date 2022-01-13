def regular_function(s):
    return s.capitalize()


def user_of_function(words, f):
    for w in words:
        print(f(w))


data = ['a', 'b', 'c']
# equivalent behavior:
user_of_function(data, regular_function)
user_of_function(data, lambda w: w.capitalize())
user_of_function(data, lambda w: regular_function(w))
