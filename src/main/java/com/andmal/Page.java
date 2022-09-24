package com.andmal;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pages")
public class Page extends PanacheEntity {
    public String title;
    public String body;
    @Column(name = "space_key")
    public String spaceKey;
    @Column(name = "author_id")
    public String authorId;
    @Column(name = "created_at")
    public String createdAt;
    @Column(name = "last_updated")
    public String lastUpdated;
    @Column(name = "parent_id")
    public String parentId;
}
