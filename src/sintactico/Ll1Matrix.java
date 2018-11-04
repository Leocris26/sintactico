package sintactico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ll1Matrix {
	public Rule[][] matrix;
	public Terminales[] terminales;
	public Terminales[] noTerminales;
	public Map<String, Terminales> map;

	public Ll1Matrix(Terminales[] terminales, Terminales[] noTerminales, HashMap<String, Terminales> map) {
		matrix = new Rule[noTerminales.length][terminales.length];
		this.terminales = terminales;
		this.noTerminales = noTerminales;
		this.map = map;
	}

	public void fillmat(HashMap<String, HashMap<String, String>> table) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = new Rule(null, false, false);
			}
		}
		for (int i = 0; i < noTerminales.length; i++) {
			HashMap<String, String> rules = table.get(noTerminales[i].valor);
			ArrayList<String> analizarrr = new ArrayList<String>();
			for ( String key : rules.keySet() ) {
			    analizarrr.add( key );
			}
			String[] analizar = new String[analizarrr.size()];
			for (int j = 0; j < analizarrr.size(); j++) {
				analizar[j] = analizarrr.get(j);
			}
			for (int j = 0; j < analizar.length; j++) {
				boolean epsilon = false;
				Terminales objetivo;
				if (map.containsKey(analizar[j]))
					objetivo = map.get(analizar[j]);
				else
					objetivo = new Terminales(analizar[j], "cadena");
				String[] proximaRS = rules.get(analizar[j]).split("\\s+");
				int[] proximaRI = null;
				if (proximaRS[0].equals("e")) {
					epsilon = true;
				} else {
					proximaRI = new int[proximaRS.length];
					for (int k = 0; k < proximaRS.length; k++) {
						int val = 0;
						Terminales relleno;
						if (map.containsKey(proximaRS[k]))
							relleno = map.get(proximaRS[k]);
						else {
							for (int l = 0; l < noTerminales.length; l++) {
								if(noTerminales[l].valor.equals(proximaRS[k])) {
									val = -(l+1);
								}
							}
							relleno = new Terminales(proximaRS[k], "cadena");
						}
						if(val == 0) {
							for (int l = 0; l < terminales.length; l++) {
								if(terminales[l].valor.equals(relleno.valor) && terminales[l].tipo.equals(relleno.tipo)) {
									val = (l+1);
								}
							}
						}
						proximaRI[k] = val;
					}
				}
				int cory = -1;
				for (int k = 0; k < terminales.length; k++) {
					if(terminales[k].valor.equals(objetivo.valor) && terminales[k].tipo.equals(objetivo.tipo)) {
						cory = k;
					}
				}
				matrix[i][cory].existe = true;
				if(epsilon) {
					matrix[i][cory].epsilon = true;
				}else {
					matrix[i][cory].regla = proximaRI;
				}
			}
		}
		for (int i = 0; i < matrix.length; i++) {
			for (int k = 0; k < matrix[i].length; k++) {
				if(matrix[i][k].existe) {
					if(matrix[i][k].epsilon)
						System.out.print("e ");
					else {
						for (int k2 = 0; k2 < matrix[i][k].regla.length; k2++) {
							System.out.print(matrix[i][k].regla[k2]+",");
						}
						System.out.print(" ");
					}
				}
				else System.out.print(" - ");
			}
			System.out.println();
		}
	}
}
