# Technologies (pipeline)
### Graphical User Interface (GUI)
Xscada uses the eclipse E4 Rich Client Platform (RCP).  Oracle's JavaFX is selected over Eclipse SWT because it provides support for line charts (trends), 3D views, Scene Builder and #FXML annotation that facilitates Model-View-Controller (MVC) software achitectual pattern.  Scene Builder can be found [here.] (https://gluonhq.com/open-source/scene-builder)  E(fx)clipse provides JavaFX access to the E4 RCP.  The elipse E4 tooling can be used to generate an "application.e4xmi" file together with fragment file(s) for each station.  The Scene Builder generates *controller.java and *.fxml files.  All three of these XML type of files will be generated from the XML describing the pipeline using Extensible Stylesheet Language Transformations (XSLT).
### .XPSL (XML describing the pipeline)
The [Pipeline Simulation Iterest Group's] (https://psig.org/) schema definition will be used as a starting point.  The schema definition is contained in these files:
* XPSLElemType_1_4.xsd
* SPSLGlobals_1_4.xsd
* XPSLMain_1_4.xsd
* XPSLUnits_1_4.xsd
* XPSLValueTypes_1_4.xsd

It will be necessary to get PSIGs release of its intellectual property on the XPSL schema in order to be hosted at eclipse.org. 

To be very very versatile few constraints are enforced.  For example, nearly all attributes and elements are optional which means there is no enforcement that “downNode” and “upNode” attributes are required for inline nodes so the model may end up being incomplete.  On the other hand the unused attribute “id” defined in the “BaseAttGroup” can be used to link elements to the xscada database.  Also the “DeviceGroupType” and “DeviceSequenceType” can be used for station and profile views respectively.  Despite a number of HMI* (human-machine interface) attributes and elements we cannot use them because the location of controls change as the size of the view changes and when the view is small may not visible at all.  Instead we will use the “xCoordinate” and “yCoordinate” elements of “LocationType” to determine the relative position of controls.  These values will be relative to the centre line of the pipeline and the station milepost while looking downstream (e.g. negative “xCoordinate” left of centre line and negative “yCoordinate” before the station milepost.)  Just to be thorough XPSL also provides flexibility with its “ExtensionType” and “CommentType.”  There are sensors like “man on site” and pump “vibration” that are not relevant to pipeline simulation and not currently included in the XPSL schema.
### Communications Between Computers
Communication between computers will use the User Datagram Protocol (UDP).  Initially we'll use the MulticastSocket class found in package java.net.  This [tutorial] (https://docs.oracle.com/javase/tutorial/networking/datagrams/broadcasting.html) shows how it's done.  It may be desirable to use Eclipse Communication Framework (ECF) in the future.

The datagrams will be a compressed XML files.  See [this W3C draft] (https://www.w3.org/TR/2015/WD-exi-c14n-20150521/).  Check out [Nagasena's OpenEXI] (http://openexi.sourceforge.net/).

RTUs or PLCs (we will call them RTUs from now on) in the stations will need to do the following
* Accept and process a configuration file.
* Send out a complete datagram of it's state at startup and on request.
* Generate a databram "heartbeat" every second.
* Accept and process control and setpoint request.
* Support redundant RTUs with automatic failover when a heartbeat or a confirmation message from the primary RTU is missed.


### Intra-Computer Communication
An enhancement request to provide an e(fx)clipse NoUIPart or VirtualPart that would be available at the application level of an application.e4xmi file.  The @PostConstruct method would not have any arguments.  It that happens then we can use the e(fx)clipse EventBus class for communication.

The message namespace topic will start with org/xscada.  So for a man on site status at at StationX the topic name would be org/xscada/StationX/ManOnSite.  [Temporary pointer] (http://felix.apache.org/documentation/subprojects/apache-felix-event-admin.html).