package sintactico;

import java.util.ArrayList;

public class Filter {
	public void filtrar(){
		ArrayList<Simbol> filtrado = new ArrayList<Simbol>();
		for (int i = 0; i < Code.tokens.size(); i++) {
			Tokenzz aux = Code.tokens.get(i);
			int id = SimbolTable.indice(aux.cadena);
			if(id >= 0) {
				Simbol fill = SimbolTable.table.get(id);
				filtrado.add(fill);
			}else {
				if(!aux.caract.equals("Keyword") && !aux.cadena.equals("(") && !aux.cadena.equals(")") && !aux.cadena.equals("{") && !aux.cadena.equals("}"))
					filtrado.add(new Simbol(aux.cadena,aux.cadena,aux.x,aux.y));
			}
		}
		for (int i = 0; i < filtrado.size(); i++) {
			//System.out.println("type: " + filtrado.get(i).type);
			if(filtrado.get(i).type.equals("==")||filtrado.get(i).type.equals("+")) {
				if(!filtrado.get(i-1).type.equals(filtrado.get(i+1).type)) {
					System.out.println("error: " + filtrado.get(i).x+" ,"+filtrado.get(i).y);
				}
			}else if (filtrado.get(i).type.equals("*")||filtrado.get(i).type.equals("/")||filtrado.get(i).type.equals("-")) {
				String tipo1 = filtrado.get(i-1).type;
				String tipo2 = filtrado.get(i+1).type;
				if(!(tipo1.equals(tipo2)&&(tipo1.equals("ent")||tipo1.equals("decimal"))))
					System.out.println("error: " + filtrado.get(i).x+" ,"+filtrado.get(i).y);
			}else if(filtrado.get(i).type.equals("=")) {
				if(!filtrado.get(i-1).type.equals(filtrado.get(i+1).type)) {
					System.out.println("error: " + filtrado.get(i).x+" ,"+filtrado.get(i).y);
				}
			}else if(filtrado.get(i).type.equals(">=")||filtrado.get(i).type.equals("<=")
					||filtrado.get(i).type.equals("<")||filtrado.get(i).type.equals(">")) {
				String tipo1 = filtrado.get(i-1).type;
				String tipo2 = filtrado.get(i+1).type;
				if(!(tipo1.equals(tipo2)&&(tipo1.equals("ent")||tipo1.equals("decimal"))))
					System.out.println("error: " + filtrado.get(i).x+" ,"+filtrado.get(i).y);
			}else if(filtrado.get(i).type.equals("++")) {
				if(!(filtrado.get(i-1).type.equals("ent")||filtrado.get(i-1).type.equals("decimal"))) {
					System.out.println("error: " + filtrado.get(i).x+" ,"+filtrado.get(i).y);
				}
			}
		}
	}
}
