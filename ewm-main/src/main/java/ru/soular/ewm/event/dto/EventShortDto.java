package ru.soular.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.soular.ewm.category.dto.CategoryDto;
import ru.soular.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EventShortDto {

    private Long id;
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Long views;
}
