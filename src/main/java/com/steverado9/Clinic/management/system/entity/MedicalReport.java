package com.steverado9.Clinic.management.system.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MedicalReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clinicName;

    private String diagnosis;

    private String treatmentNotes;

    private LocalDateTime appointmentDate;

    @ManyToOne
    private PatientProfile patient;

    @ManyToOne
    private DoctorProfile doctor;

    public MedicalReport() {}

    public MedicalReport(String clinicName, String diagnosis, String treatmentNotes, PatientProfile patient, DoctorProfile doctor) {
        this.clinicName = clinicName;
        this.diagnosis = diagnosis;
        this.treatmentNotes = treatmentNotes;
        this.patient = patient;
        this.doctor = doctor;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatmentNotes() {
        return treatmentNotes;
    }

    public void setTreatmentNotes(String treatmentNotes) {
        this.treatmentNotes = treatmentNotes;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public PatientProfile getPatient() {
        return patient;
    }

    public void setPatient(PatientProfile patient) {
        this.patient = patient;
    }

    public DoctorProfile getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorProfile doctor) {
        this.doctor = doctor;
    }
}
