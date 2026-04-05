package com.steverado9.Clinic.management.system.repository;

import com.steverado9.Clinic.management.system.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
