package sintactico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

import sintactico.Code;
import sintactico.MatrizDol;
import sintactico.Token;

public class Principal {

	public static void main(String[] args) throws IOException {
		Code.reset();
		FileReader in = new FileReader("xample.txt");
		BufferedReader br = new BufferedReader(in);
		br.mark(10000);
		String linea;
		while ((linea=br.readLine()) != null) {
			System.out.println(linea);
		}
		System.out.println();
		br.reset();
		int f=1;
		int facum =0;
		int c;
		int cward=0;
		String palabra="";
		Token tok;
		boolean scom = false;
		boolean tcom = false;
		boolean sc = false;
		boolean tc = false;
		//palabra = "0123456789    bBoOxX";
		//for (int i = 0; i < palabra.length(); i++) {
		//	System.out.print(palabra.codePointAt(i)+" ");
		//}
		//System.out.println();
		while ((linea=br.readLine()) != null) {
			c=0;
			int mat;
			if(!tcom &&!tc)palabra = "";
			for (int i = 0; i < linea.length(); i++) {
				if(!scom && !tcom && !sc && !tc){
					char auxxx = ' ';
					if(i!=0)auxxx = linea.charAt(i-1);
					if(linea.length()-i>2)mat=MatrizDol.verificar(auxxx,linea.substring(i,i+3),f-facum,i+1,c,palabra);
					else if(linea.length()-i>1)mat=MatrizDol.verificar(auxxx,linea.substring(i,i+2),f-facum,i+1,c,palabra);
					else mat=MatrizDol.verificar(auxxx,linea.substring(i,i+1),f-facum,i+1,c,palabra);
					if(linea.codePointAt(i)==35)break;
					if(linea.codePointAt(i)==32 && palabra!=""){
						tok = new Token(f-facum,i+1-c,palabra,true,0);
						palabra = "";
						tok.verificar();
						c=0;
					}else if(linea.codePointAt(i)==32){}
					else if(mat>0){
						c=0;
						palabra = "";
						switch(mat){
						case 1:
							break;
						case 2:
							i++;
							break;
						case 3:
							i+=2;
							break;
						}
					}
					else{
						if(linea.codePointAt(i)==34 && linea.length()-i>2 && 
							linea.codePointAt(i+1)==34 && linea.codePointAt(i+2)==34)tcom = true;
						else if(linea.codePointAt(i)==39 && linea.length()-i>2 && 
								linea.codePointAt(i+1)==39 && linea.codePointAt(i+2)==39)tc = true;
						else if(linea.codePointAt(i)==34)scom = true;
						else if(linea.codePointAt(i)==39)sc = true;
						palabra = palabra + linea.substring(i,i+1);
						c++;
						if(tcom || tc){
							palabra = palabra + linea.substring(i+1,i+2) + linea.substring(i+2,i+3);
							cward=i-c+2;
							c+=2;
							i+=2;
						}
					}
				}else{
					if(scom){
						if(linea.codePointAt(i)==34 && linea.codePointAt(i-1)!=92){
							tok = new Token(f-facum,i+1-c,palabra+linea.substring(i,i+1),true,1);
							scom = false;
							palabra = "";
							tok.verificar();
							c=0;
						}else{
							palabra = palabra + linea.substring(i,i+1);
							c++;
						}
					}else if(sc){
						if(linea.codePointAt(i)==39 && linea.codePointAt(i-1)!=92){
							tok = new Token(f-facum,i+1-c,palabra+linea.substring(i,i+1),true,2);
							sc = false;
							palabra = "";
							tok.verificar();
							c=0;
						}else{
							palabra = palabra + linea.substring(i,i+1);
							c++;
						}
					}else if(tcom){
						if(linea.codePointAt(i)==34 && (i==0 || (linea.codePointAt(i-1)!=92)) && 
								linea.length()-i>2 && linea.codePointAt(i+1)==34 && linea.codePointAt(i+2)==34){
							tok = new Token(f-facum,cward,palabra+linea.substring(i,i+1)+linea.substring(i+1,i+2)+linea.substring(i+2,i+3),true,3);
							tcom = false;
							palabra = "";
							tok.verificar();
							c=0;
							facum =0;
							i+=2;
						}else{
							palabra = palabra + linea.substring(i,i+1);
							c++;
						}
					}else{
						if(linea.codePointAt(i)==39 && (i==0 || (linea.codePointAt(i-1)!=92)) && 
								linea.length()-i>2 && linea.codePointAt(i+1)==39 && linea.codePointAt(i+2)==39){
							tok = new Token(f-facum,cward,palabra+linea.substring(i,i+1)+linea.substring(i+1,i+2)+linea.substring(i+2,i+3),true,4);
							tc = false;
							palabra = "";
							tok.verificar();
							c=0;
							facum =0;
							i+=2;
						}else{
							palabra = palabra + linea.substring(i,i+1);
							c++;
						}
					}
				}
			}
			if(scom || sc){
				tok = new Token(f,linea.length()+1-c,palabra,false,0);
				palabra = "";
				tok.verificar();
				scom = sc = false;
			}else if(tcom || tc){
				palabra = palabra + "\n";
				facum++;
			}else if(palabra!=""){
				tok = new Token(f-facum,linea.length()+1-c,palabra,true,0);
				palabra = "";
				tok.verificar();
			}
			f++;
		}
		if(tcom || tc){
			tok = new Token(f-facum,cward,palabra,false,0);
			tok.verificar();
		}
		in.close();
		Code.print();
		SimbolTable.printall();
		//hasta aqui el lexico
		if(SimbolTable.estado && Code.estado) {
		Terminales[] terminales = {new Terminales(";","cadena"),new Terminales("(","cadena"),new Terminales(")","cadena"),
				new Terminales("{","cadena"),new Terminales("}","cadena"),new Terminales("[","cadena"),new Terminales("]","cadena"),
				new Terminales(",","cadena"),new Terminales("=","cadena"),new Terminales("si","cadena"),new Terminales("mientras","cadena"),
				new Terminales("para","cadena"),new Terminales("sino","cadena"),new Terminales("osi","cadena"),
				new Terminales(".","cadena"),new Terminales("*","cadena"),new Terminales("/","cadena"),new Terminales("+","cadena"),
				new Terminales("-","cadena"),new Terminales("++","cadena"),new Terminales("==","cadena"),new Terminales(">=","cadena"),
				new Terminales("<=","cadena"),new Terminales("<","cadena"),new Terminales(">","cadena"),new Terminales("|","cadena"),
				new Terminales("&","cadena"),new Terminales("Identifier","caract"),new Terminales("ent","cadena"),new Terminales("cadena","cadena"),
				new Terminales("marwin","cadena"),new Terminales("decimal","cadena"),new Terminales("Integer","caract"),
				new Terminales("float","caract"),new Terminales("string","caract"),new Terminales("verdadero","cadena"),
				new Terminales("falso","cadena"),new Terminales("$","cadena")};
		Terminales[] noterminales = {new Terminales("S","NT"),new Terminales("C","NT"),new Terminales("Y","NT"),
				new Terminales("M","NT"),new Terminales("I","NT"),new Terminales("A","NT"),new Terminales("K","NT"),
				new Terminales("J","NT"),new Terminales("Ñ","NT"),new Terminales("B","NT"),new Terminales("D","NT"),
				new Terminales("G","NT"),new Terminales("Z","NT"),new Terminales("W","NT"),new Terminales("T","NT"),
				new Terminales("H","NT"),new Terminales("O","NT"),new Terminales("Q","NT"),new Terminales("QQ","NT"),
				new Terminales("V","NT"),new Terminales("N","NT"),new Terminales("Q1","NT"),new Terminales("Q2","NT"),
				new Terminales("Q3","NT"),new Terminales("Q4","NT"),new Terminales("MC","NT")};
		Iniciador init = new Iniciador();
		HashMap<String, HashMap<String, String>> table = init.gettable();
		HashMap<String, Terminales> map = new HashMap<String, Terminales>();
		Scanner scanner2 = null;
		try {
			scanner2 = new Scanner(new File("trans.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int n = scanner2.nextInt();
        scanner2.nextLine();
        for(int i=0;i<n;i++)
        {
        	String reghash = scanner2.nextLine();
        	String [] obj = reghash.split("\\s+");
        	Terminales res = new Terminales (obj[1],obj[2]);
        	map.put(obj[0], res);
        }
		Ll1Matrix matriz = new Ll1Matrix(terminales,noterminales,map);
		matriz.fillmat(table);
		Stack<Terminales> inicial=new Stack<Terminales>();
		inicial.push(new Terminales("$","cadena"));
		inicial.push(new Terminales("S","NT"));
		ArrayList<Tokenzz> cadenap = Code.tokens;
		/*cadenap.add(new Tokenzz(0,1,"marwin","Keyword"));
		cadenap.add(new Tokenzz(0,2,"a","Identifier"));
		cadenap.add(new Tokenzz(0,3,"=","delimiter"));
		cadenap.add(new Tokenzz(0,4,"verdadero","Keyword"));
		cadenap.add(new Tokenzz(0,4,";","delimiter"));
		cadenap.add(new Tokenzz(0,5,"hhh","Identifier"));
		cadenap.add(new Tokenzz(0,6,"(","delimiter"));
		cadenap.add(new Tokenzz(0,7,")","delimiter"));
		cadenap.add(new Tokenzz(0,8,"{","delimiter"));
		cadenap.add(new Tokenzz(0,9,"a","Identifier"));
		cadenap.add(new Tokenzz(0,10,"=","delimiter"));
		cadenap.add(new Tokenzz(0,11,"falso","Keyword"));
		cadenap.add(new Tokenzz(0,12,";","delimiter"));
		cadenap.add(new Tokenzz(0,13,"}","delimiter"));
		//cadenap.add(new Tokenzz(0,8,")","delimiter"));*/
		Verificador prueba = new Verificador();
		System.out.println(prueba.verificar(cadenap, matriz, inicial));
		
		
		/*inicial=new Stack<Terminales>();
		inicial.push(new Terminales("$","cadena"));
		inicial.push(new Terminales("S","NT"));
		cadenap = new ArrayList<Tokenzz>();
		cadenap.add(new Tokenzz(0,0,"(","delimiter"));
		cadenap.add(new Tokenzz(0,0,"(","delimiter"));
		cadenap.add(new Tokenzz(0,0,"123","x"));
		cadenap.add(new Tokenzz(0,0,")","delimiter"));
		cadenap.add(new Tokenzz(0,0,";","delimiter"));
		cadenap.add(new Tokenzz(0,0,"123","x"));
		cadenap.add(new Tokenzz(0,0,")","delimiter"));
		prueba = new Verificador();
		System.out.println(prueba.verificar(cadenap, matriz, inicial));*/
		}
	}

}
