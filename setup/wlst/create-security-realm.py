# Copyright © 2015, 2015, Oracle and/or its affiliates. All rights reserved.
from java.io import FileInputStream

# Variables loaded from properties file
propInputStream = FileInputStream('jcs.properties')
configProps = Properties()
configProps.load(propInputStream)

# Variables
username = configProps.get('username')
password = configProps.get('password')
URL = configProps.get('URL')
datasource_name = configProps.get('datasource_name')
realm = configProps.get('realm')

# Connect and Activate Edit Mode
connect(username,password,URL)
edit()
startEdit()

domainName = cmo.getName()
realm_path = '/SecurityConfiguration/'+domainName+'/Realms/'+realm

# Create SQL Authenticator
cd(realm_path)
cmo.createAuthenticationProvider('OEAuthenticator', 'weblogic.security.providers.authentication.SQLAuthenticator')

cd(realm_path+'/AuthenticationProviders/OEAuthenticator')
cmo.setControlFlag('SUFFICIENT')
cmo.setPlaintextPasswordsEnabled(true)
cmo.setPasswordStyle('SALTEDHASHED')

cmo.setSQLListGroups('SELECT SG_NAME FROM SECURITY_GROUPS WHERE SG_NAME like ?')
cmo.setDescriptionsSupported(false)
cmo.setDataSourceName(datasource_name)
cmo.setSQLListMemberGroups('SELECT sg.sg_name FROM Customers c,Security_Groups sg, Customers_Group cg WHERE c.customer_id=cg.csg_cust_id and cg.csg_sg_id=sg.sg_id and c.cust_email=?')
cmo.setSQLUserExists('SELECT CUSTOMERS.CUST_EMAIL FROM  CUSTOMERS where CUSTOMERS.CUST_EMAIL=?')
cmo.setSQLIsMember('SELECT c.cust_email FROM Customers c,Security_Groups sg, Customers_Group cg WHERE c.customer_id=cg.csg_cust_id and cg.csg_sg_id=sg.sg_id AND sg.sg_name=? and c.cust_email=?')
cmo.setSQLGetUsersPassword('SELECT CUSTOMER_PASSWORD.CUSTPWD_PASSWORD FROM CUSTOMER_PASSWORD, CUSTOMERS WHERE CUSTOMERS.CUSTOMER_ID=CUSTOMER_PASSWORD.CUSTPWD_CUSTID and CUSTOMERS.CUST_EMAIL= ?')
cmo.setSQLGroupExists('SELECT SG_NAME FROM SECURITY_GROUPS WHERE SG_NAME = ?')
cmo.setSQLSetUserPassword('UPDATE CUSTOMER_PASSWORD SET CUSTOMER_PASSWORD.CUSTPWD_PASSWORD = ? WHERE CUSTOMER_PASSWORD.CUSTPWD_CUSTID = (SELECT CUSTOMERS.CUSTOMER_ID FROM  CUSTOMERS where CUSTOMERS.CUST_EMAIL=?)')
cmo.setSQLListGroupMembers('SELECT c.cust_email FROM Customers c,Security_Groups sg, Customers_Group cg WHERE c.customer_id=cg.csg_cust_id and cg.csg_sg_id=sg.sg_id AND sg.sg_name=? and c.cust_email like ?')
cmo.setSQLListUsers('SELECT CUSTOMERS.CUST_EMAIL FROM  CUSTOMERS where CUSTOMERS.CUST_EMAIL LIKE ?')
cmo.setSQLRemoveGroupMemberships('')
cmo.setSQLRemoveUser('')
cmo.setSQLAddMemberToGroup('')
cmo.setSQLCreateGroup('')
cmo.setSQLGetGroupDescription('')
cmo.setSQLCreateUser('')
cmo.setSQLRemoveGroup('')
cmo.setSQLRemoveMemberFromGroup('')
cmo.setSQLSetUserDescription('')
cmo.setSQLRemoveGroupMember('')
cmo.setSQLSetGroupDescription('')
cmo.setSQLGetUserDescription('')

activate()
dumpStack()
exit()
