package ie.gmit.mypackage;

import java.io.Serializable;
import java.lang.reflect.Field;

public class Phone implements Serializable {
	
	// Add for serialization
	private static final long serialVersionUID = 1L;
	
	//Instance Variables
	private String id;
	private String title;
	private int releaseYear;
	private float price;
	
	// Constructor
	public Phone(String id) {
		this.id = id;
	}
	
	public Phone(String id, String title, float price) {
		this(id);
		this.title = title;
		this.price = price;		
	}

	public Phone(String id, String title, float price, int releaseYear) {
		this(id, title, price);
		this.releaseYear = releaseYear;
	}
	
	// Getters and Setters
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}
	
	public String findAllFieldValuesInCSVFormat() {
		StringBuilder listOfFields = new StringBuilder();
		
		
		// determine fields declared in this class only (no fields of superclass)
		Field[] fields = this.getClass().getDeclaredFields();

		// print field names
		for (Field field : fields) {	
		try {
			// discard static fields
			if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
				
				//requires access to private field:
				listOfFields.append( field.get(this) );
				listOfFields.append(",");
		    }	
				// requires access to private field:				
			} catch (IllegalAccessException ex) {
				System.out.println(ex);
			}
			
		}
		
		// Remove comma at end
		if( listOfFields.length() > 0 ) {
			listOfFields.setLength( listOfFields.length() - 1 );			
		}

		return listOfFields.toString();
	}
	
}