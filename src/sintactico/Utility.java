package sintactico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utility {

     static Map<String,List<String>> map = new HashMap<String,List<String>>();
     static ArrayList<String> charList = new ArrayList<String>();
     static  ArrayList<String> terminalList = new ArrayList<>();
     static Map<String, ArrayList<String>> m = new HashMap<>();
     static Map<String, ArrayList<String>> follow = new HashMap<>();
    static ArrayList<String> firstSet(String pr)
    {
        ArrayList<String> tempFirst = new ArrayList<>();
        if(charList.contains(pr.split("\\s+")[0]))
        {
            int i,j;
            i=0;
            int flag=1;
            while(i<pr.split("\\s+").length && charList.contains(pr.split("\\s+")[i]) && flag==1)
            {
                String current = pr.split("\\s+")[i];
                ArrayList<String> first = m.get(current);
                flag=0;
                for(j=0;j<first.size();j++)
                {
                    if(!first.get(j).equals("e"))
                        tempFirst.add(first.get(j));
                    else
                        flag=1;
                }
                i++;
            }
            if(i == pr.split("\\s+").length && flag==1)
                tempFirst.add("e");
            else if(flag==1)
            {
                tempFirst.add(pr.split("\\s+")[i]);
            }

        }
        else
        {
            tempFirst.add(pr.split("\\s+")[0]);
        }
        return  tempFirst;
    }

    static ArrayList<String> followSet(String variable)
    {
        return follow.get(variable);
    }

    static void initialiseTerminals()
    {
        int i,j,k;
        for(i=0;i<charList.size();i++)
        {
            String currentAi = charList.get(i);
            List<String> productionAi = map.get(currentAi);
            for(j=0;j<productionAi.size();j++)
            {
                String currentClause =productionAi.get(j);
                for(k=0;k<currentClause.split("\\s+").length;k++)
                {
                    if(!charList.contains(currentClause.split("\\s+")[k]) && !terminalList.contains(currentClause.split("\\s+")[k]))
                    {
                        if(!(currentClause.split("\\s+")[k]).equals("e"))
                        terminalList.add(currentClause.split("\\s+")[k]);
                    }
                }
            }
        }
       // System.out.println(terminalList);
    }
}
