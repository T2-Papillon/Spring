package com.boogle.papplan.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Integer eno;
    private String email;
    private String name;
    @JsonProperty("dept_no")
    private String deptNo;
    @JsonProperty("position_id")
    private String positionId;
}
