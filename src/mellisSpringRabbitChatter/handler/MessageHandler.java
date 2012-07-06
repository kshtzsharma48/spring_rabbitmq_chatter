package mellisSpringRabbitChatter.handler;

import mellisSpringRabbitChatter.controller.ChatterController;

public class MessageHandler {
	
	private ChatterController chatterController;

    public void handleMessage(String msg) {
        chatterController.addPost(msg);
    }
	
	public void setChatterController(ChatterController chatterController) {
		this.chatterController = chatterController;
	}
}
