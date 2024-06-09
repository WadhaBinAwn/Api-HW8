package org.example.dto;

import java.util.ArrayList;

public class EmployeeIdDto {

    private String deptCode;
    private int seq;
    private int hireYear;
    private int job_id;

    private ArrayList<LinkDto> links = new ArrayList<>();


    public EmployeeIdDto() {
    }

    public EmployeeIdDto(int seq, String deptCode, int hireYear, int job_id) {
        this.seq = seq;
        this.deptCode = deptCode;
        this.hireYear = hireYear;
        this.job_id = job_id;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getHireYear() {
        return hireYear;
    }

    public void setHireYear(int hireYear) {
        this.hireYear = hireYear;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public ArrayList<LinkDto> getLinks() {
        return links;
    }

    public void addLink(String url, String rel) {
        LinkDto link = new LinkDto();
        link.setLink(url);
        link.setRel(rel);
        links.add(link);
    }

    @Override
    public String toString() {
        return "EmployeeIdDto{" +
                "deptCode='" + deptCode + '\'' +
                ", seq=" + seq +
                ", hireYear=" + hireYear +
                ", job_id=" + job_id +
                '}';
    }
}
