package ru.soular.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.soular.ewm.user.dao.UserDAO;
import ru.soular.ewm.user.dto.UserDto;
import ru.soular.ewm.user.model.User;
import ru.soular.ewm.util.PaginationBuilder;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final ModelMapper mapper;

    @Override
    public UserDto create(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        log.info("Creating new user: data={}", userDto);

        return mapper.map(userDAO.save(user), UserDto.class);
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        User user = userDAO.findById(id).orElseThrow(EntityNotFoundException::new);

        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }

        if (userDto.getEmail() != null) {
            user.setEmail(user.getEmail());
        }

        log.info("Updating User ID:{} with new data={}", id, userDto);
        return mapper.map(userDAO.save(user), UserDto.class);
    }

    @Override
    public void delete(Long id) {
        log.info("Removing user with ID: {}", id);
        userDAO.deleteById(id);
    }

    @Override
    public List<UserDto> getAll(List<Long> ids, Integer from, Integer size) {
        List<User> result;

        if (ids != null && ids.size() > 0) {
            log.info("Getting info about users: [{}]", ids);
            result = userDAO.findAllByIdIn(ids, PaginationBuilder.build(from, size));
        } else {
            log.info("Getting info about all users");
            result = userDAO.findAll(PaginationBuilder.build(from, size)).getContent();
        }

        return result.stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
}
