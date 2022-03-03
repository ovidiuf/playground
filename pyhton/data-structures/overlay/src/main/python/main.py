import yaml


def descend(path, data):
    """
    Gets a dot separated YAML path and a data structure. Attempt to descend the data structure following the keys
    and return the value associated with the leaf or None on any kind of mismatch or missing data.
    :param path: a dot-separated YAML path. Example: 'level_one.level_two.level_three'
    :param data: a recursive map
    :return: the value associated with the leaf, after recursively navigating the data structure, or None
    """
    if not data or not type(data) is dict:
        return None
    toks = path.split('.')
    if len(toks) > 1:
        return descend('.'.join(toks[1:]), data.get(toks[0]))
    else:
        return data.get(path)


def resolve(path, individual_value=None, overlay=None, base=None, default_value=None):
    """
    Gets an (optional) individual value, an (optional) overlay recursive map, an (optional) base recursive map and an
    optional default value and return the value associated with the path's leaf in the following descending order of
    priority (first encountered value wins): individual_value, overlay, base, default_value.
    :param path: a dot-separated YAML path. Example: 'level_one.level_two.level_three'
    :param default_value: an optional individual value.
    :param base: an optional recursive map
    :param overlay: an optional recursive map
    :param individual_value: an optional individual value.
    :return: the first value encountered in this order: individual_value, overlay, base, default_value.
    """
    v = None
    if individual_value is not None:
        v = individual_value
    if not v and overlay:
        v = descend(path, overlay)
    if not v and base:
        v = descend(path, base)
    if not v and default_value is not None:
        v = default_value
    return v


default_value_text = 'black'
base_text = """
level_one:
  level_two:
    level_three: 'blue'
"""
base_structure = yaml.load(base_text, Loader=yaml.Loader)
overlay_text = """
level_one:
  level_two:
    level_three: 'green'
"""
overlay_structure = yaml.load(overlay_text, Loader=yaml.Loader)
individual_value_text = 'white'

print(resolve('level_one.level_two.level_three',
              default_value='D'))
