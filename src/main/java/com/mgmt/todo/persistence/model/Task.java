package com.mgmt.todo.persistence.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TASK")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    private String title;
    private String description;
    @Column(name = "ORDER_TASK")
    private Long order;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "user_auth_username")
    private UserAuth userAuth;
}
