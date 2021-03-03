package edu.kpi.ip71.dovhopoliuk.cp1.binary.relations.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RelationPropertyViolation {

    private RelationProperty property;
    private List<String> messages;
}
