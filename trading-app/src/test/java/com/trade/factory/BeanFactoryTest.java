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
	void getValidConfigExecutor() {
		assertEquals(AlgoAdapter.class, ClassUtils.getUserClass(beanFactory.getCommandExecutor("algo").getClass()));
	}
	
	@Test
	void getInValidConfigExecutor() {
		assertThrows(NoSuchBeanDefinitionException.class, () -> ClassUtils.getUserClass(beanFactory.getCommandExecutor("algo1").getClass()));
	}
	
	@Test
	void testNullConfigExecutor() {
		assertThrows(IllegalArgumentException.class, () -> ClassUtils.getUserClass(beanFactory.getCommandExecutor(null).getClass()));
	}

}
