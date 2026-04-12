package com.steverado9.Clinic.management.system.repository;

import com.steverado9.Clinic.management.system.entity.DoctorProfile;
import com.steverado9.Clinic.management.system.entity.MedicalReport;
import com.steverado9.Clinic.management.system.entity.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalReportRespository extends JpaRepository<MedicalReport, Long> {

    List<MedicalReport> findPatient(PatientProfile patient);

    List<MedicalReport> findByDoctor(DoctorProfile doctor);
}
