#!/bin/sh
#
# Util script to encrypt a password on a remote JCS WebLogic domain through SSH
# 
# Inform user (usually 'opc'), ipaddress, and absolute path of your private key
#
REMOTE_USER=opc
REMOTE_SERVER=129.152.151.123
PRIVATE_KEY=

ssh $REMOTE_USER@$REMOTE_SERVER -i $PRIVATEKEY "PWD=$1 /bin/bash -s" <<'ENDSSHSESSION'
  sudo su - oracle
  cd ${DOMAIN_HOME}/bin
  source setDomainEnv.sh
  java weblogic.security.Encrypt $PWD 
ENDSSHSESSION
