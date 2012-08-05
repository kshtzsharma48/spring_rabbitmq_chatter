/********************************
 * @author melanie.maronde
 *********************************/

package mellisSpringRabbitChatter.controller;
 
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mellisSpringRabbitChatter.entities.*;

import org.apache.catalina.util.Enumerator;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@Service("chatterController")
public class ChatterController {
	//injected beans --> chatter-servlet.xml
	private String startView;
	private String chatView;
	private String updateView;
	private String publishView;
	private String protocol;
	//Note: amqpTemplate and admin are autowired. 
	//There must be a bean with id == property_name, otherwise it will be null
	@Autowired
	private AmqpTemplate amqpTemplate = null;
	@Autowired
	private RabbitAdmin admin = null;

	public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}
	@Autowired
	public void setStartView(@Value("welcome") String startView){
    	this.startView = startView;
    }
	@Autowired
    public void setChatView(@Value("chat") String chatView){
    	this.chatView = chatView;
    }
	@Autowired
    public void setUpdateView(@Value("update") String updateView){
    	this.updateView = updateView;
    }
	@Autowired
    public void setPublishView(@Value("chat") String publishView){
    	this.publishView = publishView;
    }	
	@Autowired
    public void setProtocol(@Value("") String protocol){
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
    public ModelAndView onSubmit(HttpServletRequest req, HttpServletResponse res, Model model, Chat chat){
    	
    	/*
    	Enumeration e = req.getParameterNames();
    	while(e.hasMoreElements()){
    		String s = (String) e.nextElement();
    		System.out.println(s + ": " +req.getParameter(s));
    	}
    	*/
    	// Send a message to the "messages" queue
        amqpTemplate.convertAndSend("chatQueue", chat.getSender() +": "+chat.getMessage());
        
        chat = new Chat(chat.getSender());
        return showChat(model, chat);        
    }
    @RequestMapping(params = "delete", value = "/chat", method = RequestMethod.POST)
    public ModelAndView onDelete(Model model, Chat chat) {
        
    	// Clear protocol
        this.setProtocol("");
        
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
        model.addAttribute("update_view", updateView);
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