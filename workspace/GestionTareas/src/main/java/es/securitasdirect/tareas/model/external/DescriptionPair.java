package es.securitasdirect.tareas.model.external;

/**
 * Pareja de valores para combos con descripción.
 */
public class DescriptionPair extends StringPair {

    private String description;
    
    public DescriptionPair(){
    	super();
    }
    
	public DescriptionPair(String description) {
		super();
		this.description = description;
	}

	public DescriptionPair(DescriptionPair descriptionPair) {
		this.setDescription(descriptionPair.getDescription());
		this.setId(descriptionPair.getId());
		this.setValue(descriptionPair.getValue());
		// TODO Auto-generated constructor stub
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
