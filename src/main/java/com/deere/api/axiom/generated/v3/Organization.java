
package com.deere.api.axiom.generated.v3;

public class Organization extends Resource {

    private final static long serialVersionUID = 1L;
    protected String name;
    protected String type;
    //@XmlElement(name = "address")
    //protected List<Address> addresses;
    protected String accountId;
    //@XmlElement(name = "partnership")
    //protected List<Partnership> partnerships;
    protected boolean member;
    //protected Farms farms;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }
}
