package com.manhhoach.EofficeFull.dto.outgoingDocument;

import com.manhhoach.EofficeFull.entity.OutgoingDocument;
import lombok.Data;
import java.time.LocalDate;


@Data
public class CreateOutgoingDocReq {
    private String documentNumber;
    private String summary;
    private LocalDate sentDate;
    private String outgoingNumber;
    private String fileUrl;
    private String fileName;

    public static OutgoingDocument map(CreateOutgoingDocReq data){
        var e = new OutgoingDocument();
        e.setOutgoingNumber(data.getOutgoingNumber());
        e.setSentDate(data.getSentDate());
        e.setSummary(data.getSummary());
        e.setDocumentNumber(data.getDocumentNumber());
        return e;
    }
}
