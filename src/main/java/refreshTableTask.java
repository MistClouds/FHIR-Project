import java.util.TimerTask;

/**
 * This is the class where the task is handled.
 * It basically just calls a method in the client when the task is run.
 */
public class refreshTableTask extends TimerTask {
    Client client;

    /**
     * The constructor.
     * @param client a Client
     */
    public refreshTableTask(Client client){
        this.client = client;
    }

    /**
     * The run method. just runs a method from client.
     */
    @Override
    public void run() {
        client.getUpdatedCholesterolData();
    }
}
