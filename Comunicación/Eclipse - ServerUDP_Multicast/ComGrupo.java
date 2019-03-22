import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class ComGrupo extends Thread {

	private InetAddress group;
	private MulticastSocket s;

	public ComGrupo() {
		try {
			group = InetAddress.getByName("228.5.7.7");
			s = new MulticastSocket(6789);
			s.joinGroup(group);
		} catch (Exception e) {

		}
	}

	public void enviarMensaje(String mensaje) {
		String msg = mensaje;
		DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(), group, 6789);
		try {
			s.send(hi);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void recibirMensaje() {
		byte[] buf = new byte[1000];
		DatagramPacket recv = new DatagramPacket(buf, buf.length);

		try {
			s.receive(recv);
			// InetAddress adress = InetAddress.getByName(recv.getAddress());
			//System.out.println(new String(recv.getData(), 0, recv.getLength()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true) {
			recibirMensaje();
			try {
				sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	public String getDato() {
		return dato;
	}
	*/
}
