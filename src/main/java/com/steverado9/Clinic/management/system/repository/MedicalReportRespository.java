package com.steverado9.Clinic.management.system.repository;

import com.steverado9.Clinic.management.system.entity.DoctorProfile;
import com.steverado9.Clinic.management.system.entity.MedicalReport;
import com.steverado9.Clinic.management.system.entity.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalReportRespository extends JpaRepository<MedicalReport, Long> {
    Optional<List<MedicalReport>> findByPatient(PatientProfile patient);

    void deleteByDoctorId(Long id);

    Optional<List<MedicalReport>> findByPatientId(Long patientId);
}
