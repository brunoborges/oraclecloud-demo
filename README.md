JCS Order Entry Sample Demo
============
This is a sample Java EE application that will guide you through the process of taking an on Premise application to the Cloud with ease.

# Running On Premise
Sure, developers often have all pieces their applications require to run on a development environment. In this case, we will be doing both in the same computer. Here is the list of software you must download to run this tutorial, both to have a development enrivonment but also to simulate an On Premise production environment.  To start, let's simulate an On Premise environment with WebLogic as the application server and Oracle DB as the database. . To do this, you will need to download the following:

 * [Oracle VirtualBox 4.3.26](http://www.virtualbox.org)
 * [Oracle DB VirtualBox VM](http://www.oracle.com/technetwork/database/enterprise-edition/databaseappdev-vm-161299.html)
 * [Oracle JDK 7u79](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
 * [Oracle WebLogic 12.1.3 ZIP Update 2](http://www.oracle.com/technetwork/middleware/weblogic/downloads/wls-for-dev-1703574.html)
 * [Oracle Enterprise Pack for Eclipse 12.1.3.5](http://www.oracle.com/technetwork/developer-tools/eclipse/downloads/index.html)
 * [Apache Maven 3](http://maven.apache.org)

Skip those you already have installed and configured. If you already have Oracle DB 12c somewhere, you will be able to use that. For WebLogic, you will be asked to create a new domain during this tutorial.

## Installing On Premise Software

 1. Download and install VirtualBox based on your host operating system.
 2. Import the VM Appliance of Oracle DB you downloaded into VirtualBox. See [docs](https://www.virtualbox.org/manual/ch01.html#ovf) for more information.
 3. Install Oracle JDK in your system and make sure to point environment variable **JAVA\_HOME** to the path of Oracle JDK.
 4. Install OEPE 12c as described in the [docs](http://docs.oracle.com/middleware/oepe12135/oepe/install/toc.htm).
 5. Extract Apache Maven to 

# Moving to the Cloud
In order to run this application on Oracle Public Cloud, you must follow these steps.

# 1. Create DB and JCS PaaS instances on Oracle Public Cloud
When prompted to provide a VM Public Key (to do SSH), use the existing one at 'etc/ssh/id\_rsa.pub'.

 * Create a Database Cloud Service (non-VI) instance - 12c
 * Create a Java Cloud Service (VI) instance - 12c

## Creating a DBCS instance
The Database Cloud Service instance you must create must have the following configuration:

 * Service Level: Oracle Database Cloud Service (important: not Virtual Image)
 * Software Release: Oracle Database 12c Release 1
 * Software Edition: Enterprise Edition

In the Service Details form, provide a name for your DBCS instance. For your conveniency, please use 'OEDEMO'. See list below for other configurations:

 * Instance Name: OEDEMO
 * Compute Shape: OC3 - 1 OCPU
 * VM Public Key: <upload etc/ssh/id_rsa.pub>
 * Administration Password: Welcome1#
 * Confirm Password: Welcome1#

Since this is an instance for demonstration, do not select a Backup Destination. 

 * Backup Destination: None

Leave all other fields unchanged with their default values. Finish the process to start provisioning this instance.

Do not proceed to next step while still provisioning. Wait for it to finish.

## Create a JCS instance
The Java Cloud Service instance you must create must have the following configuration:

 * Service Level: Oracle Java Cloud Service - Virtual Image
 * Software Release: Oracle WebLogic Server 12c (12.1.2.0)
 * Software Edition: Enterprise Edition (important: not with Coherence)

In the Instance Details form, provide a name for your JCS instance. For your conveniency, please use 'OEDEMO'. See list below for other configurations:

 * Instance Name: OEDEMO
 * Compute Shape: OC3 - 1 OCPU
 * VM Public Key: <upload etc/ssh/id_rsa.pub>
 * User Name: weblogic
 * Password: Welcome1#
 * Confirm Password: Welcome1#
 * Database Configuration: select previously created DBCS, say 'OEDEMO'
 * Database Administrator User Name: system
 * Password: Welcome1#
 * Load Balancer: No

Leave all other fields unchanged with their default values. Finish the process to start provisioning this instance.

You can advance to Step 2 below while JCS is provisioning.

# 2. Activate the OE Schema in your newly created Oracle DBCS instance
After your DBCS has been provisioned, go to the instance details and find what is its Public IP address. 
Connect with SSH using the private key 'id_rsa' as follows:
'''
$ ssh oracle@<dbcs-public-ip-address> -i etc/ssh/id_rsa
'''

Call the following SQL\*Plus command to activate the OE Schema in Oracle DB 12c:

'''
$ sqlplus / as sysdba
alter session set container=pdb1;
alter user oe account unlock identified by welcome1;
$ sqlplus oe/welcome1@pdb1
'''

# Copyright
Copyright © 2015, 2015, Oracle and/or its affiliates. All rights reserved.
