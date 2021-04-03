package edu.kpi.ip71.dovhopoliuk.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuzzyRelationPropertyViolation {

    private FuzzyRelationProperty property;
    private List<String> messages;
}
