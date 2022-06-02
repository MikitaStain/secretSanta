package com.innowise.secret_santa.mapper;

import com.innowise.secret_santa.model.Profile;
import com.innowise.secret_santa.model.dto.ProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "players", ignore = true)
    @Mapping(target = "id", ignore = true)
    Profile toProfile(ProfileDto profileDto);

    ProfileDto toProfileDto(Profile profile);
}
