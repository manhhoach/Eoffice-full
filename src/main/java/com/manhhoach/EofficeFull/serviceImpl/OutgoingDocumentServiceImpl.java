package com.manhhoach.EofficeFull.serviceImpl;

import com.manhhoach.EofficeFull.constant.AttachmentType;
import com.manhhoach.EofficeFull.constant.ProcessFlowConstant;
import com.manhhoach.EofficeFull.constant.ProcessProgressType;
import com.manhhoach.EofficeFull.dto.outgoingDocument.CreateOutgoingDocReq;
import com.manhhoach.EofficeFull.entity.Attachment;
import com.manhhoach.EofficeFull.helper.FileHelper;
import com.manhhoach.EofficeFull.repository.AttachmentRepository;
import com.manhhoach.EofficeFull.repository.OutgoingDocumentRepository;
import com.manhhoach.EofficeFull.service.OutgoingDocumentService;
import com.manhhoach.EofficeFull.service.ProcessService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OutgoingDocumentServiceImpl implements OutgoingDocumentService {
    private final OutgoingDocumentRepository outgoingDocumentRepository;
    private final AttachmentRepository attachmentRepository;
    private final ProcessService processService;


    @Transactional
    @Override
    public void create(CreateOutgoingDocReq req) {
        try {
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

            processService.initProcess(entity.getId(), ProcessProgressType.OUTGOING_DOCUMENT, ProcessFlowConstant.OUTGOING_DOCUMENT);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Transactional
    @Override
    public void update(CreateOutgoingDocReq req, Long id) {
        try {
            var entity = outgoingDocumentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
            if (req.getFileName() != null && !req.getFileName().isBlank()) {
                String newPath = FileHelper.moveFile(req.getFileUrl(), AttachmentType.OUTGOING_DOCUMENT.name());
                Attachment attachment = Attachment.builder()
                        .itemId(entity.getId())
                        .type(AttachmentType.OUTGOING_DOCUMENT)
                        .filePath(newPath)
                        .fileName(req.getFileName())
                        .build();
                attachmentRepository.save(attachment);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {

        outgoingDocumentRepository.deleteById(id);
        attachmentRepository.deleteByItemIdAndType(id, AttachmentType.OUTGOING_DOCUMENT);
    }
}
