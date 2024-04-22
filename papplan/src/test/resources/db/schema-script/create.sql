CREATE TABLE department (
    dept_no VARCHAR(20) NOT NULL,
    dept_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (dept_no)
);

CREATE TABLE job_title (
    position_id VARCHAR(20) NOT NULL,
    position_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (position_id)
);

CREATE TABLE employees (
    eno INTEGER AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(65) NOT NULL,
    name VARCHAR(20) NOT NULL,
    dept_no VARCHAR(20) NOT NULL,
    position_id VARCHAR(20) NOT NULL,
    CONSTRAINT fk_dept_no FOREIGN KEY (dept_no) REFERENCES department(dept_no),
    CONSTRAINT fk_position_id FOREIGN KEY (position_id) REFERENCES job_title(position_id)
);

CREATE TABLE project_priority (
    project_priority_id VARCHAR(20) NOT NULL,
    project_priority_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (project_priority_id)
);

CREATE TABLE project_status (
    project_status_id VARCHAR(20) NOT NULL,
    project_status_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (project_status_id)
);

CREATE TABLE project (
    proj_no INTEGER AUTO_INCREMENT PRIMARY KEY,
    proj_title VARCHAR(50) NOT NULL,
    proj_pm INTEGER NOT NULL,
    proj_start_date DATE NOT NULL,
    proj_end_date DATE NOT NULL,
    proj_percent INTEGER,
    proj_create_date DATE NOT NULL,
    proj_desc VARCHAR(300) NOT NULL,
    projp_no VARCHAR(20) NOT NULL,
    projs_no VARCHAR(20) NOT NULL,
    FOREIGN KEY (proj_pm) REFERENCES employees (eno),
    FOREIGN KEY (projp_no) REFERENCES project_priority (project_priority_id),
    FOREIGN KEY (projs_no) REFERENCES project_status (project_status_id)
);

CREATE TABLE contributor (
    id BIGINT NOT NULL AUTO_INCREMENT,
    proj_no INTEGER NOT NULL,
    eno INTEGER NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_proj_no FOREIGN KEY (proj_no) REFERENCES project (proj_no),
    CONSTRAINT FK_eno FOREIGN KEY (eno) REFERENCES employees (eno)
);

CREATE TABLE task_priority (
    task_priority_id VARCHAR(20) NOT NULL,
    task_priority_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (task_priority_id)
);

CREATE TABLE task_status (
    task_status_id VARCHAR(20) NOT NULL,
    task_status_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (task_status_id)
);

CREATE TABLE task (
    task_no INTEGER NOT NULL AUTO_INCREMENT,
    proj_no INTEGER NOT NULL,
    task_title VARCHAR(50) NOT NULL,
    assignee_eno INTEGER NOT NULL,
    task_start_date DATE NOT NULL,
    task_end_date DATE NOT NULL,
    task_finish_date DATE,
    task_percent INTEGER NOT NULL,
    task_create_date DATE NOT NULL,
    task_test BOOLEAN NOT NULL,
    task_test_url VARCHAR(255),
    task_update_date DATE,
    task_desc VARCHAR(300) NOT NULL,
    task_status_id VARCHAR(20) NOT NULL,
    task_priority_id VARCHAR(20) NOT NULL,
    PRIMARY KEY (task_no),
    CONSTRAINT FK_proj_no_task FOREIGN KEY (proj_no) REFERENCES project (proj_no),
    CONSTRAINT FK_assignee_eno FOREIGN KEY (assignee_eno) REFERENCES employees (eno),
    CONSTRAINT FK_task_status_id_task FOREIGN KEY (task_status_id) REFERENCES task_status (task_status_id),
    CONSTRAINT FK_task_priority_id_task FOREIGN KEY (task_priority_id) REFERENCES task_priority (task_priority_id)
);