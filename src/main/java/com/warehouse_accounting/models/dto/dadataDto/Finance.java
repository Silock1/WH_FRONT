
package com.warehouse_accounting.models.dto.dadataDto;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Finance {

    @SerializedName("tax_system")
    @Expose
    private Object taxSystem;
    @SerializedName("income")
    @Expose
    private Object income;
    @SerializedName("expense")
    @Expose
    private Object expense;
    @SerializedName("debt")
    @Expose
    private Object debt;
    @SerializedName("penalty")
    @Expose
    private Object penalty;
    @SerializedName("year")
    @Expose
    private Object year;

    public Object getTaxSystem() {
        return taxSystem;
    }

    public void setTaxSystem(Object taxSystem) {
        this.taxSystem = taxSystem;
    }

    public Object getIncome() {
        return income;
    }

    public void setIncome(Object income) {
        this.income = income;
    }

    public Object getExpense() {
        return expense;
    }

    public void setExpense(Object expense) {
        this.expense = expense;
    }

    public Object getDebt() {
        return debt;
    }

    public void setDebt(Object debt) {
        this.debt = debt;
    }

    public Object getPenalty() {
        return penalty;
    }

    public void setPenalty(Object penalty) {
        this.penalty = penalty;
    }

    public Object getYear() {
        return year;
    }

    public void setYear(Object year) {
        this.year = year;
    }

}
