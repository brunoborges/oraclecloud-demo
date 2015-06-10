# Copyright © 2015, 2015, Oracle and/or its affiliates. All rights reserved.

#
# Starts the AdminServer of a JCS Instance
# 
# Create an SSH Tunnel to port 5556 (Node Manager) of your JCS Instance 

# Edit the username/password/instance values below
#  - username: the weblogic admin username
#  - password: the password of username
#  - instance: the name of your JCS instance
#
# Author: bruno.borges@oracle.com 
#
username = 'weblogic'
password = 'Welcome1#'
instance = 'demojcs'
hostname = 'localhost'
portnumb = '5556'

nmConnect(username, password, hostname, portnumb, instance + '_domain', '/u01/data/domains/' + instance + '_domain', 'SSL')
nmStart(instance+'__adminserver')
exit()
