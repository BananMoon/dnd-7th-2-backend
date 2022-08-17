package com.dnd.niceteam.domain.recruiting;

import com.dnd.niceteam.domain.code.Field;
import com.dnd.niceteam.domain.code.ProgressStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

import static com.dnd.niceteam.domain.member.QMember.member;
import static com.dnd.niceteam.domain.project.QProject.project;
import static com.dnd.niceteam.domain.project.QSideProject.sideProject;
import static com.dnd.niceteam.domain.recruiting.QRecruiting.recruiting;

@RequiredArgsConstructor
public class RecruitingRepositoryCustomImpl implements RecruitingRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public PageImpl<Recruiting> findAllByMemberId(Long memberId, Pageable pageable) {
        List<Recruiting> recruitings = query
                .selectFrom(recruiting)
                .join(recruiting.member, member)
                .where(member.id.eq(memberId))
                .orderBy(recruiting.createdDate.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetch();
        Long totalCount = query.select(recruiting.count())
                .from(recruiting)
                .join(recruiting.member, member)
                .where(member.id.eq(memberId))
                .fetchOne();

        return new PageImpl<>(recruitings, pageable, totalCount);
    }

    public PageImpl<Recruiting> findAllByMemberIdAndStatus(Long memberId, ProgressStatus progressStatus, Pageable pageable) {
        List<Recruiting> recruitings = query
                .selectFrom(recruiting)
                .join(recruiting.member, member)
                .where(member.id.eq(memberId)
                        .and(recruiting.status.eq(progressStatus)))
                .orderBy(recruiting.createdDate.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetch();
        Long totalCount = query.select(recruiting.count())
                .from(recruiting)
                .join(recruiting.member, member)
                .where(member.id.eq(memberId)
                        .and(recruiting.status.eq(progressStatus)))
                .fetchOne();

        return new PageImpl<>(recruitings, pageable, totalCount);
    }

    @Override
    public PageImpl<Recruiting> findAllByInterestingFieldsOrderByWriterLevel(Set<Field> interestingFields, Pageable pageable) {
        List<Recruiting> recruitings = query
                .select(recruiting)
                .from(recruiting)
                .join(recruiting.project, project)
                .where(project.in(
                        query.selectFrom(project)
                                .leftJoin(sideProject).on(sideProject.eq(project))
                                .where(sideProject.field.in(interestingFields))
                                .fetch()
                        )
                )
                .orderBy(recruiting.member.memberScore.level.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long totalCount = query.select(recruiting.count())
                .from(recruiting)
                .join(recruiting.project, project)
                .where(project.in(
                                query.selectFrom(project)
                                        .leftJoin(sideProject).on(sideProject.eq(project))
                                        .where(sideProject.field.in(interestingFields))
                                        .fetch()
                        )
                )
                .fetchOne();

        return new PageImpl<>(recruitings, pageable, totalCount);
    }
}