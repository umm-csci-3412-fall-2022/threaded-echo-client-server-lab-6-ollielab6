#!/bin/bash

numCalls=$1
bigFile=$2

for (( i=0; i<$numCalls; i++ ))
do  
	./StressTest.sh $i $bigFile
done

