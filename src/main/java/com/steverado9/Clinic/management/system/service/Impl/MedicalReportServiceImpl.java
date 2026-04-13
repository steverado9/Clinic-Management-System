package com.steverado9.Clinic.management.system.service.Impl;

import com.steverado9.Clinic.management.system.entity.MedicalReport;
import com.steverado9.Clinic.management.system.repository.MedicalReportRespository;
import com.steverado9.Clinic.management.system.service.MedicalReportService;
import org.springframework.stereotype.Service;

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
}
