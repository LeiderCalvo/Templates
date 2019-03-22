package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;

import leidercalvo.envio.Modelo;

public class ComToServer extends Observable implements Runnable {

	private Socket s;
	private InputStream is;
	private ObjectInputStream ois;
	private OutputStream os;
	private ObjectOutputStream oos;

	public ComToServer(String ip, int puerto) {
		try {
			s = new Socket(InetAddress.getByName(ip), puerto); //crear el punto de entrada y salida
			is = s.getInputStream(); //crear la entrada
			ois = new ObjectInputStream(is); //definir la entrada para objetos (serializacion)
			os = s.getOutputStream(); //crear la salida
			oos = new ObjectOutputStream(os); //definir la salida para objetos (serializacion)
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				recibir();
				Thread.sleep(100);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void recibir() throws IOException {
		/*
		leer datos en string
		byte[] buf = new byte[128];
		is.read(buf);
		setChanged();
		notifyObservers(new String(buf).trim());
		clearChanged();*/
		
		Modelo modelo;
		try {
			modelo = (Modelo)ois.readObject(); //convertir la serializacion que llega del servidor a el objeto modelo
			setChanged();
			notifyObservers(modelo); //notificar al observador (Logica)
			clearChanged();
		} catch (ClassNotFoundException e) {
			System.out.println("No encuentra la clase modelo en controlcliente");
		}
	}

	public void enviar(Modelo modelo) {
		/*
		enviar datos en string
		try {
			salida.write(mensaje.getBytes());
			salida.flush();
		} catch (IOException e) {
			System.out.println("error enviando un string");
		}*/
		try {
			oos.writeObject(modelo); //serializar el objeto que voy a enviar
			oos.flush(); //enviar el mensaje (objeto serializado)
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
