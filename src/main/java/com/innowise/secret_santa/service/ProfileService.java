package com.innowise.secret_santa.service;

import com.innowise.secret_santa.mapper.ProfileMapper;
import com.innowise.secret_santa.model.Profile;
import com.innowise.secret_santa.model.dto.ProfileDto;
import com.innowise.secret_santa.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    public void createdProfile(ProfileDto profile){

        profileRepository.save(profileMapper.toProfile(profile));
    }

    public void deleteProfile(Long id){
        profileRepository.deleteById(id);

    }

    public ProfileDto getProfileById(Long id){
        return profileRepository.findById(id)
                .map(profileMapper::toProfileDto)
                .orElseThrow();
    }
}
