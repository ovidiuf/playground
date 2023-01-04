# from jinja2 import Environment
#
# rds_create_gslb_record = True
# template_as_text = '''
#   {% if rds_create_gslb_record == false %}
#   rds:create_gslb_record: false
#   {% elif rds_create_gslb_record %}
#   rds:create_gslb_record: {{ rds_create_gslb_record }}
#   gslb:property_owner_group: aiml-di-automation-bot-user
#   gslb:dns_internal_only: true
#   gslb:comment: CNAME
#   {% endif %}
# '''
# rtemplate = Environment().from_string(template_as_text)
# data = rtemplate.render(rds_create_gslb_record=rds_create_gslb_record)
# print(data)
from datetime import datetime


def some_func(par1: str='blue') -> str:
    return par1.upper()


assert some_func() == "BLUE"