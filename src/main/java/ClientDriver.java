/**
 * Driver for the main app
 * So that GUI and Client can remain seperate but call each other's methods
 */
public class ClientDriver {

    	public static void main(String[] args){
		//pretty simple, it just makes a client then calls the initial method
		Client client = new Client();
        client.initial();
	}

}
