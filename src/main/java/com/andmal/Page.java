package com.andmal;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Page extends PanacheEntity {
    public String title;
    public String body;
}
