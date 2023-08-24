import yaml
from jinja2 import Environment, FileSystemLoader
from pathlib import Path

tdir = Path('.')
template = Environment(loader=FileSystemLoader(tdir)).get_template('experimental-jinja-template.yaml.j2')
yaml_data = """
config:
  color: blue
  size: 1
  enabled: true
  samples:
    - name: venice
      neighborhoods:
        cannaregio: 1
        castello: 2
        giudecca: 3
    - name: genoa
      neighborhoods:
        molo: 1
        foce: 2
        marassi: 3
    - name: florence
      neighborhoods:
        santamaria: 1
        sanmarco: 2
        santacroce: 3
        no-such-neighborhood:
          - a1: v1
            a2: v2
          - b
          - c
"""
m = yaml.safe_load(yaml_data)
rendered_text = template.render(m=m)
print(rendered_text)

m2 = yaml.safe_load(rendered_text)
assert m == m2





