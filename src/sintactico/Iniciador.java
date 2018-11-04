package sintactico;

import java.io.*;
import java.util.*;

public class Iniciador {

	public Iniciador() {
		
	}
    public HashMap<String, HashMap<String, String>> gettable() {
        int i,j,n;
        ArrayList<String> production =new ArrayList<String>();
        Map<String,List<String>> map = new HashMap<String,List<String>>();
        ArrayList<String> charList = new ArrayList<String>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        n=0;
        if (scanner != null) {
            n= scanner.nextInt();
            scanner.nextLine();
        }
        for(i=0;i<n;i++)
        {
        	production.add(scanner.nextLine());
        	ArrayList<String> tempProduction = new ArrayList<String>();
        	String current = production.get(i);
        	String c = current.split("\\s+")[0];
        	charList.add(c);
        	StringBuilder stringBuilder = new StringBuilder();
        	j=2;
            while(j<current.split("\\s+").length)
            {
            	if(current.split("\\s+")[j].equals("orr")) 
            	{
            		tempProduction.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
            	}else
            	{
            		stringBuilder.append(current.split("\\s+")[j]+" ");
            	}
            	j++;
            }
            tempProduction.add(stringBuilder.toString());
            map.put(c,tempProduction);

        }
        /*System.out.println("Result");
        for(i=0;i<charList.size();i++)
        {
            System.out.print(charList.get(i)+"-> ");
            List<String> out = map.get(charList.get(i));
            for(j=0;j< out.size();j++)
            {
                System.out.print(out.get(j));
                if(j<out.size()-1)
                    System.out.print(" orr ");
            }
            System.out.println();
        }*/
        Utility.charList = charList;
        Utility.map = map;
        Utility.initialiseTerminals();
        FirstAndFollowSet Fs = new FirstAndFollowSet(map,charList);
        Fs.firstAndFollowSet();
        Map<String, ArrayList<String>> first = new HashMap<>();                                 // First Set
        Map<String, ArrayList<String>> follow = new HashMap<>();                                // Follow Set
        first = Fs.getFirst();
        follow = Fs.getFollow();
        /*System.out.println("First Set for each non terminal");
        for (i = 0; i < charList.size(); i++) {
            System.out.print("First(" + charList.get(i) + ") = ");
            System.out.println(first.get(charList.get(i)));
        }
        System.out.println("Follow Set for each non terminal");
        for(i=0;i<charList.size();i++)
        {
            System.out.print("Follow("+charList.get(i)+") = ");
            System.out.println(follow.get(charList.get(i)));
        }*/
        //LL1Check ll1Check = new LL1Check(charList,map);
        //if(ll1Check.isLL1())
        //{
            PredictiveParser predictiveParser = new PredictiveParser(charList,map);
            predictiveParser.computeParseTable();
            return predictiveParser.table;
        /*}
        else
        {
            System.out.println("Grammer is not LL1");
        }
        return null;*/

    }
}
