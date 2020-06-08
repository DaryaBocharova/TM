package ru.bocharova.se.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Project implements Serializable {

//    public static final long serialVersionUID = 1;

    private String id = UUID.randomUUID().toString();

    private String name = "";

    private Date dateBegin;

    private Date dateEnd;

    private Date created = new Date();

//    private transient Integer test = 123;

    public void test() {
        System.out.println("HELLO");
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

}
