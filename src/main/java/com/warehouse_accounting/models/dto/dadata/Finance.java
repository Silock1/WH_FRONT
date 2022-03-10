
package com.warehouse_accounting.models.dto.dadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tax_system",
    "income",
    "expense",
    "debt",
    "penalty",
    "year"
})
@Generated("jsonschema2pojo")
public class Finance {

    @JsonProperty("tax_system")
    private Object taxSystem;
    @JsonProperty("income")
    private Object income;
    @JsonProperty("expense")
    private Object expense;
    @JsonProperty("debt")
    private Object debt;
    @JsonProperty("penalty")
    private Object penalty;
    @JsonProperty("year")
    private Object year;

    @JsonProperty("tax_system")
    public Object getTaxSystem() {
        return taxSystem;
    }

    @JsonProperty("tax_system")
    public void setTaxSystem(Object taxSystem) {
        this.taxSystem = taxSystem;
    }

    @JsonProperty("income")
    public Object getIncome() {
        return income;
    }

    @JsonProperty("income")
    public void setIncome(Object income) {
        this.income = income;
    }

    @JsonProperty("expense")
    public Object getExpense() {
        return expense;
    }

    @JsonProperty("expense")
    public void setExpense(Object expense) {
        this.expense = expense;
    }

    @JsonProperty("debt")
    public Object getDebt() {
        return debt;
    }

    @JsonProperty("debt")
    public void setDebt(Object debt) {
        this.debt = debt;
    }

    @JsonProperty("penalty")
    public Object getPenalty() {
        return penalty;
    }

    @JsonProperty("penalty")
    public void setPenalty(Object penalty) {
        this.penalty = penalty;
    }

    @JsonProperty("year")
    public Object getYear() {
        return year;
    }

    @JsonProperty("year")
    public void setYear(Object year) {
        this.year = year;
    }

}
