@echo off
echo Starting API
cd estore-api
START mvn exec:java
echo Starting UI
cd ../estore-ui
ng serve
echo E-Store is up and running!
exit