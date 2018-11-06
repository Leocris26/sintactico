package sintactico;

import java.util.ArrayList;
import java.util.Stack;

public class Verificador {

	public Ll1Matrix matriz;
	public Stack<Tokenzz> cadena;
	public Stack<Terminales> verificacion;
	public Verificador() {
	}
	
	public boolean verificar(ArrayList<Tokenzz> cadenap, Ll1Matrix matriz, Stack<Terminales> inicial) {
		cadena = new Stack<Tokenzz>();
		boolean estado = true;
		cadena.push(new Tokenzz(0,0,"$","cadena"));
		this.matriz = matriz;
		this.verificacion = inicial;
		for (int i = 0; i < cadenap.size(); i++) {
			cadena.push(cadenap.get(cadenap.size()-1-i));
		}
		while(!(cadena.peek().cadena.equals("$") && verificacion.peek().valor.equals("$")) && estado) {
			/*for (int i = 0; i < verificacion.size(); i++) {
				System.out.print(verificacion.get(i).valor);
			}
			System.out.println();
			for (int i = 0; i < cadena.size(); i++) {
				System.out.print(cadena.get(i).cadena);
			}
			System.out.println();*/
			if(verificacion.peek().tipo.equals("NT")) {
				int x = -1;
				int y = -1;
				for (int i = 0; i < matriz.noTerminales.length; i++) {
					if(verificacion.peek().valor.equals(matriz.noTerminales[i].valor)) {
						x = i;
						break;
					}
				}
				for (int i = 0; i < matriz.terminales.length; i++) {
					if(matriz.terminales[i].tipo.equals("cadena")) {
						if(matriz.terminales[i].valor.equals(cadena.peek().cadena)) {
							y = i;
							break;
						}
					}else {
						if(matriz.terminales[i].valor.equals(cadena.peek().caract)) {
							y = i;
							break;
						}
					}
				}
				if(x <0 && y<0) {
					System.out.println("error fatal");
					estado = false;
				}else {
					if(matriz.matrix[x][y].existe) {
						if(matriz.matrix[x][y].epsilon) {
							verificacion.pop();
						}else {
							int[] regla = matriz.matrix[x][y].regla;
							verificacion.pop();
							for (int i = 0; i < regla.length; i++) {
								if(regla[regla.length-1-i]>0) {
									verificacion.push(matriz.terminales[regla[regla.length-1-i]-1]);
								}else {
									verificacion.push(matriz.noTerminales[-regla[regla.length-1-i]-1]);
								}
							}
						}
					}
					else {
						System.out.println("error in "+ cadena.peek().x+", "+cadena.peek().y);
						estado = false;
					}
				}
			}else {
				Terminales a = verificacion.peek();
				Tokenzz b = cadena.peek();
				if(a.tipo.equals("cadena")) {
					if(a.valor.equals(b.cadena)) {
						verificacion.pop();
						cadena.pop();
					}else {
						System.out.println("error in "+b.x+", "+b.y);
						estado = false;
					}
				}else {
					if(a.valor.equals(b.caract)) {
						verificacion.pop();
						cadena.pop();
					}else {
						System.out.println("error in "+b.x+", "+b.y);
						estado = false;
					}
				}
			}
		}
		return estado;
	}
}
