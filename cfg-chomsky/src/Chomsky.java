import java.util.*;

public class Chomsky {
    Chomsky(Map<String, List<String>> map){
        redesignMap(map);
        eliminateTerminals();
        breakStrings();
    }
     private void eliminateEpsilon(Map<String, List<String>> map){
        System.out.println("CFG FORM\n");
        System.out.println("Eliminate €");
        for(Map.Entry m:map.entrySet()){
            List<String> list = (List) m.getValue();
            for(int a = 0; a < list.size(); a++){
                if(list.get(a).equals("€")){
                    list.remove(a);
                }
            }

        }
        showArray(map);
        eliminateUnitProduction(map);
    }
    private void eliminateUnitProduction(Map<String, List<String>> map){//A'yı B'yi diğer variableda açık açık yazmak
        System.out.println("\nEliminate unit production");
        String[] arr = new String[10];
        int i = 0;
        for(Map.Entry m:map.entrySet()){
            if(!m.getKey().equals("E") || !m.getKey().equals("S") || !m.getKey().equals("S0")){
                
                arr[i] = String.valueOf(m.getKey());//adding non terminal keys
                System.out.println(arr[i]);
                i++;
            }
        }
        for(Map.Entry m1:map.entrySet()){
            if(!m1.getKey().equals("E") || !m1.getKey().equals("S") || !m1.getKey().equals("S0")){
                List<String> list = (List) m1.getValue();
                for(int a = 0; a < list.size(); a++){
                    for(int b = 0; b < arr.length; b++){
                        if(list.get(a).equals(arr[b])){
                            List<String> list2 = (List) map.get(arr[b]);
                            map.remove(arr[b]);
                            for(int c = 0; c < list2.size(); c++){
                                list.add(list2.get(c));
                            }
                            map.put(String.valueOf(m1.getKey()), list);
                        }
                    }
                }
            }
        }
        showArray(map);
    }
    private void eliminateTerminals(){

    }
    private void breakStrings(){
        
    }
    private void redesignMap(Map<String, List<String>> map){
        List list = new LinkedList<>();
        list.add("S");
        Map<String, List<String>> map1 = new LinkedHashMap<>();
        map1.put("S0", list);
        for(Map.Entry m:map.entrySet()){
            map1.put(String.valueOf(m.getKey()), (List) m.getValue());
        }
        map = map1;
        eliminateEpsilon(map);
    }
    private void showArray(Map<String, List<String>> map){
        for(Map.Entry m:map.entrySet()){  
            System.out.println(m.getKey()+"-"+m.getValue());  
           }
    }
}
