package de.sri.domain.entities;

import java.time.LocalDate;

public class Accident {
    private final int id;
    private double cost;
    private LocalDate date;
    private final int policyId;

    public Accident(int id, double cost, LocalDate date, int policyId) {
        this.id = id;
        this.cost = cost;
        this.date = date;
        this.policyId = policyId;
    }

    public int getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPolicyId() {
        return policyId;
    }

    @Override
    public String toString() {
        return "Accident{" + "id=" + id + ", cost=" + cost + ", date=" + date + ", policyId=" + policyId + '}';
    }
}
