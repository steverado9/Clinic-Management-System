package com.steverado9.Clinic.management.system.service;

import com.steverado9.Clinic.management.system.entity.MedicalReport;
import com.steverado9.Clinic.management.system.entity.PatientProfile;

import java.util.List;

public interface MedicalReportService {

    void saveReport(MedicalReport report);

    List<MedicalReport> findByPatient(PatientProfile patient);

    MedicalReport findById(Long reportId);
}
