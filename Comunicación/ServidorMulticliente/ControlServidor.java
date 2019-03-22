import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import leidercalvo.envio.Modelo;

public class ControlServidor extends Observable implements Observer{
	
	private CreadorClientes creadorClientes;
	private ArrayList<ControlCliente> clientes;
	
	public ControlServidor() {
		creadorClientes = CreadorClientes.getInstance();
		creadorClientes.addObserver(this);
		clientes = new ArrayList<ControlCliente>();
		Thread thread = new Thread(creadorClientes);
		thread.start();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof CreadorClientes) {
			Socket socket = (Socket) arg;
			ControlCliente cliente = new ControlCliente(socket);
			cliente.addObserver(this);
			clientes.add(cliente);
			System.out.println(clientes.size()+"");
		}
		
		if(o instanceof ControlCliente) {
			Modelo modelo = (Modelo) arg;
			/*
			 * Aqui deberia hacer algo con el mensaje que llega
			 * 
			 */
		}
	}

}
