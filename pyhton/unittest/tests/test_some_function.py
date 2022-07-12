from unittest import TestCase

from my_code_to_test import some_function


class TestMyCode(TestCase):

    def test_empty(self):
        self.assertFalse(some_function(''))

    def test_lower_case(self):
        self.assertEqual('A', some_function('a'))

    def test_upper_case(self):
        self.assertEqual('A', some_function('A'))

    def test_exception(self):
        with self.assertRaises(ValueError):
            some_function('special')

