package cn.masterlover.activiti.variable;

import lombok.Data;

import java.io.Serializable;

@Data
public class VariableEntity implements Serializable {

    private static final long serialVersionUID = -4928983553815385140L;
    private String name;
    private int day;
}
