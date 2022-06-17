package com.innowise.secret_santa.service.account_services;

import com.innowise.secret_santa.model.RoleEnum;
import com.innowise.secret_santa.model.SettingRolesEnum;

public interface AccountGameService {

    void setRoleToAccount(Long id, RoleEnum roleEnum, SettingRolesEnum settingRolesEnum);
}
