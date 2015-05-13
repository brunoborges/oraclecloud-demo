#!/bin/sh
DIR="$( cd "$( dirname "$0" )" && pwd )"
ssh opc@129.152.129.211 -L 9001:129.152.129.211:9001 -i $DIR/id_rsa
