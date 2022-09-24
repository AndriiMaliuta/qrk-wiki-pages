package com.andmal;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Uni;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pages")
@NamedQueries({
        @NamedQuery(name = "Page.getBySpace", query = "from Page where space_key = ?1")
})
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
    public static Uni<List<Page>> findBySpaceKey(String spaceKey) {
        return find("#Page.getBySpace", spaceKey).list();
    }
}
