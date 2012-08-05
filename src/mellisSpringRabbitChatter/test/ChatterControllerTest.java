package mellisSpringRabbitChatter.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import mellisSpringRabbitChatter.controller.ChatterController;
import mellisSpringRabbitChatter.entities.Chat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:WebContent/WEB-INF/chatter-servlet.xml")
public class ChatterControllerTest {

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private ChatterController controller;
	private AnnotationMethodHandlerAdapter adapter;

	@Autowired
	private ApplicationContext context;

	@Before
	public void setUp() {
		request    = new MockHttpServletRequest();
	    response   = new MockHttpServletResponse();
	    response.setOutputStreamAccessAllowed(true);
	    controller = context.getBean(ChatterController.class);
	    adapter = new AnnotationMethodHandlerAdapter();
	}

	    
	@Test
	public void handleStartRequestTest() throws Exception {
		request.setMethod("GET");
		request.setRequestURI("/");

		final ModelAndView mav = adapter.handle(request, response, controller);

		// check view name
		ModelAndViewAssert.assertViewName(mav, "welcome");
		    
		//check model
		Map<String, Object> expectedModel = new HashMap<String,Object>();
		expectedModel.put("message", "Welcome to my little chatter web app!");
		expectedModel.put("chat_view", "chat");
		    
		ModelAndViewAssert.assertModelAttributeValues(mav, expectedModel);
	}
	
	@Test 
	public void showChatTest() throws Exception {
		request.setMethod("GET");
	    request.setRequestURI("/chat");
	    
	    final ModelAndView mav = adapter.handle(request, response,controller);
	    // check view name	    
	    ModelAndViewAssert.assertViewName(mav, "chat");

	    //check model
	    ModelAndViewAssert.assertModelAttributeValue(mav, "submit_view", "chat");
	    ModelAndViewAssert.assertModelAttributeValue(mav, "update_view", "update");
	    ModelAndViewAssert.assertModelAttributeValue(mav, "protocol", "");
	    
	    Chat chat = (Chat) mav.getModel().get("chat");
	    assertEquals("", chat.getSender());
	    assertEquals("", chat.getMessage());
	}
	
	@Test 
	public void onSubmitTest() throws Exception {
		
		request.setMethod("POST");
	    request.setRequestURI("/chat");
	    request.addParameter("add", "add");
	    request.setParameter("sender", "TestSender");
	    request.setParameter("message", "Hello World!");
	    
	    final ModelAndView mav = adapter.handle(request, response, controller);
	    	    
	    // check view
	    ModelAndViewAssert.assertViewName(mav, "chat");
	    
	    //check model
	    ModelMap map = mav.getModelMap();
	    Chat chat = (Chat) map.get("chat");
	    assertEquals("TestSender", chat.getSender());
	    assertEquals("", chat.getMessage());
	    //wait for rabbitmq
	    Thread.sleep(1000);
	    assertEquals("TestSender: Hello World!<br>", controller.updateChatlog());

	}
	
	@Test 
	public void deleteTest() throws Exception {
		
		request.setMethod("POST");
	    request.setRequestURI("/chat");
	    request.addParameter("delete", "delete");
	    request.setParameter("sender", "TestDeleter");
	    request.setParameter("message", "Goodbye World!");
	    
	    final ModelAndView mav = adapter.handle(request, response, controller);
	    	    
	    // check view
	    ModelAndViewAssert.assertViewName(mav, "chat");
	    
	    //check model
	    ModelMap map = mav.getModelMap();
	    Chat chat = (Chat) map.get("chat");
	    assertEquals("TestDeleter", chat.getSender());
	    assertEquals("", chat.getMessage());
	    //wait for rabbitmq
	    Thread.sleep(1000);
	    assertEquals("", controller.updateChatlog());

	}
	/*
	//add post to protocol and check that updateChatlog() returns updated protocol
	@Test 
	public void addPostTest() throws Exception {
		ChatterController controller = context.getBean(ChatterController.class);
		controller.addPost("Start new chat test");
		controller.addPost("test message");
			
		assertEquals("Start new chat test<br>test message<br>", controller.updateChatlog());

	}
	*/

}