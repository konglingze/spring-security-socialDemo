package edu.nuc.web.async;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


//应用2和异步返回
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {// spring初始化完毕事件

	@Autowired
	private MockQueue mockQueue;

	@Autowired
	private DeferredResultHolder deferredResultHolder;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		new Thread(() -> {

			while (true) {
				if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())) {

					String orderMumber = mockQueue.getCompleteOrder();

					logger.info("返回"+ orderMumber+"订单处理结果" );

					deferredResultHolder.getMap().get(orderMumber).setResult("place order success");

					mockQueue.setCompleteOrder(null);
				} else {

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();

	}

}
