package com.steverado9.Clinic.management.system.service.Impl;

import com.steverado9.Clinic.management.system.entity.ReceptionProfile;
import com.steverado9.Clinic.management.system.repository.ReceptionRepository;
import com.steverado9.Clinic.management.system.service.ReceptionService;
import org.springframework.stereotype.Service;

@Service
public class ReceptionServiceImpl implements ReceptionService {

    private ReceptionRepository receptionRepository;

    public ReceptionServiceImpl(ReceptionRepository receptionRepository) {
        this.receptionRepository = receptionRepository;
    }


    @Override
    public ReceptionProfile saveReception(ReceptionProfile reception) {
        return receptionRepository.save(reception);
    }
}
