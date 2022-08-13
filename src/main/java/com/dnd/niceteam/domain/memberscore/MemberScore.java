package com.dnd.niceteam.domain.memberscore;

import com.dnd.niceteam.domain.code.ReviewTag;
import com.dnd.niceteam.domain.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Entity
@Getter
@Builder
@Table(name = "member_score")
@Where(clause = "use_yn = true")
@SQLDelete(sql = "UPDATE member_score SET use_yn = false where member_score_id = ?")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberScore extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_score_id")
    private Long id;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "review_num", nullable = false)
    private Integer reviewNum;

    @Column(name = "total_participation_score≠", nullable = false)
    private Integer totalParticipationScore;

    @Column(name = "rematching_sum", nullable = false)
    private Integer rematchingSum;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "review_tag_num", joinColumns = @JoinColumn(name = "member_score_id", nullable = false))
    @MapKeyColumn(name = "review_tag", length = 45)
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "num", nullable = false)
    @Builder.Default
    private Map<ReviewTag, Integer> reviewTagToNums = new HashMap<>(
            IntStream.range(0, ReviewTag.values().length).boxed()
                    .collect(Collectors.toMap(i -> ReviewTag.values()[i], i -> 0))
    );

    public Double participationPct() {
        if (isNull(totalParticipationScore) || isNull(reviewNum)) {
            return null;
        }
        return (double) totalParticipationScore / reviewNum * 20;
    }
}
