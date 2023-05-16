package com.propulse.backendfacturier.dto;

public class OperatorDTO {

    private String imageFileName;
    private String name;
    private String label;

    public OperatorDTO(String imageFileName, String name, String label) {
        this.imageFileName = imageFileName;
        this.name = name;
        this.label = label;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

}
