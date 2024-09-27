package com.fresh.coding.sooatelapi.entities;

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
public class Salary extends Model {

    @ManyToOne
    @JoinColumn
    private Employee employee;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime salaryDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "salary")
    @Builder.Default
    private List<EmployeePayment> employeePayments = new ArrayList<>();


}
