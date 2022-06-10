package com.innowise.secret_santa.controller;

import com.innowise.secret_santa.model.dto.ProfileDto;
import com.innowise.secret_santa.service.ProfileServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api("Profile Rest Controller")
public class ProfileController {

    private final ProfileServiceImpl profileService;

    @Autowired
    public ProfileController(ProfileServiceImpl profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{accountId}/profile/")
    @ApiOperation("getting a profile by id")
    public ResponseEntity<ProfileDto> getProfileByAccountId(@PathVariable("accountId") Long id) {

        ProfileDto profileDtoByAccount = profileService.getProfileDtoByAccount(id);

        return new ResponseEntity<>(profileDtoByAccount, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("save a profile")
    public ResponseEntity<HttpStatus> createProfile(@RequestBody ProfileDto profile) {

        profileService.createdProfile(profile);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete account by id")
    public ResponseEntity<HttpStatus> deleteProfile(@PathVariable("id") Long id) {

        profileService.deleteProfileByAccount(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
