package com.boogle.papplan.service.dashboard;

import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.entity.*;
import com.boogle.papplan.repository.*;
import com.boogle.papplan.service.project.ProjectService;
import com.boogle.papplan.service.task.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class DashBoardServiceTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ContributorRepository contributorRepository;
    @Autowired
    private TaskRepository taskRepository;


    Employees employees;
    Project project;
    Contributor contributor;

    List<Task> tasks = new ArrayList<>();

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void beforeEmployee() throws SQLException {

        // given
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);

        Department department = new Department();   department.setDept_no("PL");
        Position position = new Position();         position.setPosition_id("TEAM_LEADER");
        ProjectPriority projectPriority = new ProjectPriority();    projectPriority.setProjectPriorityId("LV1");
        ProjectStatus projectStatus = new ProjectStatus();          projectStatus.setProjectStatusId("DOING");
        TaskPriority taskPriority = new TaskPriority();             taskPriority.setTaskPriorityId("LV1");
        TaskStatus doing = new TaskStatus();                  doing.setTaskStatusId("DOING");
        TaskStatus done = new TaskStatus();                   done.setTaskStatusId("DONE");

        //  회원 추가
        employees = new Employees(1, "test@boogle.com", "1234", "홍길동", department, position);

        //  프로젝트 추가
        project = new Project(1, "테스트 프로젝트", employees,
                new GregorianCalendar(2024, month, 1).getTime(),
                new GregorianCalendar(2024, month, 25).getTime(),
                0, new Date(), "",
                projectPriority, projectStatus, new ArrayList<Task>(), new ArrayList<Contributor>());
        contributor = new Contributor(1L,project, employees);

        //  하위업무 추가
        Task task1 = new Task(1, project, "테스크1", employees,
                new GregorianCalendar(2024, month, 1).getTime(),
                new GregorianCalendar(2024,month, 13).getTime(),
                new GregorianCalendar(2024,month,10).getTime(),
                100, new GregorianCalendar(2024,month,1).getTime(),
                false, "", null, "", done, taskPriority);
        Task task2 = new Task(2, project, "테스크2", employees,
                new GregorianCalendar(2024, month, 1).getTime(),
                new GregorianCalendar(2024,month, 12).getTime(),
                new GregorianCalendar(2024,month,10).getTime(),
                100, new GregorianCalendar(2024,month,1).getTime(),
                false, "", null, "", done, taskPriority);
        Task task3 = new Task(3, project, "테스크3", employees,
                new GregorianCalendar(2024, month, 1).getTime(),
                new GregorianCalendar(2024,month, 12).getTime(),
                null,
                50, new GregorianCalendar(2024,month,1).getTime(),
                false, "", null, "", doing, taskPriority);
        tasks.add(task1);   tasks.add(task2);   tasks.add(task3);
    }

    /**
     * T006-1
     * 대시보드 집계, 필터링 로직 테스트
     * 테스트를 진행하는 달을 기준으로 값을 비교
     */
    @Test
    @Transactional
    @DisplayName("TEST1 - 대시보드 데이터 집계 및 필터링")
    public void 대시보드_집계_필터링() {

        // given
        int empno = 1;
        HashMap<String, Object> serviceResult = new HashMap<>();
        LocalDate today = LocalDate.now();
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        employeeRepository.save(employees);
        projectRepository.save(project);
        contributorRepository.save(contributor);
        taskRepository.saveAll(tasks);

        // when
        Optional<HashMap<String,Object>> projectData  = projectService.findProjectsByEmpNoDashBoard(empno);
        Optional<HashMap<String,Object>> taskData = taskService.getTasksByEmpNoDashBoard(empno);
        projectData.ifPresent(serviceResult::putAll);
        taskData.ifPresent(serviceResult::putAll);

        // then
        assertThat(serviceResult).isNotNull();
        assertThat((List)serviceResult.get("tasks")).hasSize(1);        // DOING 상태인 프로젝트 개수 확인
        assertThat((List)serviceResult.get("projects")).hasSize(1);     // DOING 상태인 하위업무 개수 확인
        if(day == 12 || day == 13)
            assertThat(serviceResult.get("task_today")).isEqualTo(1);
        if((day - 12) == 1 || (day - 13) == 1)
            assertThat(serviceResult.get("task_yesterday")).isEqualTo(1);
        assertThat(serviceResult.get("task_week")).isIn(0,1,2);

    }

}
