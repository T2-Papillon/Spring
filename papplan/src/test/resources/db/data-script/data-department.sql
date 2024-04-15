INSERT INTO department (dept_no,dept_name) VALUES
('PL','기획팀'),
('DS','디자인팀'),
('DEV1','개발1팀'),
('DEV2','개발2팀'),
('MKT1','마케팅1팀'),
('MKT2','마케팅2팀'),
('HR','인사팀'),
('FIN','재무팀'),
('SLS','영업팀'),
('CS','CS팀'),
('OS','해외영업팀'),
('SYS','시스템개발팀');

INSERT INTO job_title(position_id,position_name) VALUES
('TEAM_LEADER','팀장'),
('STAFF','사원');

INSERT INTO project_priority (project_priority_id,project_priority_name) VALUES
('LV0','긴급'),
('LV1','높음'),
('LV2','보통'),
('LV3','낮음');

INSERT INTO project_status (project_status_id,project_status_name) VALUES
('TODO','진행예정'),
('DOING','진행중'),
('DONE','완료'),
('HOLD','보류');

INSERT INTO task_priority (task_priority_id,task_priority_name) VALUES
('LV0','긴급'),
('LV1','높음'),
('LV2','보통'),
('LV3','낮음');

INSERT INTO task_status (task_status_id,task_status_name) VALUES
('TODO','진행예정'),
('DOING','진행중'),
('DONE','완료'),
('HOLD','보류'),
('TEST','테스트');
