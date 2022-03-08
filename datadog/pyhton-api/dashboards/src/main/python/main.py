from datadog_api_client.v1 import ApiClient, Configuration
from datadog_api_client.v1.api.dashboards_api import DashboardsApi

configuration = Configuration()
#
# Get all dashboards
#
with ApiClient(configuration) as api_client:
    api_instance = DashboardsApi(api_client)

    #
    # all dashboards
    #
    dashboard_summaries = api_instance.list_dashboards(filter_shared=False)
    m = dashboard_summaries.to_dict()
    l = m.get('dashboards')
    did = None
    for d in l:
        if did is None:
            did = d['id']
        print(d['title'] + ' (ID ' + d['id'] + ')')

    #
    # one dashboard
    #
    d = api_instance.get_dashboard(dashboard_id=did)
    m = d.to_dict()
    print(m)
#
# Get one dashboard
#
