package leidercalvo.envio;

import java.io.Serializable;

public class Modelo implements Serializable{
	
	private String a, b, c;

	public Modelo(String a, String b, String c) {
		this.a=a;
		this.b=b;
		this.c=c;
	}
	
	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

}
