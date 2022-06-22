package com.innowise.secret_santa.mapper;

import com.innowise.secret_santa.model.dto.ProfileDto;
import com.innowise.secret_santa.model.dto.request_dto.PlayerRequestDto;
import com.innowise.secret_santa.model.dto.response_dto.PlayerResponseDto;
import com.innowise.secret_santa.model.postgres.Player;
import com.innowise.secret_santa.model.postgres.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(target = "players", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address.id", ignore = true)
    @Mapping(target = "account.password", ignore = true)
    @Mapping(target = "account.dateCreated", ignore = true)
    @Mapping(target = "account.profile", ignore = true)
    Profile toProfile(ProfileDto profileDto);

    Player toPlayer(PlayerRequestDto playerRequestDto);

    PlayerResponseDto toPlayerResponseDto(Player player);

}
