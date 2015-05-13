#!/bin/sh
#
# Util script to encrypt a password on a remote WebLogic domain through SSH
# 
# Inform user (usually 'opc'), ipaddress, and domain location of remote WebLogic server
#
REMOTE_USER=opc
REMOTE_SERVER=129.152.129.211
REMOTE_DOMAIN_HOME=/home/bruno/Work/tools/oracle/mw1212/user_projects/domains/base_domain

DIR="$( cd "$( dirname "$0" )" && pwd )"
PRIVATE_KEYFILE=$DIR/id_rsa

ssh $REMOTE_USER@$REMOTE_SERVER -i $PRIVATE_KEYFILE DOMAIN_HOME=$REMOTE_DOMAIN_HOME PLAINTEXT=$1 '/bin/bash -s' <<'ENDSSHSESSION'
  cd $DOMAIN_HOME/bin
  source setDomainEnv.sh
  java weblogic.security.Encrypt $PLAINTEXT
ENDSSHSESSION
