username = 'weblogic'
 
password = 'Welc9me_311'
 
URL='t3s://myjcs1-wls-1:7002'

JDBCURL='jdbc:oracle:thin:@MyDB1:1521/PDB1.HOLZ13JCS739445103.oraclecloud.internal'
 
connect(username,password,URL)
edit()

startEdit()

cd('/')
cmo.createJDBCSystemResource('OE')

cd('/JDBCSystemResources/OE/JDBCResource/OE')
cmo.setName('OE')

cd('/JDBCSystemResources/OE/JDBCResource/OE/JDBCDataSourceParams/OE')
set('JNDINames',jarray.array([String('jdbc/OE')], String))

cd('/JDBCSystemResources/OE/JDBCResource/OE/JDBCDriverParams/OE')
cmo.setUrl(JDBCURL)
cmo.setDriverName('oracle.jdbc.xa.client.OracleXADataSource')
setEncrypted('Password', 'Password_1411491420181', '/home/opc/OrderEntryWLSTScripts/Script1411491379287Config', '/home/opc/OrderEntryWLSTScripts/Script1411491379287Secret')

cd('/JDBCSystemResources/OE/JDBCResource/OE/JDBCConnectionPoolParams/OE')
cmo.setTestTableName('SQL ISVALID\r\n\r\n')

cd('/JDBCSystemResources/OE/JDBCResource/OE/JDBCDriverParams/OE/Properties/OE')
cmo.createProperty('user')

cd('/JDBCSystemResources/OE/JDBCResource/OE/JDBCDriverParams/OE/Properties/OE/Properties/user')
cmo.setValue('oe')

cd('/JDBCSystemResources/OE/JDBCResource/OE/JDBCDataSourceParams/OE')
cmo.setGlobalTransactionsProtocol('TwoPhaseCommit')

cd('/JDBCSystemResources/OE')
set('Targets',jarray.array([ObjectName('com.bea:Name=MyJCS1_adminserver,Type=Server'), ObjectName('com.bea:Name=MyJCS1_d_cluster,Type=Cluster')], ObjectName))


activate()



dumpStack()
exit()
