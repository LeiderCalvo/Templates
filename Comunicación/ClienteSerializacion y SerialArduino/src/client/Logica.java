package client;

import java.util.Observable;
import java.util.Observer;

import leidercalvo.envio.Modelo;
import processing.core.PApplet;
import processing.serial.Serial;

public class Logica extends PApplet implements Observer, Runnable {

	private ComToServer com; // Comunicacion con el servidor
	private PApplet app;
	
	private Serial serial; //comunicacion con arduino

	public Logica() {
		
		serial = new Serial(this, Serial.list()[1], 9600);

		com = new ComToServer("127.0.0.1", 5000);
		com.addObserver(this);

		Thread t = new Thread(com);
		t.start();
		
	}

	@Override
	public void run() {
		while (true) {
			try {
				
				int dato = serial.read(); //leyendo los datos que arduino escribió en el puerto serial

				Modelo modelo = new Modelo("a", "b", "c");
				com.enviar(modelo); //enviando datos en forma de modelo al servidor

				/*
				 * Los procesos que pueda ejecutar la silla
				 */
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//Aqui llegan los mensajes de la comunicacion con el servidor
	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof ComToServer) {
			Modelo modelo = (Modelo) arg;
			/*
			 * Este mensaje lo envia el servidor Aqui deberia hacer algo con el mensaje que
			 * llega
			 */

			 serial.write(2); // le estoy enviando un "int = 2" a arduino
		}
	}
	
}
