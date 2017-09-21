package org.midnightbsd.appstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Lucas Holt
 */
@Entity
@Table(name = "package")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Package implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = -1378527327884598865L;

    @Id
    @SequenceGenerator(name = "package_id_seq",
            sequenceName = "package_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "package_id_seq")
    @Column(name = "id", updatable = false)
    private int id;

    private String name;

    private String description;

    private String url;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "package_category_map", joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "package_id", referencedColumnName = "id"))
    private Set<Category> categories;

    @JsonManagedReference(value = "package-rating")
    @JsonProperty("ratings")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pkg", fetch = FetchType.EAGER)
    private Set<Rating> ratings;

    @JsonManagedReference(value = "package-instance")
    @JsonProperty("instances")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pkg", fetch = FetchType.EAGER)
    private Set<PackageInstance> instances;
}
