package com.dnd.niceteam.project;

import com.dnd.niceteam.project.dto.LectureProjectRequest;
import com.dnd.niceteam.project.dto.LectureTimeRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class LectureProjectTestFactory {

    private static final Long ID = 1L;
    private static final String NAME = "테스트 강의 프로젝트";
    private static final LocalDate START_DATE = LocalDate.of(2022, 7, 4);
    private static final LocalDate END_DATE = LocalDate.of(2022, 8, 27);
    
    private static final String PROFESSOR = "양경호 교수";

    public static LectureProjectRequest.Register createRegisterRequest() {
        LectureTimeRequest lectureTimeRequest = new LectureTimeRequest();
        lectureTimeRequest.setDay('월');
        lectureTimeRequest.setStartTime(LocalTime.of(8, 0));

        LectureProjectRequest.Register request = new LectureProjectRequest.Register();

        request.setName(NAME);
        request.setStartDate(START_DATE);
        request.setEndDate(END_DATE);
        request.setProfessor(PROFESSOR);
        request.setDepartmentId(1L);
        request.setLectureTimes(List.of(lectureTimeRequest));
        return request;
    }

}
