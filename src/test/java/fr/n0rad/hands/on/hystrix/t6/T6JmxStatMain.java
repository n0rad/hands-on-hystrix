package fr.n0rad.hands.on.hystrix.t6;

public class T6JmxStatMain {

    public static void main(String[] args) throws Exception {
        // TODO register metrics system

        new T6JmxStatCommand().execute(); // hystrix need at least an execution to push something into JMX

        while (true) {
            if (false) {
                break;
            }
            Thread.sleep(1000);
        }

        // use visualvm with mbean plugin to visually assert ;)

        //TODO don't know how to get jmx connection :/

//        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost/jmxrmi");
//        JMXConnector connect = JMXConnectorFactory.connect(url);
//        MBeanServerConnection mbsc = connect.getMBeanServerConnection();
//        ObjectName o = new ObjectName("com.netflix.servo:name=countSuccess,instance=T6JmxStatCommand,type=HystrixCommand");
//        Object value = mbsc.getAttribute(o, "value");
//        connect.close();
    }

}