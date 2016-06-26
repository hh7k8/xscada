package myE4Package;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

//import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;

public class MyEventBroker {
	
//		@Inject
//		IEclipseContext eclipseContext;
		@Inject
		private static IEventBroker eventBroker;
		
		public static void main(String[] args) {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(30);
				} catch(InterruptedException ex) {
					System.out.println("Exception on sleep: " + ex);
				}
				boolean wasDispatchedSuccessfully = eventBroker.post(MyEventConstants.TOPIC_TODO_NEW, "text");
				System.out.println("post result " + wasDispatchedSuccessfully);
			}
		}
}