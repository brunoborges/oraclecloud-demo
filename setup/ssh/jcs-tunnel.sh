#!/bin/sh
#
# Copyright © 2015, 2015, Oracle and/or its affiliates. All rights reserved.
#
DIR="$( cd "$( dirname "$0" )" && pwd )"
ssh opc@129.152.129.211 -L 9001:129.152.129.211:9001 -i $DIR/id_rsa
