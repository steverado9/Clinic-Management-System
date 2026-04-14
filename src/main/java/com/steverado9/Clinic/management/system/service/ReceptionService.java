package com.steverado9.Clinic.management.system.service;

import com.steverado9.Clinic.management.system.entity.ReceptionProfile;

import java.util.List;

public interface ReceptionService {
    ReceptionProfile saveReception(ReceptionProfile reception);

    List<ReceptionProfile> getAllReceptions();

    void deleteByReceptionId(Long id);
}
