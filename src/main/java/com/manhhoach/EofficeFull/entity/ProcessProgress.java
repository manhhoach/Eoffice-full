package com.manhhoach.EofficeFull.entity;

import com.manhhoach.EofficeFull.constant.AttachmentType;
import com.manhhoach.EofficeFull.constant.ProcessProgressType;
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
@Table(name = "process_progress")
public class ProcessProgress extends BaseEntity{
    private Long itemId;

    private Boolean isProcessed;

    @Enumerated(EnumType.STRING)
    private ProcessProgressType type;

    private String note;

    private Long recipientId;

    private Long handlerId;
}
