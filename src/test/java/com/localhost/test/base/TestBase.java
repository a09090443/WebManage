package com.localhost.test.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
@WebAppConfiguration
public class TestBase {

	// @Test
	// public void testSampleService() {
	// assertEquals("class
	// com.areyes1.jgc.junit.spring.service.SampleServiceImpl",
	// this.sampleService.getClass().toString());
	// }
	//
	// @Test
	// public void testSampleServiceGetAccountDescription() {
	// // Check if the return description has a Description: string.
	// assertTrue(sampleService.getOrderDescription().contains("Description:"));
	// }

}
