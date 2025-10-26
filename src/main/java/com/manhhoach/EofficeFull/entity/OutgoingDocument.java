package com.manhhoach.EofficeFull.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "outgoing_document")
public class OutgoingDocument extends Document {
    private String outgoingNumber;
    private LocalDate sentDate;

    public OutgoingDocument() {
    }

    public String getOutgoingNumber() {
        return outgoingNumber;
    }

    public void setOutgoingNumber(String outgoingNumber) {
        this.outgoingNumber = outgoingNumber;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }
}
