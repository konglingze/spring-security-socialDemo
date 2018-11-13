package edu.nuc.web.async;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

//同步调用
@RestController
public class AsyncController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MockQueue mockQueue;
	
	@Autowired
    private DeferredResultHolder deferredResultHolder; 
	
	
/*	@RequestMapping("/order")
	public String order() throws Exception {
		logger.info("主线程开始");
		Thread.sleep(1000);// 同步方法开始
		logger.info("主线程返回");

		return "";
	}
*/
	//Runable异步处理请求
/*	@RequestMapping("/order")
	public Callable<String> order() throws Exception {
		logger.info("主线程开始");
		//开启其他线程
		Callable<String> result = new Callable<String>() {

			@Override
			public String call() throws Exception {

				logger.info("副线程开始");
				Thread.sleep(1000);// 逻辑方法开始				
				logger.info("副线程返回");
				return "success";
			}
			
		};
		logger.info("主线程返回");

		return result;
	}*/
	
	// 生成异步处理
	@RequestMapping("/order")
	public DeferredResult<String> order() throws Exception {
		logger.info("主线程开始");
		// 开启其他线程
		String orderNumber = RandomStringUtils.randomNumeric(8);

		logger.info("已提交" + orderNumber + "订单");
		mockQueue.setPlaceOrder(orderNumber);

		DeferredResult<String> result = new DeferredResult<>();

		deferredResultHolder.getMap().put(orderNumber, result);

		logger.info("主线程返回");

		return result;
	}

}
