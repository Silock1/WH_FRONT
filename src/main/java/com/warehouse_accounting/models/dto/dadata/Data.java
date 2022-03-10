
package com.warehouse_accounting.models.dto.dadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "kpp",
    "capital",
    "management",
    "founders",
    "managers",
    "predecessors",
    "successors",
    "branch_type",
    "branch_count",
    "source",
    "qc",
    "hid",
    "type",
    "state",
    "opf",
    "name",
    "inn",
    "ogrn",
    "okpo",
    "okato",
    "oktmo",
    "okogu",
    "okfs",
    "okved",
    "okveds",
    "authorities",
    "documents",
    "licenses",
    "finance",
    "address",
    "phones",
    "emails",
    "ogrn_date",
    "okved_type",
    "employee_count"
})
@Generated("jsonschema2pojo")
public class Data {

    @JsonProperty("kpp")
    private String kpp;
    @JsonProperty("capital")
    private Object capital;
    @JsonProperty("management")
    private Management management;
    @JsonProperty("founders")
    private Object founders;
    @JsonProperty("managers")
    private Object managers;
    @JsonProperty("predecessors")
    private Object predecessors;
    @JsonProperty("successors")
    private Object successors;
    @JsonProperty("branch_type")
    private String branchType;
    @JsonProperty("branch_count")
    private Integer branchCount;
    @JsonProperty("source")
    private Object source;
    @JsonProperty("qc")
    private Object qc;
    @JsonProperty("hid")
    private String hid;
    @JsonProperty("type")
    private String type;
    @JsonProperty("state")
    private State state;
    @JsonProperty("opf")
    private Opf opf;
    @JsonProperty("name")
    private Name name;
    @JsonProperty("inn")
    private String inn;
    @JsonProperty("ogrn")
    private String ogrn;
    @JsonProperty("okpo")
    private String okpo;
    @JsonProperty("okato")
    private String okato;
    @JsonProperty("oktmo")
    private String oktmo;
    @JsonProperty("okogu")
    private String okogu;
    @JsonProperty("okfs")
    private String okfs;
    @JsonProperty("okved")
    private String okved;
    @JsonProperty("okveds")
    private Object okveds;
    @JsonProperty("authorities")
    private Object authorities;
    @JsonProperty("documents")
    private Object documents;
    @JsonProperty("licenses")
    private Object licenses;
    @JsonProperty("finance")
    private Finance finance;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("phones")
    private Object phones;
    @JsonProperty("emails")
    private Object emails;
    @JsonProperty("ogrn_date")
    private Long ogrnDate;
    @JsonProperty("okved_type")
    private String okvedType;
    @JsonProperty("employee_count")
    private Object employeeCount;

    @JsonProperty("kpp")
    public String getKpp() {
        return kpp;
    }

    @JsonProperty("kpp")
    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    @JsonProperty("capital")
    public Object getCapital() {
        return capital;
    }

    @JsonProperty("capital")
    public void setCapital(Object capital) {
        this.capital = capital;
    }

    @JsonProperty("management")
    public Management getManagement() {
        return management;
    }

    @JsonProperty("management")
    public void setManagement(Management management) {
        this.management = management;
    }

    @JsonProperty("founders")
    public Object getFounders() {
        return founders;
    }

    @JsonProperty("founders")
    public void setFounders(Object founders) {
        this.founders = founders;
    }

    @JsonProperty("managers")
    public Object getManagers() {
        return managers;
    }

    @JsonProperty("managers")
    public void setManagers(Object managers) {
        this.managers = managers;
    }

    @JsonProperty("predecessors")
    public Object getPredecessors() {
        return predecessors;
    }

    @JsonProperty("predecessors")
    public void setPredecessors(Object predecessors) {
        this.predecessors = predecessors;
    }

    @JsonProperty("successors")
    public Object getSuccessors() {
        return successors;
    }

    @JsonProperty("successors")
    public void setSuccessors(Object successors) {
        this.successors = successors;
    }

    @JsonProperty("branch_type")
    public String getBranchType() {
        return branchType;
    }

    @JsonProperty("branch_type")
    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    @JsonProperty("branch_count")
    public Integer getBranchCount() {
        return branchCount;
    }

    @JsonProperty("branch_count")
    public void setBranchCount(Integer branchCount) {
        this.branchCount = branchCount;
    }

