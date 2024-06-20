package org.example.toylog.domain.series.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.toylog.domain.post.entity.Post;

@Entity
@Table(name = "series")
@Getter @Setter
@NoArgsConstructor
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "series_name", nullable = false)
    private String seriesName;

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;
}
