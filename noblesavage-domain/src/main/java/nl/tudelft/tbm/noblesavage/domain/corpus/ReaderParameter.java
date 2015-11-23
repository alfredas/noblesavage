package nl.tudelft.tbm.noblesavage.domain.corpus;

public class ReaderParameter {

    private String param;
    private String name;
    private String desrciption;
    private String type;

    public ReaderParameter() {
    }

    public ReaderParameter(String param, String name, String desrciption, String type) {
        super();
        this.param = param;
        this.name = name;
        this.desrciption = desrciption;
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesrciption() {
        return desrciption;
    }

    public void setDesrciption(String desrciption) {
        this.desrciption = desrciption;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
