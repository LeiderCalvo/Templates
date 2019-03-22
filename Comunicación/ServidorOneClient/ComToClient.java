package client;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

import leidercalvo.envio.Modelo;

public class ComToApp extends Observable implements Runnable{
	
	private ServerSocket serverSocket; 
	private Socket socket;
	
	private OutputStream os;
	private ObjectOutputStream oos;
	private InputStream is;
	private ObjectInputStream ois;
	
	/*
	 * Esta es la comunicacion con la app
	 */
	public ComToApp() {	
		try {
			serverSocket = new ServerSocket(5000);
			socket = serverSocket.accept(); //Metodo sincrono, aqui se detiene haste recibir una solicitud
			
			os = socket.getOutputStream(); //crear la salida
			oos = new ObjectOutputStream(os); //definir la salida para serializacion
			
			is = socket.getInputStream(); //crear la entrada
			ois = new ObjectInputStream(is); //definir la entrada para serializacion
			
		} catch (IOException e) {
			System.out.println("Error inicializando el serverSocket");
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				recibir();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Error en el sleep del hilo de Control Cliente");
			} catch (IOException e) {
				System.out.println("Error recibiendo informacion en ControlCliente");
			}
		}
	}

	private void recibir() throws IOException {
		
		Modelo modelo;
		try {
			modelo = (Modelo) ois.readObject(); //interpretar el mensaje del cliente de serializaicon a objeto
			setChanged();
			notifyObservers(modelo); //notificar al observador
			clearChanged();
		} catch (ClassNotFoundException e) {
			System.out.println("No encuentra la clase modelo en controlcliente");
		}
	}
	
	private void enviar(Modelo modelo) {
		try {
			oos.writeObject(modelo); //serializar el objeto a enviar
			oos.flush(); //enviar el objeto
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
