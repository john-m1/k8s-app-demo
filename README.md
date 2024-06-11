# Overview
The lab automation project utilizes a RESTfull Application to facilitate test and resource data management.

# API

## System
Application monitor

```
GET /
```

Application monitor that fails if DB connectivity is lost

```
GET /isAlive
```

## Jmeter 

If running a performance test via jmeter, one could stream their result set via the /data/result API to stream and store data into a db for future reports.

```
POST /data/result
{
"server": "test-server1",
"epoch": 1531694299101,
"name": "QryLp HTTP Request",
"elapsed": 823,
"responseCode": 200,
"responseMsg": "OK",
"success": true,
"latency": 1434,
"idle": 744,
"connectTime": 822
}
```


```
GET /stats/jmeter
```

```
GET /stats/jmeter/size
```

```
GET /stats/jmeter/?column=<column name>&server=<server name>
```

truncate DB table to reset data store

```
DELETE /stats/jmeter
```

Post CPU utilization metrics during test run to the DB 

```
POST /data/cpu
{
"server": "pre-server1",
"date": "09/18/2017 13:14:13",
"usr": 0.09,
"sys": 0.08,
"wio": 0.07,
"idle": 0.99
}
```


```
GET /stats/cpu
```

```
GET /stats/cpu/size
```

```
GET /stats/cpu/?column=<column name>&server=<server name>
```

```
DELETE /stats/cpu
```

Post server load averages utilization 

```
POST /data/load
{
"server": "pre-server1",
"date": "09/18/2017 13:14:13",
"one": 0.05,
"five": 0.02,
"fifteen": 0.03
}
```


```
GET /stats/load
```

```
GET /stats/load/size
```

```
GET /stats/load/?column=<column name>&server=<server name>
```

```
DELETE /stats/load
```

Post process metrics 

```
POST /data/process
{
"server": "pre-server1",
"process": "processNum-001",
"date": "09/18/2017 13:14:13",
"cpu": 0.05,
"thread": 12,
"vsz": 32.21,
"rss": 44.32
}
```

```
GET /stats/process
```

```
GET /stats/process/size
```

```
GET /stats/process/?column=<column name>&server=<server name>
```

```
DELETE /stats/process
```

## vSphere
Lab usage - Post vmware LUN usage 


```
POST /data/luns
{
"name": "DEVQA-LUN-01",
"date": "03/05/2019 13:14:13",
"freeSpace": 1024,
"capacity": 2048
}
```

Lab usage - Post ESXi performance stats

```
POST /data/esxi
{
"server": "10.32.96.141",
"date": "03/05/2019 13:14:12",
"memoryUsed": 608,
"memoryCapacity": 1024,
"cpuUsageMhz": 21.2,
"cpuCores": 2,
"cpuFrequency": 2.7
}
```

Lab Usage - Post vm server ownership data 

```
POST /data/server
{
"server": "v911qa-app6",
"ipaddr": "10.32.97.247",
"vm": 1,
"owner": "ury",
"jira": "SSTDEVQALAB-X",
"db": ""
}
```

```
GET /data/server
```

# Project Requirements

``` 
Maven
Java
Docker
Kubernetes
mysql 
```

# Build Instructions

```
mvn clean package
podman build -t k8sdemo:latest .
```

# Deploy Latest to Kubernetes

Deployment tested using minikube on MacOS

```

minikube kubectl -- apply -f mysql-abal-secret.yaml
minikube kubectl -- apply -f mysql-pv.yaml
minikube kubectl -- apply -f mysql-deployment.yaml

- manually configure msyql DB init scripts with either MySQL Workbench or via container
-- launch container shell to run psql
-- eg - minikube kubectl -- exec --stdin --tty mysql-6d7f5d5944-prvxb -- /bin/bash


podman build -t localhost/k8sdemo:latest .
podman save -o k8sdemo.tar localhost/k8sdemo:latest
minikube image load k8sdemo.tar

- Update k8sdemo.yaml deployment to use the latest container
minikube kubectl -- apply -f k8sdemo.yaml


```

# Known Issues and TODOs

* Update user to be non-root user in container
* Update base image to be light weight image such as Alpine
* Update java version
* Update to latest maven
* Automate DB schema initialization
* Deploy mysql pod to support replication
* Deploy both mysql and k8sdemo app to leverage nginx load balancer to support ReplicaSets

