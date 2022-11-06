

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;

public class RMIServer {
    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 8090;
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        try {
            System.setProperty(RMI_HOSTNAME, hostName);
            // Create service for RMI
            Service service = (Service) new ServiceImpl();
            // Init service
            for (int i =1; i<50; i++){
                service.addElem(String.valueOf(i));
            }

            String serviceName = "FilesLines";

            System.out.println("Initializing " + serviceName);

            Registry registry = LocateRegistry.createRegistry(port);
            // Make service available for RMI
            registry.rebind(serviceName, service);

            // System.out.println("Start " + serviceName);
            int queuelength = service.getQueueLength();
            while(queuelength==30){
                queuelength = service.getQueueLength();
                Thread.sleep(10);
            }
            long beforeExec = System.nanoTime();
            while(queuelength > 0){

                // System.out.println("Queue length: "+queuelength);
                queuelength = service.getQueueLength();
                Thread.sleep(1000);
            }
            long afterExec = System.nanoTime();
            long spent = afterExec - beforeExec;
            // service.getResult();
            System.out.println("Time spent: "+spent);
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}