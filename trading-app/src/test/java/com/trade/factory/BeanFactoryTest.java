package com.trade.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ClassUtils;

import com.trade.adaptor.AlgoAdapter;

@SpringBootTest
public class BeanFactoryTest {
	
	@Autowired
	private BeanFactory beanFactory;
	
	@Test
	void getValidAdaptor() {
		assertEquals(AlgoAdapter.class, ClassUtils.getUserClass(beanFactory.getAdaptor("algo").getClass()));
	}
	
	@Test
	void getInValidAdaptor() {
		assertThrows(NoSuchBeanDefinitionException.class, () -> ClassUtils.getUserClass(beanFactory.getAdaptor("algo1").getClass()));
	}
	
	@Test
	void testNullAdaptor() {
		assertThrows(IllegalArgumentException.class, () -> ClassUtils.getUserClass(beanFactory.getAdaptor(null).getClass()));
	}

}
