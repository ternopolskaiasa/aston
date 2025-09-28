package org.example;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true);

        TypeMap<User, UserResponseDto> userToDto = mapper.createTypeMap(User.class, UserResponseDto.class);
        TypeMap<UserRequestDto, User> dtoToUser = mapper.createTypeMap(UserRequestDto.class, User.class);

        userToDto.addMapping(User::getId, UserResponseDto::setId);
        userToDto.addMapping(User::getName, UserResponseDto::setName);
        userToDto.addMapping(User::getEmail, UserResponseDto::setEmail);

        dtoToUser.addMapping(UserRequestDto::getName, User::setName);
        dtoToUser.addMapping(UserRequestDto::getEmail, User::setEmail);

        return mapper;
    }
}
