#!/bin/bash

awslocal sqs create-queue --queue-name qa.fifo --region ap-northeast-1 --attributes FifoQueue=true
