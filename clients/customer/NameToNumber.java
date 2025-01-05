package clients.customer;       ////////
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.JOptionPane;

public class NameToNumber extends HashMap<String, String>{
	
	public NameToNumber(){
		put("0001", "TV");
		put("0002", "Radio");
		put("0003", "Toaster");
		put("0004", "Watch");
		put("0005", "Camera");
		put("0006", "Music player");
		put("0007", "USB driver");
	}
    
	
	//method to get a number by name with case-insensitive comparison
    public <T, E> T getNumberByName(Map<T, E> map, E value) {
    	for (Entry<T, E> entry : map.entrySet()) {
            //perform a case-insensitive comparison if value is a String
            if (value instanceof String && entry.getValue() instanceof String) {
                if (((String) value).equalsIgnoreCase((String) entry.getValue())) {
                    return entry.getKey();  // Return the matching key (ID)
                }
            } 
            // Handle other types, e.g., non-string values
            else if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }

		
		  // Only show error message if no match was found
		  JOptionPane.showMessageDialog(null, "Error: The product '" + value +
		  "' does not exist. Please check the spelling or ID.", "Invalid Input",
		  JOptionPane.ERROR_MESSAGE);
		 

        return null;  // Return null when no match is found
    }
}