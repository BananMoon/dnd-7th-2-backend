package com.dnd.niceteam.comment;

import com.dnd.niceteam.comment.dto.CommentCreation;
import com.dnd.niceteam.domain.code.*;
import com.dnd.niceteam.project.dto.LectureTimeRequest;
import com.dnd.niceteam.project.dto.ProjectResponse;
import com.dnd.niceteam.recruiting.dto.RecruitingCreation;
import com.dnd.niceteam.recruiting.dto.RecruitingFind;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class DtoFactoryForTest {
    public static final String COMMENT_CONTENT = "모집글의 댓글입니다.";
    public static final Long PARENT_ID = 0L;
    public static final Long RECRUITING_ID = 1L;
    public static final Long PROJECT_ID = 1L;
    public static final Long DEPARTMENT_ID = 1L;

    public static CommentCreation.RequestDto createCommentRequest() {
        CommentCreation.RequestDto dto = new CommentCreation.RequestDto();
        dto.setContent(COMMENT_CONTENT);
        dto.setParentId(PARENT_ID);
        return dto;
    }

    public static final Long COMMENT_ID = 1L;
    public static CommentCreation.ResponseDto createCommentResponse() {
        CommentCreation.ResponseDto dto = new CommentCreation.ResponseDto();
        dto.setId(COMMENT_ID);
        return dto;
    }

    public static RecruitingCreation.RequestDto createLectureRecruitingRequest() {
        RecruitingCreation.RequestDto dto = new RecruitingCreation.RequestDto();
        dto.setTitle("모집글 제목 테스트");
        dto.setContent("모집글 내용 테스트");
        dto.setRecruitingType(Type.LECTURE);
        dto.setRecruitingEndDate(LocalDate.of(2022, 8, 16));
        dto.setActivityArea(ActivityArea.ONLINE);
        dto.setStatus(ProgressStatus.IN_PROGRESS);
        dto.setRecruitingMemberCount(3);
        dto.setIntroLink("test-intro");
        dto.setPersonalityAdjectives(Set.of(Personality.Adjective.PRECISE, Personality.Adjective.CREATIVE));
        dto.setPersonalityNouns(Set.of(Personality.Noun.INVENTOR, Personality.Noun.MEDIATOR));

        dto.setProjectStartDate(LocalDate.of(2022, 7, 4));
        dto.setProjectEndDate(LocalDate.of(2022, 8, 28));

        dto.setProjectName("project-name");
        dto.setDepartmentId(DEPARTMENT_ID);
        dto.setProfessor("test-professor");
        dto.setLectureTimes(createLectureTimeRequest());
        return dto;
    }

    private static List<LectureTimeRequest> createLectureTimeRequest() {
        LectureTimeRequest request1 = new LectureTimeRequest();
        request1.setDayOfWeek(DayOfWeek.MON);
        request1.setStartTime(LocalTime.of(9,0));

        LectureTimeRequest request2 = new LectureTimeRequest();
        request2.setDayOfWeek(DayOfWeek.WED);
        request2.setStartTime(LocalTime.of(9,30));
        return List.of(request1, request2);
    }

    public static RecruitingCreation.ResponseDto createLectureRecruitingResponse() {
        RecruitingCreation.ResponseDto dto = new RecruitingCreation.ResponseDto();
        dto.setRecruitingId(RECRUITING_ID);
        dto.setProjectId(PROJECT_ID);
        return dto;
    }

    public static RecruitingFind.DetailResponseDto createDetailSideRecruitingResponse() {
        RecruitingFind.DetailResponseDto dto = new RecruitingFind.DetailResponseDto();
        dto.setTitle("모집글 제목 테스트");
        dto.setContent("모집글 내용 테스트");
        dto.setRecruitingType(Type.SIDE);
        dto.setRecruitingStatus(ProgressStatus.IN_PROGRESS);
        dto.setRecruitingEndDate(LocalDate.of(2022, 8, 16));
        dto.setActivityArea(ActivityArea.ONLINE);
        dto.setRecruitingMemberCount(3);
        dto.setIntroLink("test-intro");
        dto.setPersonalityAdjectives(Set.of(Personality.Adjective.PRECISE, Personality.Adjective.CHEERFUL));
        dto.setPersonalityNouns(Set.of(Personality.Noun.INVENTOR, Personality.Noun.JACK_OF_ALL_TRADES));

        ProjectResponse.Detail detailProject = new ProjectResponse.Detail();
        detailProject.setStartDate(LocalDate.of(2022, 7, 4));
        detailProject.setEndDate(LocalDate.of(2022, 8, 28));
        detailProject.setName("project-name");
        detailProject.setField(Field.IT_SW_GAME);
        detailProject.setFieldCategory(FieldCategory.STUDY);

        dto.setProjectResponse(detailProject);
        return dto;
    }

    public static RecruitingFind.ListResponseDto createListLectureRecruitingResponse() {
        RecruitingFind.ListResponseDto dto = new RecruitingFind.ListResponseDto();
        dto.setId(RECRUITING_ID);
        dto.setTitle("모집글 제목 테스트");
        dto.setContent("모집글 내용 테스트");
        dto.setRecruitingType(Type.LECTURE);
        dto.setRecruitingStatus(ProgressStatus.IN_PROGRESS);
        dto.setCommentCount(0);
        dto.setBookmarkCount(0);
        dto.setProjectName("test-lecture-project-name");
        dto.setProfessor("test-professor-name");
        return dto;
    }

    public static RecruitingFind.RecommendedListResponseDto createRecommendedSideRecruitingListResponse() {
        RecruitingFind.RecommendedListResponseDto dto = new RecruitingFind.RecommendedListResponseDto();
        dto.setRecruitingId(RECRUITING_ID);
        dto.setTitle("모집글 제목 테스트");
        dto.setRecruitingMemberCount(4);
        dto.setProjectStartDate(LocalDate.of(2022,7,4));
        dto.setProjectEndDate(LocalDate.of(2022,8,27));
        dto.setRecruitingEndDate(LocalDate.of(2022,7,3));

        dto.setField(Field.AD_MARKETING);
        dto.setFieldCategory(FieldCategory.CONTEST);
        return dto;
    }
}
