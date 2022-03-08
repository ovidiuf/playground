import re

new_id = 'kiki'
s = '    # EKS: og60-spark-eks-03, Namespace: spark-applications\n    id: c0303\n    eksCluster: og60-spark-eks-03\n'
#s = re.sub(r'^( *id: *).*$', '\\1' + new_id, s)
s = re.sub(r'( *id: *)[^\n]*(\n)', '\\1' + new_id + '\\2', s)
print(s)




