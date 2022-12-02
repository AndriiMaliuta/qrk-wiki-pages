package com.andmal;

import javax.json.bind.annotation.JsonbProperty;
import java.time.LocalDateTime;

public class CreatePage {
    public String title;
    public String body;
    @JsonbProperty(value = "space_key")
    public String spaceKey;
    @JsonbProperty(value = "author_id")
    public Long authorId;
    @JsonbProperty(value = "created_at")
    public LocalDateTime createdAt;
    @JsonbProperty(value = "last_updated")
    public LocalDateTime lastUpdated;
    @JsonbProperty(value = "parent_id")
    public Long parentId;
}
