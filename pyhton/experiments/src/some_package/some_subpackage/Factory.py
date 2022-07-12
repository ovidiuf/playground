import sys
import inspect
from pathlib import Path


def extract_module_name_to_delete(absolute_file_path, parent_package_name):
    """
    Given an absolute module file path, and the dot-separated parent package
    name, extract the module name, amenable to use in an import statement. The
    function will accept module file names that do not have a '.py' extension.
    :return: the module name, ready to be  used in __import__() function
    """
    path = absolute_file_path.split('/')
    module_name = path[-1]
    i = module_name.rfind('.')
    if i != -1:
        module_name = module_name[0:i]
    path = path[0:-1]
    path.reverse()
    package = parent_package_name.split('.')
    package.reverse()
    for i, n in enumerate(package):
        if n != path[i]:
            break
        module_name = n + '.' + module_name
    return module_name


class Factory:
    @staticmethod
    def some_static_method():
        # we operate under the assumption that all classes whose instances
        # can be built by this Factory are declared in modules (files) present
        # in the same directory as the Factory class module (file).
        parent_dir = Path(__file__).parent
        for f in parent_dir.iterdir():
            if f.name.endswith('py'):
                module_name = extract_module_name_to_delete(str(f), __package__)
                __import__(module_name)
        # identify the suitable modules from the same package
        modules = sys.modules.copy()
        for module_name in modules.keys():
            if module_name.startswith(__package__ + "."):
                module = modules[module_name]
                # lookup the class
                # print(module)

        module = modules['some_package.some_subpackage.SomeClass']
        class_tuples = inspect.getmembers(module, inspect.isclass)
        for ct in class_tuples:
            class_name = ct[0]
            cls = ct[1]
            method_name = 'some_static_method'
            try:
                method = getattr(cls, method_name)
                method('blue')
            except AttributeError:
                print(f"{method_name} method not found in class {cls}")



