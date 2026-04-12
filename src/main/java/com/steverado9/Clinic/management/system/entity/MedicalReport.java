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

    @Column(length = 2000)
    private String treatmentNotes;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientProfile patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorProfile doctor;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    public MedicalReport() {}

    public MedicalReport(String clinicName, String diagnosis, String treatmentNotes, PatientProfile patient, DoctorProfile doctor, Appointment appointment) {
        this.clinicName = clinicName;
        this.diagnosis = diagnosis;
        this.treatmentNotes = treatmentNotes;
        this.patient = patient;
        this.doctor = doctor;
        this.appointment = appointment;
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

    public PatientProfile getPatient() {
        return patient;
    }

    public void setPatient(PatientProfile patient) {
        this.patient = patient;
    }

    public DoctorProfile getDoctor() {
        return doctor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setDoctor(DoctorProfile doctor) {
        this.doctor = doctor;
    }


}
