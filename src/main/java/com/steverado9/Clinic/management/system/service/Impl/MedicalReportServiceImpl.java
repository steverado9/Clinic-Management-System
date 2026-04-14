package com.steverado9.Clinic.management.system.service.Impl;

import com.steverado9.Clinic.management.system.entity.MedicalReport;
import com.steverado9.Clinic.management.system.entity.PatientProfile;
import com.steverado9.Clinic.management.system.repository.MedicalReportRespository;
import com.steverado9.Clinic.management.system.service.MedicalReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalReportServiceImpl implements MedicalReportService {

    MedicalReportRespository medicalReportRespository;

    public MedicalReportServiceImpl(MedicalReportRespository medicalReportRespository) {
        this.medicalReportRespository = medicalReportRespository;
    }

    @Override
    public void saveReport(MedicalReport report) {
        medicalReportRespository.save(report);
    }

    @Override
    public List<MedicalReport> findByPatient(PatientProfile patient) {
        return medicalReportRespository.findByPatient(patient);
    }

    @Override
    public MedicalReport findById(Long reportId) {
        return medicalReportRespository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    @Override
    public List<MedicalReport> getMedicalReportByPatientId(Long patientId) {
        return medicalReportRespository.findByPatientId(patientId);
    }
}
