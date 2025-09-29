package com.manhhoach.EofficeFull.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "outgoing_document")
public class OutgoingDocument extends Document {
    private String outgoingNumber;
    private LocalDate sentDate;
}
