package edu.nuc.service.impl;

import org.springframework.stereotype.Service;

import edu.nuc.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {

	public void hello(String name) {

		System.out.println("使用hello service" + name);

	}
}
