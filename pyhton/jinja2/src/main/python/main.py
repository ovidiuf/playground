from pathlib import Path
from jinja2 import Environment, FileSystemLoader
import yaml


seq = ['A', 'B']

template_dir = Path(Path(__file__).parent, '../jinja2')
text = Environment(loader=FileSystemLoader(template_dir))\
  .get_template('my-template.yaml.j2') \
  .render(
    variable_1='A',
    variable_2='B',
    seq=seq)

#yaml_data = yaml.load(text, Loader=yaml.Loader)
print(text)


