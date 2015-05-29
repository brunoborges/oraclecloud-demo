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

# get Server status
def getMSserverStatus(server):
    try:
       cd('/ServerLifeCycleRuntimes/' +server)
    except:
       print 'oh ohohohoh';
       dumpStack();
    return cmo.getState()

# -----------------------
# Find the AdminServer
# -----------------------
def findAdminServer():
    previousLocation = pwd()
    serverRuntime()
    adminServer = cmo.getName()
    cd(previousLocation)
    return adminServer

adminservername = findAdminServer()

domainConfig()
svrs = cmo.getServers()
domainRuntime()

for server in svrs:
    # Do not start the adminserver, it's already running
    if server.getName() != adminservername:
        # Get state and machine
        serverState = getMSserverStatus(server.getName())
        print server.getName() + " is " + serverState
        # startup if needed
        if (serverState == "SHUTDOWN") or (serverState == "FAILED_NOT_RESTARTABLE"):
            print "Starting " + server.getName();
            start(server.getName(),'Server')
            serverState = getMSserverStatus(server.getName())
            print "Now " + server.getName() + " is " + serverState;

exit()

