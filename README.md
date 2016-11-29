# SaaS - AppDirect Service Integration

Application to demonstrate the integration of subscription management features using AppDirect service.

### Event Subscription End Points
1) Create Subscription -  http://host/v1/notifications/CREATE/subscriptions?eventUrl={eventUrl}
2) Change Subscription -  http://host/v1/notifications/CHANGE/subscriptions?eventUrl={eventUrl}
3) Cancel Subscription -  http://host/v1/notifications/CANCEL/subscriptions?eventUrl={eventUrl}
All endpoints are aysnchronoius in nature and returns HTTP 201 response upon successfully reception of events 

### Design Approach
Entire flow in the applciation is following "Eventual conistancey" approach.
Once application receives events, it get queued. Background processor service process every events concurrently and create, update, cancel the subscriptions.
Beauty of this approach is scalability and fault tolerance. Even if system receives higher maginiuted of events application could sustain the performance and relibility.
Application modularised enough to extend it into micro services form.
e.g.Subscription module, User modules

### Configuration
```sh
logging.file = saasapp.log [logging file location]
logging.level.org.springframework.web = DEBUG [logging level]

oAuthKey = <Appdirect - oAuth Key>
oAuthSecrete = <AppDirect - oAuth Secreate Key>
```

### Build
Springboot based application
```sh
mvn clean install
mvn clean 
```

### Run 
```sh
java -jar SaasApp-0.0.1-SNAPSHOT.jar
or
mvn spring-boot:run 
```
Note: jar file get availabel under target directory once application built sucessfully

### API End points
```sh
http://<host>:8080/
```
This will give the link for all the resources of applciation e.g. subscriptions, account, notification events.
Thhis application is aware of every resources it has and using HATEOS (Hypertext Resource Engine)
