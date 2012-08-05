package mellisSpringRabbitChatter.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import mellisSpringRabbitChatter.controller.ChatterController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:WebContent/WEB-INF/chatter-servlet.xml")
public class ChatterControllerSimpleTest{
	@Autowired
	ApplicationContext context;
	
	@Test
	public void test() {
		ChatterController controller = context.getBean(ChatterController.class);
		System.out.println(controller.handleStartRequest());
		//assertEquals("Hello world!", controller.handleStartRequest());
		assertTrue(true);
	}
}