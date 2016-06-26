package myE4Package;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;

import javafx.scene.layout.BorderPane;

public class MyEventSubscriber {
	@Inject
	private static IEventBroker eventBroker;
	
	@Inject @Optional
	void myEventHandler(@UIEventTopic(MyEventConstants.TOPIC_TODO_NEW) String payload) {
			System.out.println(payload);		
	}
	
	@PostConstruct
	void initUI(BorderPane pane) {
// 			boolean subresult = eventBroker.subscribe(MyEventConstants.TOPIC_TODO_NEW, myEventHandler);
 		}
	
 }
