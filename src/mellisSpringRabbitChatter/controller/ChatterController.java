/********************************
 * @author melanie.maronde
 *********************************/

package mellisSpringRabbitChatter.controller;
 
import java.util.Date;

import mellisSpringRabbitChatter.entities.Chat;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ChatterController {
	//injected beans --> chatter-servlet.xml
	private String startView;
	private String chatView;
	private String publishView;
	private String protocol;
	private AmqpTemplate template;
	private RabbitAdmin admin;
	
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
	public void setAdmin(RabbitAdmin admin) {
		this.admin = admin;
	}
	// handle ROOT ("/")
    @RequestMapping("/")
    public ModelAndView handleStartRequest() {
 
        String message = "Welcome to my little chatter web app!";
        ModelAndView modelview = new ModelAndView(startView, "message", message);
        modelview.addObject("chat_view", chatView);
        return modelview;
    }
    
    // handle submitted form (submit-button with name="add")
    @RequestMapping(params = "add", value = "/chat", method = RequestMethod.POST)
    public ModelAndView onSubmit(Model model, Chat chat) {
           
    	// Send a message to the "messages" queue
        template.convertAndSend("chatQueue", chat.getSender() +": "+chat.getMessage());
        
        chat = new Chat(chat.getSender());
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
    
    //add Post to protocol
    public void addPost(String message){
    	protocol = protocol + message + "<br>";
    }
    
    //return chat protocol to ajax function
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public @ResponseBody String updateChatlog() {
      return protocol;
    }
}