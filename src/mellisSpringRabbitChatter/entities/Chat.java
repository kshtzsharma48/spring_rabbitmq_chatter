package mellisSpringRabbitChatter.entities;

public class Chat {
	private String sender;
	private String message;
	
	public Chat(){
		this.sender = "";
		this.message = "";
	}
	public Chat(String sender){
		this.sender = sender;
		this.message = "";
	}	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
	
	public String toString(){
		return sender + ": " +message;
	}

}
