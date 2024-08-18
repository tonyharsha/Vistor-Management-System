package com.example.L15_Minor_Project_extention.entity;

import com.example.L15_Minor_Project_extention.enums.VisitStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VisitStatus status;

    private String purpose;

    private Date inTime;

    private Date outTime;

    private String imgUrl;

    @Column(nullable = false)
    private Integer noOfPeople;

    @ManyToOne
    @JoinColumn(name="flat_id")
    private Flat flat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="visitor_id")
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name="created_by_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name="approved_by_id")
    private User approvedBy;

    @CreationTimestamp
    private Date createdDate;

    @UpdateTimestamp
    private Date updatedDate;

}
