package com.innowise.secret_santa.service;

import com.innowise.secret_santa.model.dto.ProfileDto;
import com.innowise.secret_santa.model.dto.request_dto.PagesDto;
import com.innowise.secret_santa.model.dto.response_dto.PagesDtoResponse;

public interface ProfileService {

    void createdProfile(ProfileDto profile);

    ProfileDto getProfileDtoByAccount(Long id);

    ProfileDto getProfileDtoById(Long id);

    PagesDtoResponse<Object> getAllProfiles(PagesDto pages);

    ProfileDto updateProfile(Long accountId, ProfileDto profileDto);
}
