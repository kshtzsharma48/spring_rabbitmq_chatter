/********************************
 * @author melanie.maronde
 *********************************/

package mellisSpringRabbitChatter.controller;
 
import mellisSpringRabbitChatter.entities.Chat;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ChatterController {
	//injected beans --> chatter-servlet.xml
	private String startView;
	private String chatView;
	private String publishView;
	private String protocol;
	private AmqpAdmin admin;
	private AmqpTemplate template;
	
	public void setAdmin(AmqpAdmin admin) {
		this.admin = admin;
	}
	public void setTemplate(AmqpTemplate template) {
		this.template = template;
	}
	public void setStartView(String startView){
    	this.startView = startView;
    }	
    public void setChatView(String chatView){
    	this.chatView = chatView;
    }
    public void setPublishView(String publishView){
    	this.publishView = publishView;
    }	
    public void setProtocol(String protocol){
    	this.protocol = protocol;
    }
    
	// handle ROOT ("/")
    @RequestMapping("/")
    public ModelAndView handleStartRequest() {
 
        String message = "Welcome to my little chatter web app!";
        ModelAndView modelview = new ModelAndView(startView, "message", message);
        modelview.addObject("chat_view", chatView);
        return modelview;
    }
    
    // handle submitted form (add)
    @RequestMapping(params = "add", value = "/chat", method = RequestMethod.POST)
    public ModelAndView onSubmit(Model model, Chat chat) {
           
    	// Send a message to the "messages" queue
        template.convertAndSend("chatQueue", chat.getSender() +": "+chat.getMessage());
        chat = new Chat(chat.getSender());
        
        return showChat(model, chat);
          
    	//return showChat(model, chat);        
    }
    
    // handle submitted form (update)
    @RequestMapping(params = "update", value = "/chat", method = RequestMethod.POST)
    public ModelAndView onUpdate(Model model, Chat chat) {

        chat = new Chat(chat.getSender());
        
        // Receive a message from the "messages" queue
        String message = (String) template.receiveAndConvert("chatQueue");
        if (message != null){
            System.out.println("Message received: " +message);
        	protocol = protocol + message + "<br>";
        }
        else
            System.out.println("Queue is empty");
        
    	return showChat(model, chat);        
    }
    
    //handles form request (get)
    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public ModelAndView showChat(Model model, Chat chat) {
        
    	if(chat==null){
    		chat = new Chat();
    	}
    	model.addAttribute(chat);
        model.addAttribute("submit_view", publishView);
        model.addAttribute("protocol", protocol);
        
        ModelAndView modelview = new ModelAndView(chatView);
    	return modelview;
    }
    
}