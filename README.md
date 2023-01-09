# Discount total price calculator.

To prepare docker image with application. Maven build is used inside:
```
docker build -t price-calculator .
```

To run application:
```
docker run -it price-calculator:latest
```

If you what to change configuration just modify:
```
src/main/resources/application.yml
```