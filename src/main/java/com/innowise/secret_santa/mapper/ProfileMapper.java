package com.innowise.secret_santa.mapper;

import com.innowise.secret_santa.model.postgres.Profile;
import com.innowise.secret_santa.model.dto.ProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "players", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address.id", ignore = true)
    @Mapping(target = "account.password", ignore = true)
    @Mapping(target = "account.dateCreated", ignore = true)
    @Mapping(target = "account.role", ignore = true)
    @Mapping(target = "account.profile", ignore = true)
    @Mapping(target = "account.messages", ignore = true)
    Profile toProfile(ProfileDto profileDto);

    ProfileDto toProfileDto(Profile profile);
}
