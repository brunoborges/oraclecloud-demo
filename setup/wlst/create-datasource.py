from java.io import FileInputStream   

# Variables loaded from properties file
propInputStream = FileInputStream('jcs.properties')
configProps = Properties()
configProps.load(propInputStream)    

# Variables
username = configProps.get('username')
password = configProps.get('password')
URL = configProps.get('URL')
datasource_name=configProps.get('datasource_name')
datasource_jndi=configProps.get('datasource_jndi')
datasource_url=configProps.get('datasource_url')
datasource_username=configProps.get('datasource_username')
datasource_password=configProps.get('datasource_password')

#========================
#Connect To Domain
#========================
connect(username,password,URL)
edit()
startEdit()

# -----------------------
# Find the AdminServer
# -----------------------
def findAdminServer():
    previousLocation = pwd()
    serverRuntime()
    adminServer = cmo.getName()
    cd(previousLocation)
    return adminServer

# -----------------------
# Find the First Cluster
# -----------------------
def findCluster():
    previousLocation = pwd()
    serverConfig()
    cluster = None
    clusters = cmo.getClusters()
    if len(clusters) > 0:
        cluster = clusters[0].getName()
    cd(previousLocation)
    return cluster

# Create DataSource jdbc/OE
cd('/')
cmo.createJDBCSystemResource(datasource_name)

cd('/JDBCSystemResources/'+datasource_name+'/JDBCResource/'+datasource_name)
cmo.setName(datasource_name)

cd('/JDBCSystemResources/'+datasource_name+'/JDBCResource/'+datasource_name+'/JDBCDataSourceParams/'+datasource_name)
set('JNDINames',jarray.array([String(datasource_jndi)], String))

cd('/JDBCSystemResources/'+datasource_name+'/JDBCResource/'+datasource_name+'/JDBCDriverParams/'+datasource_name)
cmo.setUrl(datasource_url)
cmo.setDriverName('oracle.jdbc.xa.client.OracleXADataSource')
cmo.setPassword(datasource_password)

cd('/JDBCSystemResources/'+datasource_name+'/JDBCResource/'+datasource_name+'/JDBCConnectionPoolParams/'+datasource_name)
cmo.setTestTableName('SQL ISVALID\r\n\r\n')

cd('/JDBCSystemResources/'+datasource_name+'/JDBCResource/'+datasource_name+'/JDBCDriverParams/'+datasource_name+'/Properties/'+datasource_name)
cmo.createProperty('user')
cd('/JDBCSystemResources/'+datasource_name+'/JDBCResource/'+datasource_name+'/JDBCDriverParams/'+datasource_name+'/Properties/'+datasource_name+'/Properties/user')
cmo.setValue(datasource_username)

cd('/JDBCSystemResources/'+datasource_name+'/JDBCResource/'+datasource_name+'/JDBCDataSourceParams/'+datasource_name)
cmo.setGlobalTransactionsProtocol('TwoPhaseCommit')

adminServer = findAdminServer()
print('AdminServer: '+adminServer)

targets = []
targets.append(ObjectName('com.bea:Name='+adminServer+',Type=Server'))

cluster = findCluster()
# if there is a cluster, it will deploy to the first one found (in JCS, usually a domain has one cluster)
print('Cluster: '+cluster)
if cluster != None:
    targets.append(ObjectName('com.bea:Name='+cluster+',Type=Cluster'))

cd('/JDBCSystemResources/'+datasource_name)
set('Targets', jarray.array(targets, ObjectName))

activate()
dumpStack()
exit()
