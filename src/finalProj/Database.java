package finalProj;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Database {
    private static final String DATABASE_URL = "https://audioface-4c7b5.firebaseio.com/";
    private static DatabaseReference database;
    
    public static void addUser(String name, String password) {
    	User user = new User(name, password);
    	// Generates unique key for each new user
    	DatabaseReference newUserRef = database.child("users").push();
    	newUserRef.setValueAsync(user);	
    }
    
    public static void updateProfileInfo(String userId, String name, String password, String des, String dob) {
    	DatabaseReference userRef = database.child("users").child(userId);
    	
    	String dbName = userRef.child("name").toString();
    	String dbPassword = userRef.child("password").toString();
    	String dbDes = userRef.child("description").toString();
    	String dbDob = userRef.child("dob").toString();
    	
    	Map<String, Object> updates = new HashMap<>();
    	if (!dbName.contentEquals(name)) {
    		updates.put("name", name);
    	}
    	if (!dbPassword.contentEquals(password)) {
    		updates.put("password", password);
    	}
    	if (!dbDes.contentEquals(des)) {
    		updates.put("description", des);
    	}
    	if (!dbDob.contentEquals(dob)) {
    		updates.put("dob", dob);
    	}
    	
    	userRef.updateChildrenAsync(updates);
    }
    
	// Initialize the database
	public static void main(String[] args) {
		FileInputStream serviceAccount;
		String path = null;
		try {
			//path = new File("serviceAccountKey.json").getAbsolutePath();
			serviceAccount = new FileInputStream("serviceAccountKey.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
				    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
				    .setDatabaseUrl("https://audioface-4c7b5.firebaseio.com/")
				    .build();
			FirebaseApp.initializeApp();
			
			database = FirebaseDatabase.getInstance().getReference();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
