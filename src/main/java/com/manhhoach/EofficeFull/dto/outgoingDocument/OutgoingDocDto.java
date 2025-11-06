package com.manhhoach.EofficeFull.dto.outgoingDocument;

import com.manhhoach.EofficeFull.entity.OutgoingDocument;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class OutgoingDocDto {
    private Long id;
    private String documentNumber;
    private String summary;
    private LocalDate sentDate;
    private String outgoingNumber;

    public static OutgoingDocDto map(OutgoingDocument data){
        return OutgoingDocDto.builder()
                .id(data.getId())
                .documentNumber(data.getDocumentNumber())
                .outgoingNumber(data.getOutgoingNumber())
                .sentDate(data.getSentDate())
                .summary(data.getSummary())
                .build();
    }
}
