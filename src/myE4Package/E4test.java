package myE4Package;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class E4test {
	@Inject
	private MPart part;
	@Inject
	private IEclipseContext context;
	
	@PostConstruct
	void initUI(BorderPane pane) {
		class Pressure {
			public String description;
			public double pressure;
		}
		Map<String, String> props = part.getProperties();
		System.out.println(props.get("title"));
		System.out.println(props.get("namespace prefix"));
		System.out.println("y-axis");
		List<String> vars = part.getVariables();
		for (String temp : vars) {
			System.out.println(temp);
			Pressure pres = new Pressure();
			pres.description = "Analog description";
			pres.pressure = 34.56;
			context.set(temp, pres);
		}
		Pressure pres2 = new Pressure();
		pres2.description = "junk desc";
		pres2.pressure = 12.34;
		context.set("junk", pres2);
		Pressure retrieve = (Pressure) context.get("junk");
		System.out.println(retrieve.description + " " + retrieve.pressure);
 		Button b = new Button("button");
		pane.setCenter(b);
	}
	
}
