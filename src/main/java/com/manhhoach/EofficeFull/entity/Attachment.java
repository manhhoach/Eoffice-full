package com.manhhoach.EofficeFull.entity;

import com.manhhoach.EofficeFull.constant.AttachmentType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attachment")
public class Attachment extends BaseEntity{

    private String fileName;

    private String filePath;

    private String pdfFilePath;

    @Enumerated(EnumType.STRING)
    private AttachmentType type;

    private Long itemId;
}
