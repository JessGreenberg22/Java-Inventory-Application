package model;

public class OutSourced extends Part {
    private String CompanyName;

            public OutSourced(int id, String name, double price, int stock, int min,int max, String companyName)
            {
                super(id, name,price, stock, min,max);
                this.CompanyName = companyName;
            }

            /***Getter***/
    public String getCompanyName()

    {
        return CompanyName;
    }

            /****Setter****/
            public void setCompanyName(String companyName)
            {
                this.CompanyName = companyName;
            }
}
