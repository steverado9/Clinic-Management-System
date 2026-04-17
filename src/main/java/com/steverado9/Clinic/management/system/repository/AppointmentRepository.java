package com.steverado9.Clinic.management.system.repository;

import com.steverado9.Clinic.management.system.entity.Appointment;
import com.steverado9.Clinic.management.system.entity.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<List<Appointment>>findByDoctor_Id(Long doctorId);

    Optional<List<Appointment>> findByPatientId(Long patientId);

    boolean existsByDoctorAndAppointmentDate(DoctorProfile doctor, LocalDateTime appointmentDate);

    void deleteByDoctorId(Long id);
}
