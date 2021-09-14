package config;

import java.io.Serializable;

public abstract class AbstractConfig implements Serializable {

    private static final long serialVersionUID = 5612790303490609282L;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
