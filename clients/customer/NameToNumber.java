package clients.customer;       ////////
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NameToNumber extends HashMap<String, String>{
	
	NameToNumber(){
		put("0001", "TV");
		put("0002", "Radio");
		put("0003", "Toaster");
		put("0004", "Watch");
		put("0005", "Camera");
		put("0006", "Music player");
		put("0007", "USB driver");
	}

	// Method to get a number by name with case-insensitive comparison
    public <T, E> T getNumberByName(Map<T, E> map, E value) {
        for (Entry<T, E> entry : map.entrySet()) {
            if (value instanceof String && entry.getValue() instanceof String) {
                // Perform a case-insensitive comparison
                if (((String) value).equalsIgnoreCase((String) entry.getValue())) {
                    return entry.getKey();
                }
            } else if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}