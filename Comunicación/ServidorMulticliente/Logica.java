import java.util.Observable;
import java.util.Observer;

public class Logica implements Observer{

	private ControlServidor com;
	
	public Logica() {
		com = new ControlServidor();
		com.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		/*
		 * Esto paso en ControlServidor has algo
		 */
	}

}
