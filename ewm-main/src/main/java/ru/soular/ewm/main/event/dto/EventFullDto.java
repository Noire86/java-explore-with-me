package ru.soular.ewm.main.event.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.soular.ewm.main.category.dto.CategoryDto;
import ru.soular.ewm.main.comment.dto.CommentDto;
import ru.soular.ewm.main.event.model.Location;
import ru.soular.ewm.main.user.dto.UserShortDto;
import ru.soular.ewm.main.util.EventState;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ДТО с полной информацией о событии, включая просмотры и комменты
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EventFullDto {

    private Long id;
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private String description;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private Boolean commentModeration;
    private EventState state;
    private String title;
    private Long views;
    private List<CommentDto> comments;
}
