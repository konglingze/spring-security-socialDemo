package edu.nuc.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MockQueue {

	private String placeOrder;

	private String completeOrder;

	private Logger logger = LoggerFactory.getLogger(getClass());


//模拟应用1
	public void setPlaceOrder(String placeOrder) throws Exception {
		new Thread(() -> {

			logger.info("接到下单请求");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.completeOrder = placeOrder;
			logger.info("下单请求处理完成" + placeOrder);

		}).start();
	}

	public String getPlaceOrder() {
		return placeOrder;
	}

	public String getCompleteOrder() {
		return completeOrder;
	}

	public void setCompleteOrder(String completeOrder) {
		this.completeOrder = completeOrder;
	}

}
