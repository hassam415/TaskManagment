package com.example.taskmanagement;


import java.time.Period;
import java.util.List;
import java.util.Map;

public class Report {
  public period period;
public  Kpis kpis;
    public List<ThroughoutTrend> throughput_trend;
    public List<TeamBreakdown> team_breakdown;
    public List<MemberBreakdown> member_breakdown;
    public Map<String, StatusDistribution> status_distribution;
    public Map<String, Priority_Distribution> priority_distribution;


    public period getPeriod() {
        return period;
    }

    public void setPeriod(period period) {
        this.period = period;
    }

    public Kpis getKpis() {
        return kpis;
    }

    public void setKpis(Kpis kpis) {
        this.kpis = kpis;
    }

    public List<ThroughoutTrend> getThroughput_trend() {
        return throughput_trend;
    }

    public void setThroughput_trend(List<ThroughoutTrend> throughput_trend) {
        this.throughput_trend = throughput_trend;
    }

    public Report(period period, Kpis kpis, List<ThroughoutTrend> throughput_trend, List<TeamBreakdown> team_breakdown, List<MemberBreakdown> member_breakdown, Map<String, StatusDistribution> status_distribution, Map<String, Priority_Distribution> priority_distribution) {
        this.period = period;
        this.kpis = kpis;
        this.throughput_trend = throughput_trend;
        this.team_breakdown = team_breakdown;
        this.member_breakdown = member_breakdown;
        this.status_distribution = status_distribution;
        this.priority_distribution = priority_distribution;
    }

    public List<TeamBreakdown> getTeam_breakdown() {
        return team_breakdown;
    }

    public void setTeam_breakdown(List<TeamBreakdown> team_breakdown) {
        this.team_breakdown = team_breakdown;
    }

    public List<MemberBreakdown> getMember_breakdown() {
        return member_breakdown;
    }

    public void setMember_breakdown(List<MemberBreakdown> member_breakdown) {
        this.member_breakdown = member_breakdown;
    }

    public Map<String, StatusDistribution> getStatus_distribution() {
        return status_distribution;
    }

    public void setStatus_distribution(Map<String, StatusDistribution> status_distribution) {
        this.status_distribution = status_distribution;
    }

    public Map<String, Priority_Distribution> getPriority_distribution() {
        return priority_distribution;
    }

    public void setPriority_distribution(Map<String, Priority_Distribution> priority_distribution) {
        this.priority_distribution = priority_distribution;
    }
}
