package com.innowise.secret_santa.service;

import com.innowise.secret_santa.exception.MapperException;
import com.innowise.secret_santa.exception.NoDataFoundException;
import com.innowise.secret_santa.mapper.ProfileMapper;
import com.innowise.secret_santa.model.dto.AddressDto;
import com.innowise.secret_santa.model.dto.ProfileDto;
import com.innowise.secret_santa.model.dto.request_dto.PagesDto;
import com.innowise.secret_santa.model.dto.response_dto.PagesDtoResponse;
import com.innowise.secret_santa.model.postgres.Address;
import com.innowise.secret_santa.model.postgres.Profile;
import com.innowise.secret_santa.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final LoggerService<ProfileDto> logger;
    private final LoadIdAuthenticationAccount loadIdAuthenticationAccount;

    private final AccountProfileService accountService;
    private final PageService<ProfileDto> pageService;


    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository,
                              ProfileMapper profileMapper,
                              LoggerService<ProfileDto> logger,
                              LoadIdAuthenticationAccount loadIdAuthenticationAccount,
                              AccountService accountService, PageService<ProfileDto> pageService) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.logger = logger;
        this.loadIdAuthenticationAccount = loadIdAuthenticationAccount;
        this.accountService = accountService;
        this.pageService = pageService;
    }

    public void createdProfile(ProfileDto profile) {

        Optional.ofNullable(profile)
                .map(profileMapper::toProfile)
                .map(this::setAccountToProfile)
                .map(profileRepository::save)
                .ifPresent(log -> logger.loggerInfo(
                        "Account by id: {}, created profile",
                        loadIdAuthenticationAccount.getAccountId()
                ));
    }

    private Profile setAccountToProfile(Profile profile) {
        profile.setAccount(accountService.getAccountById(
                loadIdAuthenticationAccount.getAccountId()
        ));
        return profile;
    }


    public void deleteProfileByAccount(Long id) {
        Optional.ofNullable(id)
                .map(profileRepository::findProfileByAccount_Id)
                .ifPresent(profileRepository::delete);

        logger.loggerInfo(
                "Profile delete for account by id: ",
                id);
    }

    private Profile getProfileById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(profileRepository::findById)
                .orElseThrow(() -> new NoDataFoundException("Profile by id :" + id + " not found!"));
    }

    public ProfileDto getProfileDtoById(Long id) {

        return Optional.ofNullable(id)
                .map(this::getProfileById)
                .map(profileMapper::toProfileDto)
                .orElseThrow(() -> new MapperException("Error"));
    }

    public ProfileDto getProfileDtoByAccount(Long id) {
        return Optional.ofNullable(id)
                .map(profileRepository::findProfileByAccount_Id)
                .map(profileMapper::toProfileDto)
                .orElseThrow(() -> new NoDataFoundException("Profile not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public PagesDtoResponse<Object> getAllProfiles(PagesDto pages) {
        Page<ProfileDto> listProfiles = profileRepository.findAll(pageService.getPage(pages))
                .map(profileMapper::toProfileDto);
        if (listProfiles.isEmpty()) {
            throw new NoDataFoundException("Profiles not found");
        }
        return pageService.getPagesDtoResponse(pages, listProfiles.getContent());
    }

    public ProfileDto updateProfile(Long accountId, ProfileDto profileDto) {

        return Optional.ofNullable(accountId)
                .map(profileRepository::findProfileByAccount_Id)
                .map(profile -> changeProfileData(profile, profileDto))
                .map(profileRepository::save)
                .map(profileMapper::toProfileDto)
                .map(item -> logger.logger("Update account profile with id: " + accountId, item))
                .orElseThrow();
    }

    private Profile changeProfileData(Profile oldProfileData, ProfileDto newProfileData) {

        String name = newProfileData.getName();

        AddressDto addressDto = newProfileData.getAddress();
        Address address = oldProfileData.getAddress();

        if (name.isBlank() && !name.equals(oldProfileData.getName())) {
            oldProfileData.setName(name);
        }
        String country = addressDto.getCountry();
        String city = addressDto.getCity();
        String street = addressDto.getStreet();
        String numberHouse = addressDto.getNumberHouse();
        String numberApartment = addressDto.getNumberApartment();

        if (!country.isBlank() && !country.equals(address.getCountry())) {
            address.setCountry(country);
        }
        if (!city.isBlank() && !city.equals(address.getCity())) {
            address.setCity(city);
        }
        if (!street.isBlank() && !street.equals(address.getStreet())) {
            address.setStreet(street);
        }
        if (!numberHouse.isBlank() && !numberHouse.equals(address.getNumberHouse())) {
            address.setNumberHouse(numberHouse);
        }
        if (!numberApartment.isBlank() && !numberApartment.equals(address.getNumberApartment())) {
            address.setNumberApartment(numberApartment);
        }
        oldProfileData.setAddress(address);
        return oldProfileData;
    }
}
