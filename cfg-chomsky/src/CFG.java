import java.io.*;
import java.util.*;

public class CFG {
    Chomsky ch;
    Map<String, List<String>> map = new LinkedHashMap<>();
    CFG() throws Exception{
        //reading text file "cfg.txt"
        readFile();
        //send it to the chomsky class
    }
    private void readFile() throws Exception{
    	
        File file = new File("D:\\cfg1.txt");
        if (!file.canRead()) {
        	   file.setReadable(true);
        	}
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        int i = 0;
        while ((st = br.readLine()) != null){
            List<String> list = new LinkedList<>();
            if(i == 0){
                String[] str = st.split("=");
                if(str.length > 1){
                    String[] stri = str[1].split(",");
                    for(int a = 0; a < stri.length; a++){
                        list.add(stri[a]);
                    }
                    map.put(str[0], list);
                }
                
            }
            else{
                String[] str = st.split("-");
                String[] stri = str[1].split("\\|");
                for(int a = 0; a < stri.length; a++){
                    list.add(stri[a]);
                }
                map.put(str[0], list);
            }
            i++;
        }
        System.out.println("CFG FORM");
        for(Map.Entry m:map.entrySet()){  
            System.out.println(m.getKey()+"-"+m.getValue());  
           }
        ch = new Chomsky(map);
        br.close();
    }
}
