# 
# This script must be executed inside the JCS VM
# Edit the username/password/instance values below
#  - username: the weblogic admin username
#  - password: the password of username
#  - instance: the name of your JCS instance
# 
# Run this with jcs-wlst.sh:
# 
# $ ./jcs-wlst.sh -s jcs-ip-address -k privatekey -f start-admin.py
# 
username = 'weblogic'
password = 'Welcome1#'
instance = 'demojcs'

nmConnect(username, password, instance + '-wls-1', '5556', instance + '_domain', '/u01/data/domains/' + instance + '_domain', 'SSL')
nmStart(instance+'__adminserver')
exit()
