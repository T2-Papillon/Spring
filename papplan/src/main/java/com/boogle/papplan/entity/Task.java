package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_no")
    private Integer taskNo; // 업무번호

    // 외래키
    @ManyToOne
    @JoinColumn(name="proj_no", nullable = false) // 프로젝트번호 외래키
    private Project project; // 프로젝트번호

    @Column(name = "task_title", length=50, nullable = false)
    private String taskTitle; // 업무명

    @Column(name = "assignee", length=20, nullable = false)
    private String assignee; // 담당자

    @Temporal(TemporalType.DATE)
    @Column(name = "task_start_date", nullable = false)
    private Date taskStartDate; // 업무시작일

    @Temporal(TemporalType.DATE)
    @Column(name = "task_end_date", nullable = false)
    private Date taskEndDate; // 업무종료일

    @Column(name = "task_percent", nullable = false)
    private Integer taskPercent; // 업무진행율

    @Temporal(TemporalType.DATE)
    @Column(name = "task_create_date", nullable = false)
    private Date taskCreateDate; // 업무작성일

    @Column(name = "task_test", nullable = false)
    private Boolean taskTest; // 테스트 진행여부

    @Temporal(TemporalType.DATE)
    @Column(name = "task_update_date", nullable = true)
    private Date taskUpdateDate; // 업무수정일

    @Column(name = "task_desc", length=300, nullable = false)
    private String taskDesc; // 설명

    // 외래키
    @ManyToOne
    @JoinColumn(name = "task_status_id", nullable = false) // 업무상태번호 외래키
    private TaskStatus taskStatus; // 업무상태번호

    // 외래키
    @ManyToOne
    @JoinColumn(name = "task_priority_id", nullable = false) // 업무우선순위번호 외래키
    private TaskPriority taskPriority; // 업무우선순위번호

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Task task = (Task) o;
        return getTaskNo() != null && Objects.equals(getTaskNo(), task.getTaskNo());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
