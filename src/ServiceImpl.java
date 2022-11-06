
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implementation of the remote service.
 */
public class ServiceImpl extends UnicastRemoteObject implements Service {
    private final BlockingQueue<String> queue;
    private final BlockingQueue<String> result;

    public ServiceImpl() throws RemoteException {
        super();
        this.queue = new LinkedBlockingQueue<>();
        this.result = new LinkedBlockingQueue<>();
    }

    @Override
    public String pollElem() throws RemoteException {
        String res = this.queue.poll();
        // System.out.println("Queue has: "+this.queue);
        return res;
    }

    @Override
    public void addElem(String str) throws RemoteException {
        this.queue.add(str);
        // System.out.println("Queued: " + str);
    }


    @Override
    public void result(String str) throws RemoteException {
        this.result.add(str);
        // System.out.println("Added: " + str);
    }

    @Override
    public String getResult()throws RemoteException, InterruptedException{
        System.out.println(this.result);
        return null;
    }


    @Override
    public int getQueueLength()throws RemoteException{
        return this.queue.size();
    }
}
