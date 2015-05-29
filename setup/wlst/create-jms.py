from java.io import FileInputStream

# Variables loaded from properties file
propInputStream = FileInputStream('jcs.properties')
configProps = Properties()
configProps.load(propInputStream)

# Variables
username = configProps.get('username')
password = configProps.get('password')
URL = configProps.get('URL')

#========================
#Connect To Domain
#========================
connect(username,password,URL)
edit()
startEdit()

# -----------------------
# Use the default '<domainName>__server_1' in JCS
# -----------------------
domainName = cmo.getName()
serverName = domainName + '__server_1'
cd('/')
cmo.createJMSServer('DemoJMSServer')

cd('/JMSServers/DemoJMSServer')
set('Targets',jarray.array([ObjectName('com.bea:Name='+serverName+',Type=Server')], ObjectName))

activate()
startEdit()

cd('/')
cmo.createJMSSystemResource('DemoJMSSystemModule')

cd('/JMSSystemResources/DemoJMSSystemModule')
set('Targets',jarray.array([ObjectName('com.bea:Name='+serverName+',Type=Server')], ObjectName))

activate()
startEdit()

cmo.createSubDeployment('DemoSubdeployment')
cd('/JMSSystemResources/DemoJMSSystemModule/SubDeployments/DemoSubdeployment')
set('Targets',jarray.array([ObjectName('com.bea:Name=DemoJMSServer,Type=JMSServer')], ObjectName))

activate()
startEdit()

cd('/JMSSystemResources/DemoJMSSystemModule/JMSResource/DemoJMSSystemModule')
cmo.createConnectionFactory('jms/OEConnectionFactory')

cd('/JMSSystemResources/DemoJMSSystemModule/JMSResource/DemoJMSSystemModule/ConnectionFactories/jms/OEConnectionFactory')
cmo.setJNDIName('jms/OEConnectionFactory')

cd('/JMSSystemResources/DemoJMSSystemModule/JMSResource/DemoJMSSystemModule/ConnectionFactories/jms/OEConnectionFactory/SecurityParams/jms/OEConnectionFactory')
cmo.setAttachJMSXUserId(false)

cd('/JMSSystemResources/DemoJMSSystemModule/JMSResource/DemoJMSSystemModule/ConnectionFactories/jms/OEConnectionFactory/ClientParams/jms/OEConnectionFactory')
cmo.setClientIdPolicy('Restricted')
cmo.setSubscriptionSharingPolicy('Exclusive')
cmo.setMessagesMaximum(10)

cd('/JMSSystemResources/DemoJMSSystemModule/JMSResource/DemoJMSSystemModule/ConnectionFactories/jms/OEConnectionFactory/TransactionParams/jms/OEConnectionFactory')
cmo.setXAConnectionFactoryEnabled(true)

cd('/JMSSystemResources/DemoJMSSystemModule/JMSResource/DemoJMSSystemModule/ConnectionFactories/jms/OEConnectionFactory')
cmo.setDefaultTargetingEnabled(true)

activate()
startEdit()

cd('/JMSSystemResources/DemoJMSSystemModule/JMSResource/DemoJMSSystemModule')
cmo.createQueue('jms/OEProcessOrders')

cd('/JMSSystemResources/DemoJMSSystemModule/JMSResource/DemoJMSSystemModule/Queues/jms/OEProcessOrders')
cmo.setJNDIName('jms/OEProcessOrders')
cmo.setSubDeploymentName('DemoSubdeployment')

cd('/JMSSystemResources/DemoJMSSystemModule/SubDeployments/DemoSubdeployment')
set('Targets',jarray.array([ObjectName('com.bea:Name=DemoJMSServer,Type=JMSServer')], ObjectName))

activate()
exit()
