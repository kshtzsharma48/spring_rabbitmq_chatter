package mellisSpringRabbitChatter.test;

import static org.junit.Assert.*;
import mellisSpringRabbitChatter.entities.Chat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:WebContent/WEB-INF/chatter-servlet.xml")
public class ChatTest {
	@Autowired
	ApplicationContext context;
	
	@Test
	public void test1() {
		Chat chat = new Chat();
		chat.setSender("TestSender");
		chat.setMessage("Hello World!");
		
		assertEquals("TestSender", chat.getSender());
		assertEquals("Hello World!", chat.getMessage());
		
	}
	
	@Test
	public void test2() {
		Chat chat = new Chat("TestSender");
		chat.setMessage("Hello World!");
		
		assertEquals("TestSender", chat.getSender());
		assertEquals("Hello World!", chat.getMessage());
		
	}
}
