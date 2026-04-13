package com.steverado9.Clinic.management.system.service;

import com.steverado9.Clinic.management.system.entity.DoctorProfile;
import com.steverado9.Clinic.management.system.entity.User;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface DoctorService {
    DoctorProfile saveDoctor(DoctorProfile doctor);

    DoctorProfile findByUserId(Long userId);

    List<DoctorProfile> getAllDoctors();

    DoctorProfile findById(Long id);

    DoctorProfile findByUserEmail(String email);
}
