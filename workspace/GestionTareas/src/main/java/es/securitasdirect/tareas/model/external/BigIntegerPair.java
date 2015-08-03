package es.securitasdirect.tareas.model.external;

import java.math.BigInteger;

/**
 * Created by Javier Naval on 03/07/2015.
 */
public class BigIntegerPair {

    private BigInteger id;
    private String value;

    public BigIntegerPair(){

    }
    public BigIntegerPair(BigInteger id, String value){
        this.id = id;
        this.value = value;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
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
        final StringBuffer sb = new StringBuffer("BigIntegerPair{");
        sb.append("id=").append(id);
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
