package com.project.kall.service;

import com.project.kall.dto.NoticeDTO;
import com.project.kall.entity.NoticeEntity;
import com.project.kall.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    @Autowired
    NoticeRepository noticeRepository;

    public List<NoticeDTO> allNoticeList() {
        List<NoticeEntity> noticeEntity = noticeRepository.findAllByOrderByNoticeIdDesc();
        List<NoticeDTO> noticeDTOList = new ArrayList<>();
        for(NoticeEntity notice : noticeEntity) {
            if(notice != null) {
                NoticeDTO noticeDTO = NoticeDTO.toNoticeDTO(notice);
                noticeDTOList.add(noticeDTO);
            }
        }
        return noticeDTOList;
    }


    public NoticeDTO allNoticeSelect(Integer id) {
        Optional<NoticeEntity> optionalNoticeEntity = noticeRepository.findById(id);
        System.out.println(id);
        if(optionalNoticeEntity.isPresent()) {
            NoticeEntity noticeEntity = optionalNoticeEntity.get();
            NoticeDTO noticeDTO = NoticeDTO.toNoticeDTO(noticeEntity);
            if(noticeDTO != null) {
                noticeEntity.setCnt(noticeEntity.getCnt() + 1);
                noticeRepository.save(noticeEntity);
            }
            return noticeDTO;
        } else return null;
    }

    public  List<NoticeDTO> NoticeList() {
        List<NoticeDTO> noticeDTOList =  allNoticeList();
        List<NoticeDTO> noticeList = new ArrayList<>();
        for(NoticeDTO noticeDTO : noticeDTOList) {
            System.out.println("noticeDTO" + noticeDTO.getCategoryDTO().getCategoryId());
            if(noticeDTO.getCategoryDTO().getCategoryId().equals("notice")) {
                noticeList.add(noticeDTO);
            }
        }
        return noticeList;
    }

    public NoticeDTO NoticeSelect(Integer id) {
        List<NoticeDTO> noticeDTOList = NoticeList();
        NoticeDTO noticeDTO = new NoticeDTO();
        for(NoticeDTO noticeDTOs : noticeDTOList) {
            if(noticeDTOs.getNoticeId() == id) {
                noticeDTO = noticeDTOs;
                if(noticeDTO != null) {
                    NoticeEntity noticeEntity = NoticeEntity.toNoticeEntity(noticeDTO);
                    noticeEntity.setCnt(noticeEntity.getCnt() + 1);
                    noticeRepository.save(noticeEntity);
                }
            }
        }
        return noticeDTO;
    }


    public List<NoticeDTO> faqList() {
        List<NoticeDTO> noticeDTOList =  allNoticeList();
        List<NoticeDTO> noticeList = new ArrayList<>();
        for(NoticeDTO noticeDTO : noticeDTOList) {
            if(noticeDTO.getCategoryDTO().getCategoryId().contains("faq")) {
                noticeList.add(noticeDTO);
            }
        }
        return noticeList;
    }

    public NoticeDTO faqSelect(Integer id) {
        List<NoticeDTO> noticeDTOList = faqList();
        NoticeDTO noticeDTO = new NoticeDTO();
        for(NoticeDTO noticeDTOs : noticeDTOList) {
            if(noticeDTOs.getNoticeId() == id) {
                noticeDTO = noticeDTOs;
                if(noticeDTO != null) {
                    NoticeEntity noticeEntity = NoticeEntity.toNoticeEntity(noticeDTO);
                    noticeEntity.setCnt(noticeEntity.getCnt() + 1);
                    noticeRepository.save(noticeEntity);
                }
            }
        }
        return noticeDTO;
    }

    public void Noticeinsert(NoticeDTO noticeDTO) {
        NoticeEntity noticeEntity = NoticeEntity.toNoticeEntity(noticeDTO);
        noticeRepository.save(noticeEntity);
    }

    public NoticeDTO Noticeupdate(Integer id, NoticeDTO noticeDTO) {
        NoticeEntity noticeEntity = NoticeEntity.toNoticeEntity(noticeDTO);
        noticeRepository.save(noticeEntity);
        return noticeDTO;
    }

    public void Noticedelete(Integer id) {
        noticeRepository.deleteById(id);
    }
}
