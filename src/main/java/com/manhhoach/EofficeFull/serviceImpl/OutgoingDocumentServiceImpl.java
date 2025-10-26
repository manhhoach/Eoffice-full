package com.manhhoach.EofficeFull.serviceImpl;

import com.manhhoach.EofficeFull.constant.AttachmentType;
import com.manhhoach.EofficeFull.dto.outgoingDocument.CreateOutgoingDocReq;
import com.manhhoach.EofficeFull.entity.Attachment;
import com.manhhoach.EofficeFull.helper.FileHelper;
import com.manhhoach.EofficeFull.repository.AttachmentRepository;
import com.manhhoach.EofficeFull.repository.OutgoingDocumentRepository;
import com.manhhoach.EofficeFull.service.OutgoingDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OutgoingDocumentServiceImpl implements OutgoingDocumentService {
    private final OutgoingDocumentRepository outgoingDocumentRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public void create(CreateOutgoingDocReq req) {
        try{
            var entity = CreateOutgoingDocReq.map(req);
            outgoingDocumentRepository.save(entity);

            String newPath = FileHelper.moveFile(req.getFileUrl(), AttachmentType.OUTGOING_DOCUMENT.name());
            Attachment attachment = Attachment.builder()
                    .itemId(entity.getId())
                    .type(AttachmentType.OUTGOING_DOCUMENT)
                    .filePath(newPath)
                    .fileName(req.getFileName())
                    .build();
            attachmentRepository.save(attachment);
        }
        catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }
}
