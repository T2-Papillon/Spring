package com.boogle.papplan.util;

import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.dto.project.ProjectDTO;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashBoardDataUtil {

    public static HashMap<String, Object> getDashBoardPrjData(List<ProjectDTO> projects) {

        HashMap<String, Object> prjData = new HashMap<String, Object>();
        List<ProjectDTO> prjIsProgressing = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate lastWeekEnd = today.minusDays(today.getDayOfWeek().getValue() + 1);
        LocalDate lastWeekStart = lastWeekEnd.minusDays(6);
        LocalDate date;

        int prjToday        = 0;
        int prjYesterday    = 0;
        int prjWeek         = 0;

        for (ProjectDTO project : projects) {
            date = LocalDate.parse(project.getProjEndDate().toString());
            prjToday        += today.equals(date) ? 1 : 0;
            prjYesterday    += date.isAfter(today.minusDays(2)) && date.isBefore(today.minusDays(1)) ? 1 : 0;
            prjWeek         += date.isAfter(lastWeekStart) && date.isBefore(lastWeekEnd) ? 1 : 0;
            if(project.getProjectStatus().equals("DOING")) {
                prjIsProgressing.add(project);
            }
        }

        prjData.put("projects" , prjIsProgressing);
        prjData.put("prj_today" , prjToday);
        prjData.put("prj_yesterday" , prjYesterday);
        prjData.put("prj_week" , prjWeek);

        return prjData;
    }

    public static HashMap<String, Object> getDashBoardTaskData(List<TaskDTO> tasks) {

        HashMap<String, Object> taskData = new HashMap<String, Object>();
        List<TaskDTO> taskIsProgressing = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate lastWeekEnd = today.minusDays(today.getDayOfWeek().getValue() + 1);
        LocalDate lastWeekStart = lastWeekEnd.minusDays(6);

        LocalDate date;

        int taskToday        = 0;
        int taskYesterday    = 0;
        int taskWeek         = 0;

        for (TaskDTO task : tasks) {
            if(task.getTaskFinishDate() != null) {
                date = LocalDate.parse(task.getTaskFinishDate().toString());
                taskToday       += today.equals(date) ? 1 : 0;
                taskYesterday   += date.isAfter(today.minusDays(2)) && date.isBefore(today.minusDays(1)) ? 1 : 0;
                taskWeek        += date.isAfter(lastWeekStart) && date.isBefore(lastWeekEnd) ? 1 : 0;
            }
            if(task.getTaskStatus().equals("DOING")) {
                taskIsProgressing.add(task);
            }
        }

        taskData.put("tasks" , taskIsProgressing);
        taskData.put("task_today" , taskToday);
        taskData.put("task_yesterday" , taskYesterday);
        taskData.put("task_week" , taskWeek);

        return taskData;
    }
}
