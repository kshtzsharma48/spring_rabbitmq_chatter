This app implements a simple spring chat app + rabbitmq

![chatter app](https://github.com/melaniemaronde/spring_rabbitmq_chatter/raw/master/ui.gif)

<b>Pre-requisite:</b>
- Setup and start rabbitmq (localhost:5672 or change configuration in chatter-servlet.xml)
- Create queue "chatQueue"

<b>Evolution</b>

1. spring_chatter
- Implements simple chat without messaging
- XML configuration (chatter-servlet.xml)
- JSP view with parameters (chat.jsp --> object; chat2.jsp --> String params)
- Udate button

2. spring-rabbitmq-chatter 1.0_sync
- Implements simple chat with messaging (sendAndConvert, receiveAndConvert)
- XML configuration (chatter-servlet.xml; includes amqp configuration)
- JSP view with parameters
- Udate button (get message from queue and update view)

3. spring-rabbitmq-chatter 1.0_async_messaging
- Implements simple chat with messaging (sendAndConvert, queue listener --> MessageHandler)
- XML configuration (chatter-servlet.xml; includes amqp configuration)
- JSP view with parameters
- Udate button (update view)

4. spring-rabbitmq-chatter 1.0_async_messaging_ajax
- Implements simple chat with messaging (sendAndConvert, queue listener --> MessageHandler)
- XML configuration (chatter-servlet.xml; includes amqp configuration)
- JQuery 
- JSP view with parameters (use jquery to implement ajax for protocol fiv)
- Udate button (update ajax element)

5. spring-rabbitmq-chatter 1.0_async
- Implements simple chat with messaging (sendAndConvert, queue listener --> MessageHandler)
- XML configuration (chatter-servlet.xml; includes amqp configuration)
- JQuery 
- JSP view with parameters (use jquery to implement ajax for protocol fiv; use Interval to update ajax element each second)





