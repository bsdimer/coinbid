package com.madbid.notification.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by nikolov.
 */
@Entity
@Table(name = "notification_templates")
public class NotificationTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Lob
    @Column(name = "template")
    private String template;

    @OneToMany(mappedBy = "notificationTemplate", fetch = FetchType.EAGER)
    private List<PlaceholderReference> placeholderReferences;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationTemplate that = (NotificationTemplate) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!template.equals(that.template)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + template.hashCode();
        return result;
    }

    public List<PlaceholderReference> getPlaceholderReferences() {
        return placeholderReferences;
    }

    public void setPlaceholderReferences(List<PlaceholderReference> placeholderReferences) {
        this.placeholderReferences = placeholderReferences;
    }
}
