package myE4Package;

import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.swt.widgets.Event;

public class MyEventSubscriber {
	@Inject
	private static IEventBroker eventBroker;
	
//	@Inject @Optional
//	void myEventHandler(@UIEventTopic(MyEventConstants.TOPIC_TODO_NEW) String payload) {
//			System.out.println(payload);		
//	}
	
	@PostConstruct
//	void init(IEventBroker b) {
//		b.subscribe(MyEventConstants.TOPIC_TODO_NEW, extractEventData(this::handleEvent));
//	}
	private void handleEvent(String data){
		System.out.println(data);
	}
//	public static <T> Consumer<Event> extractEventData( Consumer<T> dataConsumer) {
//		   return e -> (T)e.getProperty(IEventBroker.DATA);
//	}
 }
