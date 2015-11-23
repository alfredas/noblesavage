package nl.tudelft.tbm.noblesavage.facade;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ReaderParameterDTO implements IsSerializable {

    private static final long serialVersionUID = 1L;

    private String param;
    private String name;
    private String desrciption;
    private String type;

    public ReaderParameterDTO() {
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
