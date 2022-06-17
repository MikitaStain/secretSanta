package com.innowise.secret_santa.service.profile_services;

import com.innowise.secret_santa.model.postgres.Profile;

public interface ProfileGameService {

    Profile getProfileByAccountId(Long id);
}
