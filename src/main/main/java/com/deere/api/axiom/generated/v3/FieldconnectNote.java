
package com.deere.api.axiom.generated.v3;

public class FieldconnectNote extends Resource {

    private final static long serialVersionUID = 1L;
    protected String text;
    protected String placedOnDate;
    protected String noteType;
    protected String dateModified;
    protected String dateCreated;
    protected String creator;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlacedOnDate() {
        return placedOnDate;
    }

    public void setPlacedOnDate(String placedOnDate) {
        this.placedOnDate = placedOnDate;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }


    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