    @JsonProperty("source")
    public Object getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(Object source) {
        this.source = source;
    }

    @JsonProperty("qc")
    public Object getQc() {
        return qc;
    }

    @JsonProperty("qc")
    public void setQc(Object qc) {
        this.qc = qc;
    }

    @JsonProperty("hid")
    public String getHid() {
        return hid;
    }

    @JsonProperty("hid")
    public void setHid(String hid) {
        this.hid = hid;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("state")
    public State getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(State state) {
        this.state = state;
    }

    @JsonProperty("opf")
    public Opf getOpf() {
        return opf;
    }

    @JsonProperty("opf")
    public void setOpf(Opf opf) {
        this.opf = opf;
    }

    @JsonProperty("name")
    public Name getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(Name name) {
        this.name = name;
    }

    @JsonProperty("inn")
    public String getInn() {
        return inn;
    }

    @JsonProperty("inn")
    public void setInn(String inn) {
        this.inn = inn;
    }

    @JsonProperty("ogrn")
    public String getOgrn() {
        return ogrn;
    }

    @JsonProperty("ogrn")
    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    @JsonProperty("okpo")
    public String getOkpo() {
        return okpo;
    }

    @JsonProperty("okpo")
    public void setOkpo(String okpo) {
        this.okpo = okpo;
    }

    @JsonProperty("okato")
    public String getOkato() {
        return okato;
    }

    @JsonProperty("okato")
    public void setOkato(String okato) {
        this.okato = okato;
    }

    @JsonProperty("oktmo")
    public String getOktmo() {
        return oktmo;
    }

    @JsonProperty("oktmo")
    public void setOktmo(String oktmo) {
        this.oktmo = oktmo;
    }

    @JsonProperty("okogu")
    public String getOkogu() {
        return okogu;
    }

    @JsonProperty("okogu")
    public void setOkogu(String okogu) {
        this.okogu = okogu;
    }

    @JsonProperty("okfs")
    public String getOkfs() {
        return okfs;
    }

    @JsonProperty("okfs")
    public void setOkfs(String okfs) {
        this.okfs = okfs;
    }

    @JsonProperty("okved")
    public String getOkved() {
        return okved;
    }

    @JsonProperty("okved")
    public void setOkved(String okved) {
        this.okved = okved;
    }

    @JsonProperty("okveds")
    public Object getOkveds() {
        return okveds;
    }

    @JsonProperty("okveds")
    public void setOkveds(Object okveds) {
        this.okveds = okveds;
    }

    @JsonProperty("authorities")
    public Object getAuthorities() {
        return authorities;
    }

    @JsonProperty("authorities")
    public void setAuthorities(Object authorities) {
        this.authorities = authorities;
    }

    @JsonProperty("documents")
    public Object getDocuments() {
        return documents;
    }

    @JsonProperty("documents")
    public void setDocuments(Object documents) {
        this.documents = documents;
    }

    @JsonProperty("licenses")
    public Object getLicenses() {
        return licenses;
    }

    @JsonProperty("licenses")
    public void setLicenses(Object licenses) {
        this.licenses = licenses;
    }

    @JsonProperty("finance")
    public Finance getFinance() {
        return finance;
    }

    @JsonProperty("finance")
    public void setFinance(Finance finance) {
        this.finance = finance;
    }

    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonProperty("phones")
    public Object getPhones() {
        return phones;
    }

    @JsonProperty("phones")
    public void setPhones(Object phones) {
        this.phones = phones;
    }

    @JsonProperty("emails")
    public Object getEmails() {
        return emails;
    }

    @JsonProperty("emails")
    public void setEmails(Object emails) {
        this.emails = emails;
    }

    @JsonProperty("ogrn_date")
    public Long getOgrnDate() {
        return ogrnDate;
    }

    @JsonProperty("ogrn_date")
    public void setOgrnDate(Long ogrnDate) {
        this.ogrnDate = ogrnDate;
    }

    @JsonProperty("okved_type")
    public String getOkvedType() {
        return okvedType;
    }

    @JsonProperty("okved_type")
    public void setOkvedType(String okvedType) {
        this.okvedType = okvedType;
    }

    @JsonProperty("employee_count")
    public Object getEmployeeCount() {
        return employeeCount;
    }

    @JsonProperty("employee_count")
    public void setEmployeeCount(Object employeeCount) {
        this.employeeCount = employeeCount;
    }

}
