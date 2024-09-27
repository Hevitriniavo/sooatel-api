package com.fresh.coding.sooatelapi.entities;

import com.fresh.coding.sooatelapi.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee extends Model {


    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private LocalDateTime hireDate;

    @ManyToOne
    @JoinColumn
    private Department department;

    @Column
    private String position;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @OneToMany(mappedBy = "employee")
    @Builder.Default
    private List<Salary> salaries = new ArrayList<>();

    @OneToMany(mappedBy = "employee")
    @Builder.Default
    private List<EmployeePayment> employeePayments = new ArrayList<>();

    @OneToMany(mappedBy = "employee")
    @Builder.Default
    private List<EmployeeAttendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "employee")
    @Builder.Default
    private List<EmployeeOrderService> orderServices = new ArrayList<>();
}
