import re

s = "this is a {{color}} car"
print(re.sub(r"{{color}}", 'blue', s))
