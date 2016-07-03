package myE4Package;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;

public class MyEventSubscriber {
	@Inject
	private IEventBroker eventBroker;

/*
	code from bottom of http://www.vogella.com/tutorials/Eclipse4EventSystem/article.html

	@Inject @Optional
	public void getEvent(@EventTopic(MyEventConstants.TOPIC_TODO_NEW) String test)) {
		System.out.println(test);
	}
*/
	
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
