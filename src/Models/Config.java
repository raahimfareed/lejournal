package Models;

import jakarta.persistence.*;

@Entity
@Table(name = "configs")
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "config_key", unique = true)
    private String key;

    private String value;

    public Config() {}

    public Config(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Config{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
