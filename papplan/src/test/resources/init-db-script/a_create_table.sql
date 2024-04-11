
CREATE TABLE department (
                            dept_name varchar(20) NOT NULL,
                            dept_no varchar(20) NOT NULL
)
;

CREATE TABLE job_title (
                           position_id varchar(20) NOT NULL,
                           position_name varchar(20) NOT NULL
)
;

CREATE TABLE employees (
                           eno int(11) NOT NULL AUTO_INCREMENT,
                           dept_no varchar(20) NOT NULL,
                           name varchar(20) NOT NULL,
                           position_id varchar(20) NOT NULL,
                           email varchar(50) NOT NULL,
                           password varchar(65) NOT NULL
)
;

CREATE TABLE project_priority (
                                  project_priority_id varchar(20) NOT NULL,
                                  project_priority_name varchar(20) NOT NULL
)
;

CREATE TABLE project_status (
                                project_status_id varchar(20) NOT NULL,
                                project_status_name varchar(20) NOT NULL
)
;

CREATE TABLE project (
                         proj_create_date date NOT NULL,
                         proj_end_date date NOT NULL,
                         proj_no int(11) NOT NULL AUTO_INCREMENT,
                         proj_percent int(11) DEFAULT NULL,
                         proj_start_date date NOT NULL,
                         projp_no varchar(20) NOT NULL,
                         projs_no varchar(20) NOT NULL,
                         proj_pm int(11) NOT NULL,
                         proj_title varchar(50) NOT NULL,
                         proj_desc varchar(300) NOT NULL
)
;

CREATE TABLE contributor (
                             eno int(11) NOT NULL,
                             proj_no int(11) NOT NULL,
                             id bigint(20) NOT NULL AUTO_INCREMENT
)
;

CREATE TABLE task_status (
                             task_status_id varchar(20) NOT NULL,
                             task_status_name varchar(20) NOT NULL
)
;


CREATE TABLE task_priority (
                               task_priority_id varchar(20) NOT NULL,
                               task_priority_name varchar(20) NOT NULL,
                               weight double DEFAULT NULL
)
;


CREATE TABLE task (
                      proj_no int(11) NOT NULL,
                      task_create_date date NOT NULL,
                      task_end_date date NOT NULL,
                      task_no int(11) NOT NULL AUTO_INCREMENT,
                      task_percent int(11) NOT NULL,
                      task_start_date date NOT NULL,
                      task_test bit(1) NOT NULL,
                      task_update_date date DEFAULT NULL,
                      assignee varchar(20) NOT NULL,
                      task_priority_id varchar(20) NOT NULL,
                      task_status_id varchar(20) NOT NULL,
                      task_title varchar(50) NOT NULL,
                      task_desc varchar(300) NOT NULL,
                      task_finish_date date DEFAULT NULL,
                      task_test_url varchar(255) DEFAULT NULL,
                      assignee_eno int(11) NOT NULL
)
;