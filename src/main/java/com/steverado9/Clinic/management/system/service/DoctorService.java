package com.steverado9.Clinic.management.system.service;

import com.steverado9.Clinic.management.system.entity.DoctorProfile;
import com.steverado9.Clinic.management.system.entity.User;

public interface DoctorService {
    DoctorProfile saveDoctor(DoctorProfile doctor);

    DoctorProfile findByUser(User user);
}
