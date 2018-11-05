package sintactico;

import java.util.ArrayList;

public final class SimbolTable {
	
	public static boolean estado = true;
	public static String actual = "non";
	public static ArrayList<Simbol> table = new ArrayList<Simbol>();
	
	public static void add(Simbol b) {
		if(existe(b.value)&&b.type.equals("non")) {
			//aparicion.
		}else if(existe(b.value)){
			System.out.println("error semantico: "+b.x+", "+b.y);
			estado = false;
		}else {
			table.add(b);
		}
	}
	
	public static void printall() {
		for (int i = 0; i < table.size(); i++) {
			System.out.println("type: " + table.get(i).type+" value: "+table.get(i).value);
		}
	}
	
	public static boolean existe (String v) {
		for (int i = 0; i < table.size(); i++) {
			if(table.get(i).value.equals(v)) {
				return true;
			}
		}
		return false;
	}
}
