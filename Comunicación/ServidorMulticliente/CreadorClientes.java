import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class CreadorClientes extends Observable implements Runnable{
	
	private ServerSocket serverSocket;
	private static CreadorClientes instance = null;

	private CreadorClientes() {
		try {
			serverSocket = new ServerSocket(5000);
		} catch (IOException e) {
			System.out.println("Error inicializando el serverSocket");
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				Socket socket = serverSocket.accept();
				CrearCliente(socket);
			} catch (IOException e) {
				System.out.println("Error asignando socket al cliente");
			}
		}
	}

	private void CrearCliente(Socket socket) {
		setChanged();
		notifyObservers(socket);
		clearChanged();
	}
	
	private synchronized static void createInstance() {
        if (instance == null) { 
            instance = new CreadorClientes();
        }
    }
	
	public static CreadorClientes getInstance(){
		if (instance == null) createInstance();
		return instance;
	}
}
