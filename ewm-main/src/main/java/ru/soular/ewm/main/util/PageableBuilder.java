package ru.soular.ewm.main.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableBuilder {

    public static Pageable build(Integer from, Integer size) {
        return build(from, size, Sort.unsorted());
    }

    public static Pageable build(Integer from, Integer size, Sort sort) {
        if (from != null && size != null) {
            return PageRequest.of(from / size, size, sort);
        }

        return Pageable.unpaged();
    }
}
