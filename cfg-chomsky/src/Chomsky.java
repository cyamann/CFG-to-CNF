import java.util.*;
import java.util.Map.Entry;

public class Chomsky {
	private ArrayList<String> arr;
	private ArrayList<String> arr2;
	private ArrayList<String> allTerminals;
	private String[] names;
	private int nameCounter = 0;
    Chomsky(Map<String, List<String>> map){
        redesignMap(map);
    }
     private void eliminateEpsilon(Map<String, List<String>> map){
    	 //initializing variables
    	 allTerminals = new ArrayList<>();
    	 arr = new ArrayList<>();
    	 arr2 = new ArrayList<>();
    	 names = new String[8];
    	 names[0] = "X";
    	 names[1] = "Y";
    	 names[2] = "Z";
    	 names[3] = "K";
    	 names[4] = "L";
    	 names[5] = "M";
    	 names[6] = "N";
    	 names[7] = "X";
         for(Map.Entry m:map.entrySet()){
        	 allTerminals.add((String) m.getKey());
             if(!m.getKey().equals("E") && !m.getKey().equals("S") && !m.getKey().equals("S0")){ 
                 arr.add(String.valueOf(m.getKey()));//adding non terminal keys
             }
             else {
            	 arr2.add(String.valueOf(m.getKey()));
             }
         }
        System.out.println("\nEliminate epsilon");
        for(Map.Entry m:map.entrySet()){
            List<String> list = (List) m.getValue();
            for(int a = 0; a < list.size(); a++){
                if(list.get(a).equals("â‚¬")){
                    list.remove(a);
                }
            }
        }
      
        showArray(map);
        eliminateUnitProduction(map);
    }
    private void eliminateUnitProduction(Map<String, List<String>> map){
        System.out.println("\nEliminate unit production");
      //divide 3 sized strings
        boolean flag = false;
        List<String> keysToRemove = new ArrayList<>();
        Map<String, List<String>> keysToAdd = new HashMap<>();
        ArrayList<String> newList = new ArrayList<>();
        for(Map.Entry m:map.entrySet()){
            List<String> list = (List) m.getValue();
            ArrayList<String> aList = new ArrayList<>();
            for(int a = 0; a < list.size(); a++){
                String st = list.get(a);
                if(st.length()>2) {
                	int x = 0;
                	flag = true;
                	for(int b = 0; b < st.length(); b++) {
                		aList.add(String.valueOf(st.charAt(b)));
                		String ss = String.valueOf(st.charAt(x)) + String.valueOf(st.charAt(b));
                		aList.add(ss);
                		if(b + 1 == st.length()) {
                			x++;
                			b = 0;
                		}
                		if(x + 1 == st.length())
                			break;
                			
                	}
                }
            }
            if(flag) {
            	flag = false;
            	newList = new ArrayList<>();
                for (String element : aList) {
                    if (!newList.contains(element)  ) {
                    	if(element.length() == 2 && !String.valueOf(element.charAt(0)).equalsIgnoreCase(String.valueOf(element.charAt(1))))
                    		newList.add(element);
                    	if(!allTerminals.contains(element) && element.length() == 1)
                    		newList.add(element);
                    }
                }
                keysToRemove.add((String) m.getKey());
                for(int c = 0; c < list.size(); c++) {
                	if(!newList.contains(list.get(c)))
                		newList.add(list.get(c));
                }
                keysToAdd.put((String) m.getKey(), (List)newList);
            }
            else {
            	newList = new ArrayList<>();
            	for (String element : aList) {
                    if (!newList.contains(element)  ) {
                    		newList.add(element);
                    }
                }
            }
        }
        for(Map.Entry m1:keysToAdd.entrySet()) {
        	 for(Map.Entry m:map.entrySet()){
        		 if(m.getKey().equals(m1.getKey())) {
        			 m.setValue(m1.getValue());
        		 }
        	 } 
        }
        
        showArray(map);        
        eliminateTerminals(map);
        
    }
    private void eliminateTerminals(Map<String, List<String>> map){
        System.out.println("\nEliminate Terminals");
        //firstly remove terminals which are in alphabeth
        for(Map.Entry m:map.entrySet()) {
        	List list = (List) m.getValue();
        	if(arr.contains(m.getKey())){
        		for(int j = 0; j < list.size(); j++) {
        			for(int i = 0; i < arr.size(); i++) {
              			 if(list.get(j).equals(arr.get(i))) {
              				 list.remove(arr.get(i));
              				List<String> list2 = map.get(arr.get(i));
                  			for (String element : list2) {
                                  if (!list.contains(element)  ) {
                                  		list.add(element);
                                  }
                              }
              			}
              		}
        		}
        	}
        }
        //remove terminals that aren't in alphabeth except alphabeth
        for(Map.Entry m:map.entrySet()) {
        	List list = (List) m.getValue();
        	//if(arr2.contains(m.getKey())){
        		for(int j = 0; j < list.size(); j++) {
        			for(int i = 0; i < arr2.size(); i++) {
              			 if(!m.getKey().equals("E") && list.get(j).equals(arr2.get(i))) {
              				 list.remove(arr2.get(i));
              				List<String> list2 = map.get(arr2.get(i));
                  			for (String element : list2) {
                                  if (!list.contains(element)  ) {
                                  		list.add(element);
                                  }
                              }
              			}
              			if(!m.getKey().equals("E") &&arr.contains(list.get(j))) {
            				list.remove(list.get(j));
            			}
              		}
        		}
        	//}
        }
        //rechecking alphabetic and non-alphabethic terminals
        for(Map.Entry m:map.entrySet()) {
        	List list = (List) m.getValue();
        	if(arr.contains(m.getKey())) {// A B
        		for(int i = 0; i < list.size(); i++) {
        			for(int j = 0; j < arr2.size(); j++) {
        				if(list.get(i).equals(arr2.get(j))) {
        					 list.remove(arr2.get(i));
               				List<String> list2 = map.get(arr2.get(i));
                   			for (String element : list2) {
                                   if (!list.contains(element)  ) {
                                   		list.add(element);
                                   }
                               }
        				}
        			}
        		}
        	}
        	else if(arr2.contains(m.getKey())){// s s0
        		for(int i = 0; i < list.size(); i++) {
        			for(int j = 0; j < allTerminals.size(); j++) {
        				if(!m.getKey().equals("E") && list.get(i).equals(allTerminals.get(j))) {
        					list.remove(allTerminals.get(j));
        				}
        			}
        		}
        		
        	}
        }
        //removing non-alphabethic VARIABLES
        Map<String, List<String>> foreign = new HashMap<>();
        for(Map.Entry m:map.entrySet()) {
        	List<String> list = (List) m.getValue();
        	ArrayList<String> array = new ArrayList<>();
        	for(int i = 0; i < list.size(); i++) {
        		String word = (String) list.get(i);
        		int counter = 0;
        		for(int j = 0; j < word.length(); j++) {
        			if(!contains(allTerminals, String.valueOf(word.charAt(j)))) {
        				array.add(String.valueOf(word.charAt(j)));
        			}
        		}
        		foreign.put((String) m.getKey(),array);
        	}
        }
        for(Map.Entry m:foreign.entrySet()) {
        	List<String> list = map.get(m.getKey());
        	List<String> list2 = (List<String>) m.getValue();
        	for(int i = 0; i < list2.size(); i++) {
        		if(!list.contains(list2.get(i)))
        			list.add(list2.get(i));
        	}
        	map.remove(m.getKey());
        	map.put((String)m.getKey(), list);
        }
        /**
         * 
         * alfabenin içindeki non alphabeth deðerleri sil ve bitir canýms
         * 
         * 
         */
        showMap(map);
        breakStrings(map);

    }
    private boolean contains(ArrayList<String> arr,String item) {
    	boolean flag = false;
    	for(int i = 0; i < arr.size(); i++) {
    		if(arr.get(i).equals(item)) {
    			flag = true;
    			break;
    		}
    	}
    		return flag;
    }
    private void breakStrings(Map<String, List<String>> map){
    	System.out.println("\nBreak variable strings longer than 2");
    	boolean flag = true;
    	boolean control = true;
    	Map<String, List<String>> keysToAdd = new HashMap<>();
    	while(flag) {
    		for(Map.Entry m:map.entrySet()){  
        		control = true;
                List<String> list = (List<String>) m.getValue();
                for(int i = 0; i < list.size(); i++) {
                	if(list.get(i).length()>2) {
                		String newName = String.valueOf(list.get(i).charAt(0)) + String.valueOf(list.get(i).charAt(1));
                		modify(map,newName,names[nameCounter]);
                		List<String> newList = new LinkedList<>();
                		newList.add(newName);
                		keysToAdd.put(names[nameCounter], newList);
                		allTerminals.add(names[nameCounter]);
                		nameCounter++;
                		if(list.get(i).length() - 1 > 2) {
                			control = false;
                		}
                	}
                }
            }
    		flag = !(control);
    	}
    	for(Map.Entry m:keysToAdd.entrySet()){  
    		map.put((String)m.getKey(),(List) m.getValue());
    	}

    	showMap(map);
    }
    private void modify(Map<String, List<String>> map,String newName, String name) {
    	for(Map.Entry m:map.entrySet()){  
    		List<String> list = (List<String>) m.getValue();
    		for(int i = 0; i < list.size(); i++) {
    			String word = list.get(i);
    			if(word.contains(newName)) {				
    				word = word.replace(newName, name);
    				list.set(i, word);
    			}
    		}
    	}
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
    private void showMap(Map<String, List<String>> map) {
    	for(int i = 0; i < allTerminals.size(); i++){
    		for(Map.Entry m:map.entrySet()){
    			if(allTerminals.get(i).equals(m.getKey())) {
    				System.out.println(m.getKey()+"-"+m.getValue());  
    			}
    		}
    	}
    }
    private void showArray(Map<String, List<String>> map){
        for(Map.Entry m:map.entrySet()){  
            System.out.println(m.getKey()+"-"+m.getValue());  
           }
    }
}
