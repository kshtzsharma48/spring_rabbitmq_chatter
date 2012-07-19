This app implements a simple spring chat app + rabbitmq

![chatter app](https://github.com/melaniemaronde/spring_rabbitmq_chatter/raw/master/ui.gif)

<b>Pre-requisite:</b>
- Setup and start rabbitmq (localhost:5672 or change configuration in chatter-servlet.xml)
- Create queue "chatQueue"

<b>Evolution</b>

<u>spring_chatter</u>
- Implements simple chat without messaging
- XML configuration (chatter-servlet.xml)
- JSP view with parameters (chat.jsp --> object; chat2.jsp --> String params)
- Udate button

<u>spring-rabbitmq-chatter 1.0_sync</u>
- Implements simple chat with messaging (sendAndConvert, receiveAndConvert)
- XML configuration (chatter-servlet.xml; includes amqp configuration)
- JSP view with parameters
- Udate button (get message from queue and update view)

<u>spring-rabbitmq-chatter 1.0_async_messaging</u>
- Implements simple chat with messaging (sendAndConvert, queue listener --> MessageHandler)
- XML configuration (chatter-servlet.xml; includes amqp configuration)
- JSP view with parameters
- Udate button (update view)

<u>spring-rabbitmq-chatter 1.0_async_messaging_ajax</u>
- Implements simple chat with messaging (sendAndConvert, queue listener --> MessageHandler)
- XML configuration (chatter-servlet.xml; includes amqp configuration)
- JQuery 
- JSP view with parameters (use jquery to implement ajax for protocol fiv)
- Udate button (update ajax element)

<u>spring-rabbitmq-chatter 1.0_async</u>
- Implements simple chat with messaging (sendAndConvert, queue listener --> MessageHandler)
- XML configuration (chatter-servlet.xml; includes amqp configuration)
- JQuery 
- JSP view with parameters (use jquery to implement ajax for protocol fiv; use Interval to update ajax element each second)

<u>spring-rabbitmq-chatter 1.0_di_refactoring1</u>
- enabled annotations in chatter-servlet.xml
- created rabbitmq-context.xml containing rabbitmq related configuration beans
- created webmvc-context.xml containing mvc related configuration beans
- ChatterController + MessageHandler: annotation based config (@Service, @Autowired)
Note: there is a controversal discussion about whether to use annotation-based or xml based configuration.

<u>spring-rabbitmq-chatter 1.0_cloudfoundry</u>
To use cloudfoundry services (e.g. rabbitmq) there are two options:
- auto-reconfiguration 
Without changing a single line of code (use one of the other branches --> chatQueue must be created somehow...).
Cloud Foundry automatically re-configures the relevant bean definitions to bind them to cloud services
Note: take care of limitations
- explicit configuration

At the moment there is no way to create queue in cloud foundry via rabbitmq client tools (e.g. management console).
Thats why the queue must be created by the spring application: rabbitmq-context.xml





