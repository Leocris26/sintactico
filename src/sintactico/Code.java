package sintactico;

import java.util.ArrayList;

public final class Code {
	public static ArrayList<Tokenzz> tokens = new ArrayList<Tokenzz>();
	public static boolean estado = true;
	
	public static void reset() {
		tokens = new ArrayList<Tokenzz>();
	}
	
	public static void push(Tokenzz z) {
		if(z.caract.equals("no def")) {
			System.out.println("error lexico:" +z.x+", "+z.y);
			estado=false;
		}else {
			tokens.add(z);
		}
	}
	
	public static void print() {
		for (int i = 0; i < tokens.size(); i++) {
			System.out.println(tokens.get(i).caract+": "+ tokens.get(i).cadena + " "+tokens.get(i).x+" "+tokens.get(i).y);
		}
	}
}
