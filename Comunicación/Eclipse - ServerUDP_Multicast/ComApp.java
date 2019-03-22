import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ComApp extends Thread {

	DatagramSocket socket;
	String dato;
	private static ComGrupo comGrup;

	public ComApp() {
		dato = new String();
		try {
			socket = new DatagramSocket(5000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		comGrup = new ComGrupo();
		comGrup.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				byte[] buf = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);

				System.out.println("esperando");

				socket.receive(packet);
				
				dato = new String(packet.getData());
				dato = dato.trim();
				comGrup.enviarMensaje(dato);
				
				System.out.println("recibí: " + dato);
				
				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}


		}
	}

	public String getDato() {
		return dato;
	}
}
