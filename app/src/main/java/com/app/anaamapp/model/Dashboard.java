package com.app.anaamapp.model;

public class Dashboard
{
    private String heading;
    private String subHeading;
    private int imageres;
    private String label;

    public Dashboard(String heading, String subHeading, int imageres, String label) {
        this.heading = heading;
        this.subHeading = subHeading;
        this.imageres = imageres;
        this.label = label;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public int getImageres() {
        return imageres;
    }

    public void setImageres(int imageres) {
        this.imageres = imageres;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
