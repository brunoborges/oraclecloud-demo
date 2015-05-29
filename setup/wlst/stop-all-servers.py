from java.io import FileInputStream

# Variables loaded from properties file
propInputStream = FileInputStream('jcs.properties')
configProps = Properties()
configProps.load(propInputStream)

# Variables
username = configProps.get('username')
password = configProps.get('password')
URL = configProps.get('URL')

# -----------------------
# Find the AdminServer
# -----------------------
def findAdminServer():
    previousLocation = pwd()
    serverRuntime()
    adminServer = cmo.getName()
    cd(previousLocation)
    return adminServer

def getMSserverStatus(server):
    try:
       cd('/ServerLifeCycleRuntimes/' +server)
    except:
       dumpStack();
    return cmo.getState()

connect(username, password, URL)
adminservername = findAdminServer()
domainConfig()
allServers = cmo.getServers()
domainRuntime()
for server in allServers:
    if server.getName() != adminservername:
        serverState = getMSserverStatus(server.getName())
        print server.getName() + " is " + serverState
        if serverState != "SHUTDOWN":
            print "Stopping " + server.getName();
            shutdown(server.getName(),'Server','true',1000,force='true', block='true')
            serverState = getMSserverStatus(server.getName())
            print "now " + server.getName() + " is " + serverState;

print 'Stopping AdminServer: '+adminservername;
shutdown(adminservername,'Server','true',1000,force='true', block='true')  
exit()
