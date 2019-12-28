
#https://github.com/docker/for-win/issues/3171#issuecomment-459205576
netsh int ipv4 add excludedportrange protocol=tcp startport=2181 numberofports=1
netsh int ipv4 add excludedportrange protocol=tcp startport=2888 numberofports=1
netsh int ipv4 add excludedportrange protocol=tcp startport=3888 numberofports=1