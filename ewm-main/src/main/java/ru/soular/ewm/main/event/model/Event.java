package ru.soular.ewm.main.event.model;

import lombok.Getter;
import lombok.Setter;
import ru.soular.ewm.main.category.model.Category;
import ru.soular.ewm.main.compilation.model.Compilation;
import ru.soular.ewm.main.user.model.User;
import ru.soular.ewm.main.util.EventState;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Модель события
 */
@Entity
@Getter
@Setter
@Table(name = "events", schema = "public")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String title;

    @Column
    @NotBlank
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private String description;

    @Column(name = "date_event")
    private LocalDateTime eventDate;

    @Column(name = "created")
    private LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @Column
    private Boolean paid;

    @Column(name = "participant_limit")
    @PositiveOrZero
    private Long participantLimit;

    @Column(name = "published")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Column(name = "comment_moderation")
    private Boolean commentModeration;

    @Column
    @Enumerated(EnumType.STRING)
    private EventState state;

    @Column(name = "location_lat")
    private Double locationLat;

    @Column(name = "location_lon")
    private Double locationLon;

    @ManyToMany(mappedBy = "events")
    private List<Compilation> compilations;
}
