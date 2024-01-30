package work_queues;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public class Worker {

	private static final String TASK_QUEUE_NAME = "task_queue";
	
	public static void main(String[] args)  throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setUsername("username");
		factory.setPassword("password");
		
		final Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();
		
		channel.queueDeclare(TASK_QUEUE_NAME,true,false,false,null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		
		//channel.basicQos(1);
		
		DeliverCallback deliverCallback = new DeliverCallback() {
			@Override
			public void handle(String consumerTag, Delivery delivery) throws IOException {
				String message = new String(delivery.getBody(),"UTF-8");
				
				System.out.println(" [x] Received '"+message+"'");
				try {
					doWork(message);
				}finally {
					System.out.println(" [x] Done");
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false );
				}
			}
		};
		
		boolean autoAck=true;
		channel.basicConsume(TASK_QUEUE_NAME,autoAck,deliverCallback,consumerTag -> {});

	}
	
	private static void doWork(String task) {
		for(char ch : task.toCharArray()) {
			if(ch == '.') {
				try {
					Thread.sleep(1000);
				}catch(InterruptedException _ignored) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

}
