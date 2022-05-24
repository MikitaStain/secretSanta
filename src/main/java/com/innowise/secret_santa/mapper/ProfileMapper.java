package com.innowise.secret_santa.mapper;

import com.innowise.secret_santa.model.Profile;
import com.innowise.secret_santa.model.dto.ProfileDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    Profile toProfile(ProfileDto profileDto);

    ProfileDto toProfileDto(Profile profile);
}
