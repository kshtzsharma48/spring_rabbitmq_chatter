package mellisSpringRabbitChatter.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mellisSpringRabbitChatter.controller.ChatterController;

@Service("messageHandler")
public class MessageHandler {
	
	@Autowired
	private ChatterController chatterController;

    public void handleMessage(String msg) {
        chatterController.addPost(msg);
    }
	
	public void setChatterController(ChatterController chatterController) {
		this.chatterController = chatterController;
	}
}
