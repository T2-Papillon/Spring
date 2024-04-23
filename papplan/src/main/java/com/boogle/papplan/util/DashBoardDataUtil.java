package com.boogle.papplan.util;

import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.Task;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashBoardDataUtil {

    public static HashMap<String, Object> getDashBoardPrjData(List<Project> projects) {

        HashMap<String, Object> prjData = new HashMap<String, Object>();
        List<Project> prjIsProgressing = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate lastWeekEnd = today.minusDays(today.getDayOfWeek().getValue() + 1);
        LocalDate lastWeekStart = lastWeekEnd.minusDays(6);
        LocalDate date;

        int prjToday        = 0;
        int prjYesterday    = 0;
        int prjWeek         = 0;

        for (Project project : projects) {
            date = LocalDate.parse(project.getProjEndDate().toString());
            prjToday        += today.equals(date) ? 1 : 0;
            //prjYesterday    += date.isAfter(today.minusDays(2)) && date.isBefore(today) ? 1 : 0;
            prjYesterday    += date.isEqual(today.minusDays(1)) ? 1 : 0;
            prjWeek         += date.isAfter(lastWeekStart) && date.isBefore(lastWeekEnd) ? 1 : 0;
            if(project.getProjectStatus().getProjectStatusId().equals("DOING")) {
                prjIsProgressing.add(project);
            }
        }

        prjData.put("projects" , prjIsProgressing);
        prjData.put("prj_today" , prjToday);
        prjData.put("prj_yesterday" , prjYesterday);
        prjData.put("prj_week" , prjWeek);

        return prjData;
    }

    public static HashMap<String, Object> getDashBoardTaskData(List<Task> tasks) {

        HashMap<String, Object> taskData = new HashMap<String, Object>();
        List<Task> taskIsProgressing = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate lastWeekEnd = today.minusDays(today.getDayOfWeek().getValue() + 1);
        LocalDate lastWeekStart = lastWeekEnd.minusDays(6);

        LocalDate date;

        int taskToday        = 0;
        int taskYesterday    = 0;
        int taskWeek         = 0;

        for (Task task : tasks) {
            if(task.getTaskFinishDate() != null) {
                date = LocalDate.parse(task.getTaskFinishDate().toString());
                taskToday       += today.equals(date) ? 1 : 0;
                //taskYesterday   += date.isAfter(today.minusDays(2)) && date.isBefore(today) ? 1 : 0;
                taskYesterday   += date.isEqual(today.minusDays(1)) ? 1 : 0;
                taskWeek        += date.isAfter(lastWeekStart) && date.isBefore(lastWeekEnd) ? 1 : 0;
            }
            if(task.getTaskStatus().getTaskStatusId().equals("DOING")) {
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
