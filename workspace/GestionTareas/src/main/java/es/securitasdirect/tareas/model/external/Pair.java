package es.securitasdirect.tareas.model.external;

/**
 * Pareja de valores para combos
 */
public class Pair {

    private Integer id;
    private String value;

    public Pair(){

    }
    public Pair(Integer id, String value){
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Pair{");
        sb.append("id=").append(id);
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
