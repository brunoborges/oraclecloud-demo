#!/bin/bash
#
# IMPORTANT! 
# To use this script, make sure that /etc/sudoers in the remote machine
# has option "requiretty" disabled/commented
#
# About this script: util script to run WLST on a JCS server
# For usage instructions, run: $ ./jcs-wlst.sh -h
#
# Author: Bruno Borges <bruno.borges@oracle.com>
# Since : 2015, May
#
usage() {
cat << EOF
Usage: jcs-wlst.sh -s jcs-instance-address -k private-ssh-key -f wlst-file.py
Runs a WLST file on a remote JCS server instance.
 
Parameters:
   -s: the Public IP address of your JCS server instance
   -k: the SSH private key associated to the Public Key in your JCS server
   -f: the WLST script file (.py) to be executed

MIT LICENSE
EOF
exit 0
}

if [ "$#" -eq 0 ]; then usage; fi

# Parameters
while getopts "hs:k:f:" optname; do
  case "$optname" in
    "h")
      usage
      ;;
    "s")
      REMOTE="opc@$OPTARG"
      ;;
    "k")
      PRIVKEY="-i $OPTARG"
      ;;
    "f")
      SCRIPT="$OPTARG"
      ;;
    *)
    # Should not occur
      echo "Unknown error while processing options"
      ;;
  esac
done

# Copy WLST script to remote server
scp $PRIVKEY $SCRIPT $REMOTE:/tmp/$SCRIPT

# Execute WLST script on remote server
ssh $PRIVKEY $REMOTE "WLST=$SCRIPT /bin/bash -s" <<'ENDSSHSESSION'
  echo $WLST > /tmp/wlst_remote__0
  chmod a+r /tmp/wlst_remote__0
  sudo su - oracle
  cd ${DOMAIN_HOME}/bin
  source setDomainEnv.sh
  java weblogic.WLST -skipWLSModuleScanning /tmp/`cat /tmp/wlst_remote__0`
  exit
  rm /tmp/wlst_remote__0
ENDSSHSESSION
