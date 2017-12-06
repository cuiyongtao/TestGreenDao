package com.test.testgreendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class StudentInfo {
    @Id(autoincrement = true)
    private Long id; //id  设置自增长
    private String name; //学生姓名
    private String grade;//学生年级
    private int score;//为了方便直接写成了整型
    @Generated(hash = 449582015)
    public StudentInfo(Long id, String name, String grade, int score) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.score = score;
    }
    @Generated(hash = 2016856731)
    public StudentInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGrade() {
        return this.grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public int getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score = score;
    }

}
