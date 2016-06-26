package myE4Package;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

//import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;

import javafx.scene.layout.BorderPane;

public class MyEventBroker {
	
//		@Inject
//		IEclipseContext eclipseContext;
		@Inject
		private static IEventBroker eventBroker;
		
		@PostConstruct
		void initUI(BorderPane pane) {
			(new NonUIThread()).start();
		}
		public class NonUIThread extends Thread {
			public void run() {
				while (true) {
					try {
						TimeUnit.SECONDS.sleep(5);
					} catch(InterruptedException ex) {
						System.out.println("Exception on sleep: " + ex);
					}
					boolean wasDispatchedSuccessfully = eventBroker.post(MyEventConstants.TOPIC_TODO_NEW, "text");
					System.out.println("post result " + wasDispatchedSuccessfully);
				}
				
			}
 		}
}