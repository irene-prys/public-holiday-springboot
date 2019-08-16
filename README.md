# public-holiday-springboot
Demo application which demonstrates the service which is gonna communicate with Alexa skill.
Implemented using spring boot + ASK SDK for Java (to validate the request)

## Run application locally
* Run the application 
* [Download and install the ngrok](https://ngrok.com/download). 
As you have a standalone service to communicate with Alexa, you need it to be available through the Internet and it must be available with the https. Ngrok allows to meet all these requirements and run the application locally. 

To run the ngrok: 
* move to the folder where ngrok installed; 
* run the terminal from this folder; 
* run the following command in the terminal: 
```
./ngrok http 8080
```
Ngrok will generate two links: for **http** and for **https**.
