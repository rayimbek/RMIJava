

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static String countLineJava8(String fileName) {
        // Path path = Paths.get(fileName);
        Path path = Paths.get(".\\src\\datas\\" + fileName);
        long lines = 0;
        try {
            // much slower, this task better with sequence access
            // lines = Files.lines(path).parallel().count();
            lines = Files.lines(path).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "file: " + fileName + " length:" + lines + ";";
    }

    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 8090;
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        String SERVICE_PATH = "//" + hostName + ":" + port + "/FilesLines";
        try {
            System.setProperty(RMI_HOSTNAME, hostName);
            Service service = (Service) Naming.lookup(SERVICE_PATH);

            while(true){
                String receiv = service.pollElem();
                if (receiv == null) {
                    System.out.println("Received none!");
                    break;
                } else {
                    int ind = Integer.parseInt(receiv);
                    String addElement = countLineJava8("test" + ind + ".txt");
                    System.out.println("Received top number in queue: " + receiv);
                    service.result(addElement);
                    service.getResult();
                    Thread.sleep(ind%10*100);
                    System.out.println("Done: " + ind);
                }
            }
        } catch (Exception ex) {
            System.out.println("Server shut down!");
            ex.printStackTrace();
        }
    }


}
